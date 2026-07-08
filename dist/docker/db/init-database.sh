#!/bin/sh
set -eu

log() {
    printf '[db-init] %s\n' "$*"
}

quote_sql_literal() {
    printf '%s' "$1" | sed "s/'/''/g"
}

quote_identifier() {
    printf '%s' "$1" | sed 's/`/``/g'
}

sql_exec() {
    mariadb \
        --protocol=TCP \
        --host="${DB_HOST:-db}" \
        --port="${DB_PORT:-3306}" \
        --user=root \
        --password="${MARIADB_ROOT_PASSWORD}" \
        "$@"
}

wait_for_database() {
    attempts="${1:-60}"
    delay="${2:-2}"
    count=1

    while [ "$count" -le "$attempts" ]; do
        if mariadb-admin ping \
            --protocol=TCP \
            --host="${DB_HOST:-db}" \
            --port="${DB_PORT:-3306}" \
            --user=root \
            --password="${MARIADB_ROOT_PASSWORD}" \
            --silent >/dev/null 2>&1; then
            return 0
        fi

        log "Waiting for MariaDB (${count}/${attempts})"
        count=$((count + 1))
        sleep "$delay"
    done

    return 1
}

database_exists() {
    db_name="$1"
    quoted_db_name="$(quote_sql_literal "$db_name")"
    sql_exec --skip-column-names --batch \
        -e "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '${quoted_db_name}';" \
        | grep -Fxq "$db_name"
}

ensure_database() {
    db_name="$1"
    quoted_name="$(quote_identifier "$db_name")"
    sql_exec -e "CREATE DATABASE IF NOT EXISTS \`${quoted_name}\` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
}

ensure_user_grants() {
    db_name="$1"
    quoted_name="$(quote_identifier "$db_name")"
    quoted_user="$(quote_sql_literal "$DB_APP_USER")"
    quoted_password="$(quote_sql_literal "$DB_APP_PASSWORD")"
    sql_exec -e "CREATE USER IF NOT EXISTS '${quoted_user}'@'%' IDENTIFIED BY '${quoted_password}';"
    sql_exec -e "GRANT ALL PRIVILEGES ON \`${quoted_name}\`.* TO '${quoted_user}'@'%';"
    sql_exec -e "FLUSH PRIVILEGES;"
}

import_sql_tree() {
    db_name="$1"
    sql_root="$2"

    find "${sql_root}/install" -maxdepth 1 -type f -name '*.sql' | sort | while IFS= read -r sql_file; do
        log "Importing ${sql_file} into ${db_name}"
        sql_exec "$db_name" < "$sql_file"
    done
}

ensure_default_gameserver_registration() {
    db_name="$1"
    server_id="${DEFAULT_GAMESERVER_ID:-1}"
    server_host="${DEFAULT_GAMESERVER_HOST:-127.0.0.1}"
    quoted_host="$(quote_sql_literal "$server_host")"

    sql_exec "$db_name" -e "CREATE TABLE IF NOT EXISTS gameservers (server_id INT(11) NOT NULL, host VARCHAR(255) NOT NULL, PRIMARY KEY (server_id)) DEFAULT CHARSET=utf8;"

    gameserver_count="$(sql_exec --skip-column-names --batch "$db_name" -e "SELECT COUNT(*) FROM gameservers;")"
    gameserver_count="$(printf '%s' "$gameserver_count" | tr -d '[:space:]')"

    if [ "${gameserver_count:-0}" = "0" ]; then
        log "Registering default gameserver ID ${server_id} with host ${server_host} in ${db_name}"
        sql_exec "$db_name" -e "INSERT INTO gameservers (server_id, host) VALUES (${server_id}, '${quoted_host}');"
    else
        log "Gameserver already registered in ${db_name}. Skipping default registration."
    fi
}

seed_database_if_missing() {
    db_name="$1"
    sql_root="$2"

    if database_exists "$db_name"; then
        ensure_user_grants "$db_name"
        log "Database ${db_name} already exists. Skipping schema import."
        ensure_default_gameserver_registration "$db_name"
        return 0
    fi

    log "Database ${db_name} does not exist. Creating and importing schema."
    ensure_database "$db_name"
    ensure_user_grants "$db_name"
    import_sql_tree "$db_name" "$sql_root"
    ensure_default_gameserver_registration "$db_name"
}

main() {
    : "${MARIADB_ROOT_PASSWORD:?MARIADB_ROOT_PASSWORD is required}"
    : "${DB_APP_USER:?DB_APP_USER is required}"
    : "${DB_APP_PASSWORD:?DB_APP_PASSWORD is required}"

    auth_db="${AUTH_DB_NAME:-p_lucera}"
    game_db="${GAME_DB_NAME:-p_lucera}"

    wait_for_database

    if [ "$auth_db" = "$game_db" ]; then
        if database_exists "$auth_db"; then
            ensure_user_grants "$auth_db"
            log "Database ${auth_db} already exists. Skipping schema import."
            ensure_default_gameserver_registration "$auth_db"
            exit 0
        fi

        log "Database ${auth_db} does not exist. Creating and importing authserver and gameserver schemas."
        ensure_database "$auth_db"
        ensure_user_grants "$auth_db"
        import_sql_tree "$auth_db" "/workspace/authserver/sql"
        import_sql_tree "$auth_db" "/workspace/gameserver/sql"
        ensure_default_gameserver_registration "$auth_db"
        exit 0
    fi

    seed_database_if_missing "$auth_db" "/workspace/authserver/sql"
    seed_database_if_missing "$game_db" "/workspace/gameserver/sql"
}

main "$@"
