# Setup Local do Projeto

Este guia descreve o fluxo padrao para compilar, configurar o banco e executar o projeto localmente sem Docker.

## Quando usar este guia

Use este fluxo quando voce quiser rodar o projeto:

- em Linux, VM, bare metal ou WSL
- em Proxmox, especialmente quando o ambiente for LXC/LXS/LXC-like
- com MariaDB e JDK instalados de forma real no sistema

## Aviso importante para Proxmox

Se voce estiver usando Proxmox, trate este projeto como uma aplicacao Linux padrao:

- instale MariaDB de forma nativa no sistema
- instale JDK de forma nativa no sistema
- compile com `ant`
- execute o `AuthServer` e o `GameServer` via scripts `.sh`

Nao use Docker como fluxo principal em Proxmox/LXC. Em muitos ambientes Proxmox o Docker dentro de containers aninhados tem comportamento inconsistente com rede, filesystem, permissao e init de servicos. Para esse cenario, o caminho mais estavel e o modo padrao com instalacoes reais.

## Resumo da estrutura

- codigo-fonte: `java/` e `scripts/`
- arquivos de runtime base: `dist/`
- artefato pronto para execucao apos build: `build/dist/`

Importante: para rodar o servidor, use sempre `build/dist/`. A pasta `dist/` so contem os arquivos-base; os jars compilados sao gerados em `build/dist/libs/`.

## Pre-requisitos

### Linux / Ubuntu / Debian

Pacotes recomendados:

```bash
sudo apt update
sudo apt install -y ant mariadb-server mariadb-client openjdk-25-jdk
```

Observacoes:

- o projeto foi migrado para OpenJDK 25
- para manter paridade com a stack Docker e com o ambiente suportado, use JDK 25
- o projeto ja carrega dependencias a partir de `dist/libs`, entao nao existe etapa Maven ou Gradle

### Portas usadas pelo projeto

- `2106`: clientes -> AuthServer
- `9014`: comunicacao interna GameServer <-> AuthServer
- `7777`: clientes -> GameServer
- `3306`: MariaDB local padrao

Se o servidor for acessado por outra maquina, libere essas portas no firewall e ajuste os IPs das configuracoes.

## 1. Compilar o projeto

No diretorio raiz do repositorio:

```bash
ant -f Full-build.xml dist
```

Ao final, o ambiente pronto para execucao ficara em:

```bash
build/dist
```

## 2. Criar o banco de dados

O projeto nao cria o banco automaticamente no fluxo local padrao. Os scripts SQL apenas importam as tabelas.

O caminho mais simples e usar um banco unico, igual ao padrao dos arquivos `.properties`:

- database: `p_lucera`
- usuario: `root` ou um usuario dedicado

Exemplo com usuario dedicado:

```sql
CREATE DATABASE p_lucera CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'lucera'@'localhost' IDENTIFIED BY 'troque-esta-senha';
GRANT ALL PRIVILEGES ON p_lucera.* TO 'lucera'@'localhost';
FLUSH PRIVILEGES;
```

Voce pode usar dois bancos separados para auth e game, mas o setup mais simples deste projeto e um banco unico.

## 3. Ajustar os scripts de importacao SQL

Edite os arquivos:

- `build/dist/authserver/sql/mysql_settings.sh`
- `build/dist/gameserver/sql/mysql_settings.sh`

Se voce for usar um banco unico, deixe ambos apontando para o mesmo database. Exemplo:

```sh
USER=lucera
PASS=troque-esta-senha
DBNAME=p_lucera
DBHOST=localhost
```

Observacao importante:

- os templates antigos desses arquivos usam nomes diferentes como `l2pdb` e `l2jdb`
- se voce nao alinhar isso com o banco real, a importacao vai falhar

## 4. Importar as tabelas

Rode os scripts abaixo na ordem:

```bash
cd build/dist/authserver/sql
sh install.sh
```

```bash
cd build/dist/gameserver/sql
sh install.sh
```

Esses scripts percorrem `install/*.sql` e importam tudo no banco configurado em `mysql_settings.sh`.

No caso do AuthServer, o `install.sh` tambem registra automaticamente um GameServer padrao quando a tabela `gameservers` ainda estiver vazia:

- `server_id = 1`
- `host = 127.0.0.1`

Neste fork, esse cadastro persistente nao exige arquivo `hexid`. O handshake atual do GameServer com o AuthServer usa o `RequestServerID` e os dados de rede enviados na conexao.

Se voce quiser rodar apenas esse cadastro manualmente depois, use:

```bash
cd build/dist/authserver/sql
sh register_gameserver.sh
```

## 5. Ajustar os arquivos de configuracao

### AuthServer

Arquivo:

- `build/dist/authserver/config/authserver.properties`

Campos mais importantes:

- `LoginserverHostname`
- `LoginserverPort`
- `LoginHost`
- `LoginPort`
- `Database_Host`
- `Maria_Port`
- `Data_Name`
- `Login`
- `Password`

Exemplo comum para ambiente local:

```properties
LoginserverHostname = *
LoginserverPort = 2106
LoginHost = 127.0.0.1
LoginPort = 9014
Database_Host = localhost
Maria_Port = 3306
Data_Name = p_lucera
Login = lucera
Password = troque-esta-senha
```

### GameServer

Arquivo:

- `build/dist/gameserver/config/server.properties`

Campos mais importantes:

- `GameserverHostname`
- `GameserverPort`
- `ExternalHostname`
- `InternalHostname`
- `LoginHost`
- `LoginPort`
- `RequestServerID`
- `DataBse_Host`
- `DataBase_port`
- `DataBase_login`
- `DataBase_Password`
- `DataBase_Name_DB`

Exemplo comum para ambiente local:

```properties
GameserverHostname = *
GameserverPort = 7777
ExternalHostname = 127.0.0.1
InternalHostname = 127.0.0.1
LoginHost = 127.0.0.1
LoginPort = 9014
RequestServerID = 1
DataBse_Host = localhost
DataBase_port = 3306
DataBase_login = lucera
DataBase_Password = troque-esta-senha
DataBase_Name_DB = p_lucera
```

Se o servidor estiver em outra maquina ou VM:

- troque `ExternalHostname` para o IP publico/roteavel
- troque `InternalHostname` para o IP interno correto
- mantenha `LoginHost` apontando para o host onde o AuthServer esta rodando

## 6. Iniciar os servidores

Suba primeiro o AuthServer e depois o GameServer.

### Modo simples

```bash
cd build/dist/authserver
sh StartAuthServer.sh
```

```bash
cd build/dist/gameserver
sh StartGameServer.sh
```

### Modo recomendado para diagnostico

Esse modo deixa o processo no terminal e facilita reinicio/observacao:

```bash
cd build/dist/authserver
sh AuthServer_loop.sh
```

Em outro terminal:

```bash
cd build/dist/gameserver
sh GameServer_loop.sh
```

## 7. Acompanhar logs

Os dois loops redirecionam a saida para:

- `build/dist/authserver/log/stdout.log`
- `build/dist/gameserver/log/stdout.log`

Para acompanhar em tempo real:

```bash
tail -f build/dist/authserver/log/stdout.log
```

```bash
tail -f build/dist/gameserver/log/stdout.log
```

## 8. Parar os processos

Se estiver rodando em background:

```bash
pkill -f l2.authserver.AuthServer
pkill -f l2.gameserver.GameServer
```

Ou use `jps -l` para identificar os PIDs Java antes de encerrar.

## 9. Checklist rapido de problemas comuns

### O build gerou `build/dist`, mas o servidor nao sobe

Verifique:

- se voce esta executando a partir de `build/dist`
- se o JDK instalado esta funcional no `PATH`
- se as senhas do banco nos `.properties` batem com o banco real

### O SQL importou em banco errado

Verifique:

- `build/dist/authserver/sql/mysql_settings.sh`
- `build/dist/gameserver/sql/mysql_settings.sh`

Os dois precisam apontar para o banco correto.

### O GameServer nao conecta no AuthServer

Verifique:

- `LoginHost` e `LoginPort` em `server.properties`
- `LoginHost` e `LoginPort` em `authserver.properties`
- porta `9014` liberada entre os processos/hosts

### O cliente nao consegue entrar

Verifique:

- `LoginserverPort = 2106`
- `GameserverPort = 7777`
- `ExternalHostname`
- firewall do sistema/VM/LXC/host Proxmox

## Recomendacao final para Proxmox

Em Proxmox, a recomendacao pratica e:

1. criar uma VM Linux ou um container LXC com rede normal
2. instalar MariaDB, JDK e Ant no sistema
3. compilar com `ant -f Full-build.xml dist`
4. configurar banco e `.properties`
5. executar via `sh StartAuthServer.sh` e `sh StartGameServer.sh`

Esse e o fluxo mais previsivel e mais proximo do comportamento esperado do projeto fora do Docker.
