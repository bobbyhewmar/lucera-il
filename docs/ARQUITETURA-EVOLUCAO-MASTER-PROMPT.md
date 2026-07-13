# Master Prompt - Evolucao Arquitetural

Voce vai atuar como arquiteto/implementador tecnico de uma evolucao arquitetural gradual do projeto, com foco em throughput, ownership de estado, concorrencia suspendivel bem aplicada e desacoplamento entre hot path e servicos perifericos.

## Contexto do Projeto

- Workspace: `d:\Jogos\Lineage II\Servidores\Lucera\Souce\main`
- Projeto: servidor Lucera/Lineage II em evolucao para JDK 25 e modernizacao arquitetural gradual
- Build oficial:
  - `ant -f Full-build.xml commons-jar gameserver-jar compile-scripts compile-authserver`
- Documentos obrigatorios antes de qualquer execucao:
  - `docs/ARQUITETURA-EVOLUCAO-ROADMAP.md`
  - `docs/JDK25-WARNINGS-AUDIT.md`
  - `docs/JDK25-REFATORACAO-PLANO.md`
  - `docs/JDK25-IMPLEMENTATION-HANDOFF-PROMPT.md`

## Objetivo Mestre

Evoluir o projeto em fases pequenas, mensuraveis e seguras, sem reescrita ampla prematura e sem mover o hot path do gameplay para microservicos antes da hora.

## Principios Inegociaveis

- Nao usar `@SuppressWarnings`, `ignore`, relaxamento de lint, mudanca de flag de compilacao ou mascaramento de warning como "solucao".
- Nao usar fallback cosmetico para esconder problema estrutural.
- Nao microservitizar movimento, combate, skill, visibility ou AI per-tick nesta fase.
- Nao introduzir Redis ou gRPC no hot path sem ownership e benchmark.
- Nao prometer metas de escala sem baseline e metricas.
- Toda mudanca deve ser canonicamente pronta para producao.
- Toda fase deve produzir documentacao `.md`.

## Como Trabalhar

### Passo 1 - Ler o estado atual

Antes de editar qualquer coisa:

- Ler os documentos obrigatorios.
- Mapear o que ja existe no codigo relacionado ao recorte atual.
- Identificar limites de escopo e eixos fora de escopo.

### Passo 2 - Definir recorte minimo

Antes de implementar:

- escolher um recorte pequeno e reversivel
- dizer explicitamente:
  - objetivo
  - arquivos-alvo
  - risco
  - criterio de aceitacao
  - o que fica fora de escopo

### Passo 3 - Implementar so o necessario

- Fazer somente o que o recorte pede.
- Se surgir dependencia real fora do escopo:
  - parar
  - documentar
  - pedir ou registrar expansao minima
- Nao misturar problemas heterogeneos no mesmo lote.

### Passo 4 - Validar

- Rebuildar com o comando oficial.
- Medir antes/depois.
- Registrar delta e riscos remanescentes.

### Passo 5 - Documentar

Ao fim de cada lote:

- gerar `.md` em `docs/changes/`
- atualizar docs de roadmap/auditoria quando aplicavel
- registrar o proximo passo recomendado

## Ordem de Execucao Recomendada

### Fase 0 - Baseline e Observabilidade

Objetivo:

- Medir o servidor antes de mudar arquitetura.

Entregas:

- metrica de tick
- custo de geodata/pathfinding
- custo de encode de pacotes
- backlog de filas
- GC e allocation rate
- cenarios de benchmark repetiveis

Nao fazer:

- Nao comecar por microservicos.
- Nao comecar por "otimizacao cega".

### Fase 1 - Ownership de Estado

Objetivo:

- Definir dono de estado para player, npc, summon, instance, region e eventos.

Entregas:

- documento de ownership
- mapa de estruturas compartilhadas
- fronteiras de acesso e handoff

Nao fazer:

- Nao genericizar o mundo inteiro sem ownership claro.

### Fase 2 - Coroutines Seletivas

Objetivo:

- Introduzir coroutines apenas em fluxos suspendiveis e de orquestracao.

Alvos bons:

- movement scheduling
- respawn
- cooldowns
- timers
- scripts/eventos com espera
- retry/timeout
- IO assincrono nao critico

Alvos ruins nesta fase:

- geodata/pathfinding pesado
- combat loop central
- AI critica de boss
- visibility/broadcast central

### Fase 3 - Desacoplamento Periferico

Objetivo:

- Separar auth, chat, admin, telemetria e ferramentas do processo principal.

Pode usar:

- gRPC ou contrato tipado equivalente

Nao pode usar como hot path:

- movimento
- combate
- skill cast
- AI per-tick

### Fase 4 - Persistencia Assincrona Com Garantia

Objetivo:

- Tirar o IO do hot path com garantias explicitas.

Requisitos:

- definir source of truth
- fila duravel/outbox
- idempotencia
- replay/snapshot onde couber

Nao fazer:

- Nao implementar write-behind ingenuo sem durabilidade.

### Fase 5 - Gateway e Protocolo

Objetivo:

- Criar camada de borda moderna para sessao, decode, auth e roteamento.

Direcao:

- gateway de protocolo
- ownership de sessao
- documentacao aberta de pacotes

Nao fazer:

- Nao converter cada acao do jogo em RPC distribuido.

### Fase 6 - Sharding / Actor Model

Objetivo:

- Escalar por ownership e mailboxes, nao por memoria compartilhada difusa.

Entregas:

- proposta de shard por region/instance/actor
- backpressure
- handoff controlado
- degrade mode

### Fase 7 - Open Knowledge

Objetivo:

- Publicar e manter conhecimento tecnico do projeto.

Entregas:

- protocolo
- ownership
- ADRs
- RFCs
- benchmark docs

## Anti-Goals

- Nao migrar "tudo para Kotlin" de uma vez.
- Nao microservitizar o gameplay core.
- Nao usar Redis como justificativa para ignorar consistencia.
- Nao fazer trocas amplas sem baseline.
- Nao usar benchmark informal como prova de arquitetura.

## Metricas Minimas Por Fase

- p50/p95/p99 do tick
- custo de geodata/pathfinding
- encode de pacotes
- CPU por subsistema
- lock contention
- tamanho de filas
- atraso de persistencia
- allocation rate
- GC pause

## Formato de Entrega Esperado da IA

Para cada recorte, responder com:

1. objetivo do lote
2. arquivos-alvo
3. risco
4. implementacao feita
5. validacao executada
6. delta medido
7. riscos remanescentes
8. proximo passo recomendado

## Politica de Escopo

- Se um lote parecer exigir redesign amplo, parar.
- Se um problema puder ser resolvido localmente de forma canônica, fazer o menor diff possivel.
- Se a melhoria for apenas cosmetica e nao atacar causa raiz, nao implementar.

## Politica de Linguagem

- Documentacao e relatorios em Portugues.
- Codigo e nomes tecnicos seguem o padrao existente do projeto.

## Prompt de Execucao Inicial

Antes de implementar qualquer evolucao maior, faca:

1. leitura do roadmap
2. leitura do estado atual do codigo relevante
3. proposta de recorte minimo
4. inventario dos riscos
5. implementacao apenas se o recorte for local, mensuravel e reversivel

## Resultado Esperado

Ao fim dessa trilha, o projeto deve estar mais escalavel e previsivel nao por adocao de tecnologia da moda, mas por:

- ownership claro
- menos bloqueio
- concorrencia suspendivel bem aplicada
- persistencia desacoplada com garantia
- observabilidade
- e documentacao aberta
