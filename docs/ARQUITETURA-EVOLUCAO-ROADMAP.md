# Roadmap de Evolucao Arquitetural

## Contexto

Este documento consolida um plano de evolucao arquitetural para o projeto com foco em escala real, previsibilidade operacional e reducao de gargalos historicos do ecossistema L2J/Lucera, sem cair em reescrita ampla prematura nem em modismos de infraestrutura no hot path do jogo.

O objetivo nao e "microservitizar tudo", e sim aumentar throughput e estabilidade com ownership claro de estado, concorrencia suspendivel bem aplicada, melhor observabilidade e separacao gradual entre hot path e servicos perifericos.

## Objetivo

Guiar a evolucao do projeto em fases pequenas, mensuraveis e reversiveis, preservando o comportamento funcional do servidor e evitando expansao de escopo.

## Principios

- Priorizar ganhos mensuraveis antes de grandes mudancas de arquitetura.
- Separar problemas de CPU, concorrencia, IO, persistencia e protocolo.
- Aplicar coroutines apenas onde ha espera, suspensao ou orquestracao assincrona clara.
- Evitar gRPC e microservicos no hot path de movimento, combate, skill e AI per-tick.
- Definir ownership explicito de estado antes de distribuir o mundo em processos.
- Medir cada fase com metricas objetivas.
- Nao substituir contratos legados por "atalhos cosmeticos".

## Nao Objetivos

- Nao reescrever o GameServer inteiro de uma vez.
- Nao migrar todo o projeto para Kotlin de forma ampla sem justificativa local.
- Nao externalizar IA, combate ou visibilidade para microservicos neste momento.
- Nao introduzir Redis como "bala de prata" sem definir source of truth, idempotencia e durabilidade.
- Nao prometer meta de 10k players antes de instrumentacao e benchmark.

## Estado Desejado

- Loop do jogo com menos bloqueio e menos contenção.
- Ownership explicito por actor, instancia, regiao ou shard.
- Persistencia desacoplada do hot path, com garantias claras.
- Gateway/control plane separados dos calculos centrais do jogo.
- Documentacao aberta de protocolo, contratos internos e limites de cada subsistema.
- Observabilidade suficiente para identificar gargalos reais por eixo tecnico.

## Fase 0 - Baseline e Observabilidade

### Objetivo

Criar uma linha de base confiavel antes de qualquer mudanca estrutural.

### Acoes

- Instrumentar ticks, timers e filas centrais.
- Medir p50, p95 e p99 dos seguintes eixos:
  - tick principal
  - encode de pacote
  - geodata/pathfinding
  - AI
  - persistencia
  - GC
- Criar logs estruturados para eventos de saturacao:
  - backlog de tasks
  - thread pool exhaustion
  - spikes de lock contention
  - pause de GC
- Definir cenarios de benchmark repetiveis:
  - cidade com muitos NPCs
  - raid
  - siege
  - farm com alto volume de skill ticks

### Entregaveis

- Documento de baseline tecnico.
- Painel inicial de metricas.
- Lista priorizada dos gargalos atuais.

### Criterio de Saida

- Time consegue apontar com evidencias onde o servidor perde tempo.

## Fase 1 - Ownership de Estado

### Objetivo

Parar de tratar o servidor como memoria compartilhada difusa e definir donos de estado.

### Acoes

- Mapear ownership de:
  - Player
  - Npc
  - Summon
  - Instance
  - Region
  - Event/Siege
- Documentar quais estruturas podem ser acessadas concorrentemente e quais devem ser processadas em serie pelo dono do estado.
- Identificar estruturas globais e hotspots de lock.
- Definir contratos de handoff entre dominios:
  - session -> player
  - player -> instance
  - npc -> region

### Entregaveis

- Documento de ownership e fronteiras.
- Lista de estruturas que devem sair de acesso compartilhado.

### Criterio de Saida

- Cada entidade critica tem ownership explicito e contrato de acesso definido.

## Fase 2 - Coroutines Seletivas

### Objetivo

Usar coroutines apenas onde elas melhoram concorrencia suspendivel, sem prometer ganho automatico em CPU-bound.

### Candidatos Prioritarios

- Movement scheduling
- Respawn
- Cooldowns e timers
- Scripts/eventos com espera
- Fluxos de retry e timeout
- IO assincrono nao critico

### Acoes

- Substituir sequencias baseadas em sleep/scheduler fragmentado por pipelines suspendiveis.
- Garantir cancelamento estruturado e ownership local do escopo.
- Medir alocacao, filas e reducao de contenção antes/depois.

### Nao Fazer Nesta Fase

- Nao migrar geodata/pathfinding pesado so por "modernizacao".
- Nao migrar AI critica de boss ou siege sem benchmark.
- Nao espalhar CoroutineScope global pelo projeto.

### Entregaveis

- Primeiros subsistemas suspendiveis em producao.
- Comparativo antes/depois por carga.

### Criterio de Saida

- Reducao observavel de bloqueio e simplificacao dos fluxos assincronos alvo.

## Fase 3 - Desacoplamento Periferico

### Objetivo

Retirar do hot path responsabilidades que nao precisam morar no mesmo processo de calculo do jogo.

### Candidatos

- Auth
- Chat
- Admin/control plane
- Telemetria
- Ferramentas web

### Acoes

- Definir fronteiras de API coarse-grained.
- Separar sessao e controle do gameplay core.
- Padronizar contratos de request/response e tracing.

### Tecnologias

- gRPC faz sentido aqui, nao no hot path de movimento e combate.

### Criterio de Saida

- Pelo menos um subsistema periferico desacoplado sem impacto no loop principal.

## Fase 4 - Persistencia Assincrona Com Garantia

### Objetivo

Reduzir impacto de IO no gameplay sem perder consistencia ou rastreabilidade.

### Acoes

- Mapear o que e:
  - estado quente
  - cache
  - source of truth
  - derivado
- Introduzir fila duravel ou outbox para escrita assincrona.
- Garantir idempotencia e replay seguro.
- Avaliar snapshots periodicos nos subsistemas certos.

### Cuidado

- Redis so entra com papel bem definido.
- Write-behind puro sem durabilidade nao e aceitavel como padrao.

### Criterio de Saida

- Persistencia deixa de bloquear o hot path nos casos alvo sem degradar consistencia.

## Fase 5 - Gateway e Protocolo

### Objetivo

Criar uma camada de borda moderna sem quebrar o cliente legado.

### Acoes

- Definir gateway de sessao/protocolo.
- Separar:
  - decode do cliente legado
  - autenticacao
  - compatibilidade de protocolo
  - roteamento interno para o dono da sessao
- Criar documentacao aberta de pacotes e contratos internos.

### Nao Fazer Nesta Fase

- Nao converter cada acao de gameplay em chamada gRPC entre servicos.

### Criterio de Saida

- Existe um plano documentado e testavel para gateway sem mover o combate para distribuicao prematura.

## Fase 6 - Sharding / Actor Model

### Objetivo

Distribuir processamento por ownership real, e nao por wishful thinking.

### Acoes

- Definir modelo de shard:
  - region
  - instance
  - actor mailbox
  - hibrido
- Medir handoff entre donos de estado.
- Introduzir filas por dominio com backpressure.
- Definir degradacao controlada e shedding.

### Criterio de Saida

- O servidor consegue escalar o mundo por ownership sem depender de memoria compartilhada ampla.

## Fase 7 - Open Knowledge

### Objetivo

Quebrar dependencia de conhecimento retido e tornar o projeto replicavel.

### Acoes

- Publicar protocolo e contratos internos.
- Documentar ownership, lifecycle e limites dos subsistemas.
- Criar RFCs e ADRs para decisoes de arquitetura.
- Padronizar benchmark e regressao de performance.

### Criterio de Saida

- Time e colaboradores conseguem evoluir o projeto sem depender de conhecimento oral.

## Matriz de Prioridade

### Trazer Agora

- Observabilidade
- Ownership de estado
- Coroutines seletivas
- Desacoplamento periferico

### Trazer Depois

- Persistencia assincrona com garantias
- Gateway de protocolo
- Modelo de shard/actor

### Nao Trazer Agora

- Microservicos no hot path
- gRPC para movimento/combate
- IA como servico externo per-tick
- Redis como source of truth implicito

## Metricas de Sucesso

- p95/p99 de tick por cenário
- backlog de filas por subsistema
- tempo de encode de pacote
- custo de geodata/pathfinding
- CPU por subsistema
- lock contention
- allocation rate e GC pause
- latencia de persistencia e atraso de flush
- numero de entidades ativas por dono de estado

## Ordem Recomendada

1. Fase 0 - Baseline e Observabilidade
2. Fase 1 - Ownership de Estado
3. Fase 2 - Coroutines Seletivas
4. Fase 3 - Desacoplamento Periferico
5. Fase 4 - Persistencia Assincrona Com Garantia
6. Fase 5 - Gateway e Protocolo
7. Fase 6 - Sharding / Actor Model
8. Fase 7 - Open Knowledge

## Observacao Final

O ganho real nao vira de uma tecnologia isolada. Ele vira da combinacao de:

- ownership explicito
- menos bloqueio
- concorrencia suspendivel bem aplicada
- persistencia desacoplada com garantia
- observabilidade
- e disciplina de escopo

Coroutines fazem sentido no projeto, mas como ferramenta dentro desse plano maior, nao como substituto automatico de arquitetura.
