# Plano Core Unchecked

## Objetivo

Executar a nova fase de saneamento de `unchecked` no core sensível usando o backlog literal do `javac` como fonte de verdade, sem fallback, sem suppression para mascarar warning e sem misturar correções mecânicas com pontos estruturais.

## Fonte de Verdade

- Build oficial:
  - `ant -f Full-build.xml commons-jar gameserver-jar compile-scripts compile-authserver`
- JDK alvo:
  - `C:\Program Files\Eclipse Adoptium\jdk-25.0.2.10-hotspot`
- Log de referência desta fase:
  - `jdk25-core-u3b.log`
- Baseline literal de entrada da fase:
  - `37` diagnósticos `unchecked`
  - `20` arquivos
- Backlog literal visível atual:
  - `25` diagnósticos `unchecked`
- Nota metodológica:
  - o lote é aceito por `diagnósticos-alvo eliminados`
  - o `delta visível líquido` do backlog deve ser registrado separadamente, pois o log pode revelar diagnósticos antes ocultos

## Regras

- Não tocar `deprecation` estrutural nesta fase.
- Não atualizar dependências.
- Não alterar comportamento funcional.
- Não usar `@SuppressWarnings` local ou global para esconder warning.
- Não usar `ignore`, relaxamento de lint, mudança de flag de compilação ou artifício similar.
- Não considerar fallback como solução real.
- Se parecer que apenas fallback resolve, parar e documentar.
- Toda correção deve ser canônica, tipada corretamente e pronta para produção.
- Preferir remover a causa raiz ao invés de reduzir apenas a superfície visível do warning.

## Classificação dos Lotes

### Core-U1

- Tipo:
  - mecânico no core
- Alvos:
  - `EventOwner.java`
  - `Creature.java`
  - `ItemInstance.java`
  - `ItemTemplate.java`
  - `AggroList.java`
  - `CharListenerList.java`
  - `Castle.java`
- Meta:
  - remover `8` diagnósticos `unchecked` literais
- Delta esperado:
  - `37 -> 29`
- Risco:
  - baixo
- Observação:
  - `EventOwner.java` é quick win explícito do lote
- Status:
  - concluído
- Resultado:
  - `8` diagnósticos-alvo eliminados
  - backlog visível `37 -> 34`
  - `5` diagnósticos desbloqueados no log

### Core-U2

- Tipo:
  - mecânico no core
- Alvos:
  - `Quest.java`
  - `Clan.java`
  - `SubUnit.java`
  - `SimpleSpawner.java`
  - `Reflection.java`
  - `NpcInstance.java:L1777`
- Meta:
  - atacar inicializações cruas, lazy init e mapas internos do core
- Risco:
  - baixo a médio
- Observação:
  - `NpcInstance` deve ficar fracionado; apenas o ponto mecânico entra aqui
- Status:
  - concluído
- Resultado:
  - `11` diagnósticos-alvo eliminados
  - backlog visível permaneceu `34`
  - `11` diagnósticos desbloqueados no log

### Core-U3A

- Tipo:
  - mecânico no eixo de eventos
- Alvos:
  - `GlobalEvent.java`
  - `SiegeEvent.java`
- Meta:
  - eliminar diagnósticos mecânicos do eixo de eventos antes do fluxo sensível de reward/distribuição
- Risco:
  - baixo a médio
- Status:
  - concluído
- Resultado:
  - `10` diagnósticos-alvo eliminados
  - backlog visível `34 -> 28`
  - `4` diagnósticos desbloqueados no log
- Observação:
  - os resíduos `GlobalEvent:L195/L239` e `SiegeEvent:L254` migraram para lote estrutural localizado próprio

### Core-U3A-struct

- Tipo:
  - estrutural localizado no eixo de eventos
- Alvos:
  - `GlobalEvent.java:L195`
  - `GlobalEvent.java:L239`
  - `SiegeEvent.java:L254`
- Meta:
  - endurecer o contrato genérico de buckets e de fábrica de siege sem alterar semântica funcional
- Risco:
  - médio
- Status:
  - concluído
- Resultado:
  - `3` diagnósticos-alvo eliminados
  - backlog visível `28 -> 25`
  - `0` diagnósticos desbloqueados no log
- Observação:
  - o contrato base foi tipado com token `Class<O>` em `GlobalEvent`
  - a hierarquia de siege passou a declarar explicitamente o tipo concreto via fábrica tipada

### Core-U3B

- Tipo:
  - mecânico no core com maior sensibilidade de fluxo
- Alvos:
  - `MonsterInstance.java`
  - `Party.java`
- Meta:
  - isolar correções em fluxo de reward/distribuição e listas locais cruas
- Risco:
  - médio
- Observação:
  - não misturar com reflection ou templates
- Status:
  - próximo lote recomendado

### Core-U4

- Tipo:
  - estrutural no core
- Alvos:
  - `Playable.java`
  - `Player.java`
  - `NpcInstance.java:L195`
  - `Residence.java`
- Meta:
  - tratar narrowing genérico e contratos centrais sem quebrar semântica
- Risco:
  - médio a alto

### Core-U5

- Tipo:
  - estrutural com reflection
- Alvos:
  - `NpcTemplate.java`
  - `DoorTemplate.java`
- Meta:
  - corrigir `Class` e `Constructor` crus de forma canônica
- Risco:
  - alto
- Observação:
  - lote próprio obrigatório

## Critério de Aceitação por Lote

- Build verde com o comando oficial.
- Zero warnings residuais nos arquivos tocados do lote.
- Nenhuma supressão artificial.
- Diff pequeno, cirúrgico e coerente.
- Solução pronta para produção.
- Checagem explícita por nome de arquivo no log final.

## Entrega Esperada por Lote

- Arquivos alterados.
- Resumo técnico das trocas.
- Delta literal do backlog `unchecked`.
- Confirmação exata dos warnings eliminados.
- Riscos remanescentes.
- Declaração explícita de que não foram usados fallback, suppression, ignore ou mascaramento do compilador.

## Ordem Recomendada

1. `Core-U1` concluído
2. `Core-U2` concluído
3. `Core-U3A` concluído
4. `Core-U3A-struct` concluído
5. `Core-U3B`
6. Reavaliar backlog literal
7. `Core-U4`
8. `Core-U5`
9. Fase separada de `deprecation`

## Decisão de Processo

- Enquanto houver quick wins mecânicos no core, eles devem ser preferidos antes dos lotes estruturais.
- Lotes estruturais só entram quando o backlog mecânico estiver efetivamente esgotado ou o ganho marginal deixar de compensar.
- Quando houver truncamento de warnings no `javac`, registrar separadamente:
  - diagnósticos-alvo eliminados
  - delta visível do backlog
  - diagnósticos desbloqueados no log
