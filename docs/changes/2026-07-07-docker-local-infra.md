# Infra Docker para execucao local simples e rapida

## Contexto

O projeto recebeu uma stack Docker para facilitar a execucao local com banco, bootstrap automatico da base e subida dos servidores com configuracao pronta para uso.

## Objetivo

Registrar a infraestrutura Docker e consolidar a documentacao de uso local rapido para desenvolvimento e testes.

## Arquivos alterados

- `dist/docker-compose.yml`
- `dist/.env.example`
- `dist/docker/db/init-database.sh`
- `dist/docker/server/Dockerfile`
- `dist/docker/server/entrypoint.sh`
- `dist/README-docker.md`

## Comportamento anterior

O projeto dependia de configuracao manual do ambiente local, banco e execucao dos servidores.

## Comportamento novo

Agora o projeto possui uma stack Docker local com:

- MariaDB 11.4
- bootstrap automatico e idempotente do banco
- AuthServer em Ubuntu 22.04 + OpenJDK 25
- GameServer em Ubuntu 22.04 + OpenJDK 25
- publicacao padrao das portas `3308`, `2106` e `7777`

O bootstrap tambem garante o registro padrao do GameServer ID `1` com host `127.0.0.1` quando a tabela `gameservers` estiver vazia.

## Configuracoes envolvidas

- `AUTH_DB_NAME`
- `GAME_DB_NAME`
- `DB_APP_USER`
- `DB_APP_PASSWORD`
- `MARIADB_ROOT_PASSWORD`
- `AUTH_CLIENT_PORT`
- `GAME_PORT`
- `DB_PUBLISHED_PORT`
- `GAME_EXTERNAL_HOSTNAME`
- `GAME_INTERNAL_HOSTNAME`

## Validacao executada

- conferencia manual da stack em `docker-compose.yml`
- conferencia do bootstrap do banco em `init-database.sh`
- conferencia da configuracao automatica dos servidores em `entrypoint.sh`
- revisao do guia rapido em `README-docker.md`

## Riscos, limites ou pendencias

- a execucao local via Docker depende de build e artefatos atualizados na pasta usada como contexto
- para ambientes Proxmox/LXC o fluxo recomendado continua sendo instalacao real no sistema, nao Docker
