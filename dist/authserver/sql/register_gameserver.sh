#!/bin/sh

set -eu

if [ -f ./mysql_settings.sh ]; then
        . ./mysql_settings.sh
else
        echo "Can't find mysql_settings.sh file!"
        exit 1
fi

SERVER_ID="${SERVER_ID:-1}"
SERVER_HOST="${SERVER_HOST:-127.0.0.1}"

gameserver_count=$(mysql \
        --host="$DBHOST" \
        --user="$USER" \
        --password="$PASS" \
        --skip-column-names \
        --batch \
        "$DBNAME" \
        -e "CREATE TABLE IF NOT EXISTS gameservers (server_id INT(11) NOT NULL, host VARCHAR(255) NOT NULL, PRIMARY KEY (server_id)) DEFAULT CHARSET=utf8; SELECT COUNT(*) FROM gameservers;")

if [ "${gameserver_count}" -eq 0 ]; then
        echo "Registering default gameserver ID ${SERVER_ID} with host ${SERVER_HOST} ..."
        mysql \
                --host="$DBHOST" \
                --user="$USER" \
                --password="$PASS" \
                "$DBNAME" \
                -e "INSERT INTO gameservers (server_id, host) VALUES (${SERVER_ID}, '${SERVER_HOST}');"
else
        echo "Gameserver already registered in database. Skipping default registration."
fi
