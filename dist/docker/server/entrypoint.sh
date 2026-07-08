#!/bin/sh
set -eu

log() {
    printf '[l2-entrypoint] %s\n' "$*"
}

escape_sed_replacement() {
    printf '%s' "$1" | sed 's/[\/&|]/\\&/g'
}

set_property() {
    file="$1"
    key="$2"
    value="$3"
    escaped_value="$(escape_sed_replacement "$value")"

    if grep -Eq "^[[:space:]]*${key}[[:space:]]*=" "$file"; then
        sed -i "s|^[[:space:]]*${key}[[:space:]]*=.*$|${key} = ${escaped_value}|" "$file"
    else
        printf '\n%s = %s\n' "$key" "$value" >> "$file"
    fi
}

wait_for_tcp() {
    host="$1"
    port="$2"
    label="$3"
    attempts="${4:-60}"
    delay="${5:-2}"
    count=1

    while [ "$count" -le "$attempts" ]; do
        if nc -z "$host" "$port" >/dev/null 2>&1; then
            log "$label is reachable at $host:$port"
            return 0
        fi

        log "Waiting for $label at $host:$port ($count/$attempts)"
        count=$((count + 1))
        sleep "$delay"
    done

    log "Timed out waiting for $label at $host:$port"
    return 1
}

configure_authserver() {
    config_file="/opt/l2/authserver/config/authserver.properties"

    set_property "$config_file" "LoginserverHostname" "${AUTH_BIND_HOST:-*}"
    set_property "$config_file" "LoginserverPort" "${AUTH_CLIENT_PORT:-2106}"
    set_property "$config_file" "LoginHost" "${AUTH_GAMESERVER_BIND_HOST:-*}"
    set_property "$config_file" "LoginPort" "${AUTH_GAMESERVER_PORT:-9014}"
    set_property "$config_file" "Database_Host" "${DB_HOST:-db}"
    set_property "$config_file" "Maria_Port" "${DB_PORT:-3306}"
    set_property "$config_file" "Data_Name" "${AUTH_DB_NAME:-p_lucera}"
    set_property "$config_file" "Login" "${DB_APP_USER:-lucera}"
    set_property "$config_file" "Password" "${DB_APP_PASSWORD:-lucera}"

    if [ -n "${AUTH_DATABASE_MAX_CONNECTIONS:-}" ]; then
        set_property "$config_file" "DatabaseMaxConnections" "${AUTH_DATABASE_MAX_CONNECTIONS}"
    fi
}

configure_gameserver() {
    config_file="/opt/l2/gameserver/config/server.properties"
    external_host="${GAME_EXTERNAL_HOSTNAME:-127.0.0.1}"
    internal_host="${GAME_INTERNAL_HOSTNAME:-$external_host}"

    set_property "$config_file" "GameserverHostname" "${GAME_BIND_HOST:-*}"
    set_property "$config_file" "GameserverPort" "${GAME_PORT:-7777}"
    set_property "$config_file" "ExternalHostname" "$external_host"
    set_property "$config_file" "InternalHostname" "$internal_host"
    set_property "$config_file" "LoginHost" "${AUTH_SERVICE_HOST:-authserver}"
    set_property "$config_file" "LoginPort" "${AUTH_GAMESERVER_PORT:-9014}"
    set_property "$config_file" "RequestServerID" "${GAME_REQUEST_SERVER_ID:-1}"
    set_property "$config_file" "DataBse_Host" "${DB_HOST:-db}"
    set_property "$config_file" "DataBase_port" "${DB_PORT:-3306}"
    set_property "$config_file" "DataBase_Name_DB" "${GAME_DB_NAME:-p_lucera}"
    set_property "$config_file" "DataBase_login" "${DB_APP_USER:-lucera}"
    set_property "$config_file" "DataBase_Password" "${DB_APP_PASSWORD:-lucera}"

    if [ -n "${GAME_DATABASE_MAX_CONNECTIONS:-}" ]; then
        set_property "$config_file" "DatabaseMaxConnections" "${GAME_DATABASE_MAX_CONNECTIONS}"
    fi
}

launch_authserver() {
    cd /opt/l2/authserver
    exec sh -c 'exec java -server -Dfile.encoding=UTF-8 ${L2_JAVA_OPTS:-"-Xms256m -Xmx256m"} -cp "config:/opt/l2/libs/*" l2.authserver.AuthServer'
}

launch_gameserver() {
    cd /opt/l2/gameserver
    exec sh -c 'exec java -server -Dfile.encoding=UTF-8 ${L2_JAVA_OPTS:-"-Xms2g -Xmx3g"} -cp "config:/opt/l2/libs/*" l2.gameserver.GameServer'
}

main() {
    kind="${L2_SERVER_KIND:-gameserver}"

    wait_for_tcp "${DB_HOST:-db}" "${DB_PORT:-3306}" "MariaDB"

    case "$kind" in
        authserver)
            configure_authserver
            launch_authserver
            ;;
        gameserver)
            configure_gameserver
            wait_for_tcp "${AUTH_SERVICE_HOST:-authserver}" "${AUTH_GAMESERVER_PORT:-9014}" "AuthServer"
            launch_gameserver
            ;;
        *)
            log "Unsupported L2_SERVER_KIND: $kind"
            exit 1
            ;;
    esac
}

main "$@"
