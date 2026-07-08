# Docker Quick Start

Use this stack to run the project locally in a simple and fast way.

## Local run

1. Go to the `dist` folder.
2. Copy `.env.example` to `.env`.
3. Adjust the passwords and hostnames if needed.
4. Run `docker compose up -d --build`.

Example:

```bash
cd dist
cp .env.example .env
docker compose up -d --build
```

## Default local ports

- `3308` -> MariaDB 11.4
- `2106` -> AuthServer
- `7777` -> GameServer

## Stack

- `db`: MariaDB 11.4
- `db-init`: idempotent database bootstrap using the existing SQL from `authserver/sql/install` and `gameserver/sql/install`
- `authserver`: Ubuntu 22.04 + OpenJDK 25
- `gameserver`: Ubuntu 22.04 + OpenJDK 25

## What Docker does automatically

- creates the configured database only when it does not already exist
- imports the project SQL automatically on first run
- supports single database mode when `AUTH_DB_NAME` and `GAME_DB_NAME` are the same
- registers a default GameServer when the `gameservers` table is empty

Default GameServer registration:

- `server_id = 1`
- `host = 127.0.0.1`

This project does not require a persisted `hexid` file in the current handshake flow, so Docker does not generate one.

## Useful commands

View logs:

```bash
docker compose logs -f authserver
docker compose logs -f gameserver
```

Stop the stack:

```bash
docker compose down
```
