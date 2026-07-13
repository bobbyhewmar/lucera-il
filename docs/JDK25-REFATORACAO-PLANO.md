# Plano de Refatoracao dos Warnings JDK 25

## Objetivo

Reduzir o baseline de warnings sob JDK 25 com escopo estrito, priorizando primeiro o que tem risco de quebra futura ou ruido estrutural, e deixando por ultimo os trechos que podem alterar comportamento de gameplay, scripts ou fluxo de spawn.

## Principios

- Fazer mudancas pequenas, revisaveis e por onda.
- Nao misturar saneamento mecanico de generics com refatoracao comportamental.
- Rebuildar apos cada onda com o mesmo comando da auditoria.
- Atualizar o inventario depois de cada lote para medir delta real.

## Status Atual

- Baseline corrente:
  - `unchecked`: `84`
  - `deprecation`: `67`
  - `rawtypes`: `0`
  - `removal`: `0`
  - `path`: `0`
  - `total`: `151`
- Build atual:
  - verde com `JDK 25`
- Delta acumulado:
  - `274 -> 151` (`-123`)

## Onda 0 - Higiene de Build

### Objetivo

Eliminar warning de configuracao e estabilizar o baseline.

### Itens

- Remover ou corrigir a referencia a `dist/libs/server.jar`.
- Verificar se a dependencia deveria existir, se foi renomeada ou se sobrou de build legado.

### Saida esperada

- `0` warnings `path`.

### Status

- Concluida.
- Resultado obtido:
  - `path 1 -> 0`
- Observacao:
  - a causa original estava no manifesto legado de `jts_bots.ext.jar`, sem necessidade de reempacotar terceiro.

## Onda 1 - APIs Marcadas para Remocao

### Objetivo

Eliminar warnings `removal` sem alterar comportamento.

### Itens

- Trocar `new Integer(...)` por `Integer.valueOf(...)`.
- Arquivos alvo:
  - `java/l2/commons/time/cron/SchedulingPattern.java`
  - `java/l2/gameserver/network/l2/s2c/SystemMessage.java`

### Risco

- Muito baixo.

### Saida esperada

- `0` warnings `removal`.

### Status

- Concluida.
- Resultado obtido:
  - `removal 8 -> 0`
- Observacao:
  - houve um residual fora do inventario inicial em `scripts/events/TvT2/PvPEvent.java`, corrigido com a mesma substituicao segura por `Integer.valueOf(...)`.

## Onda 2 - Scripts com Generics Cruas e Repetitivas

### Objetivo

Reduzir volume alto de `unchecked` com mudancas mecanicas e seguras.

### Itens

- Tipar `HashMap` locais usados como `Map<Skill, Integer>`.
- Remover chamadas que disparam `unchecked method invocation` por conta de tipos crus.
- Prioridade de arquivos:
  - `scripts/ai/Antharas.java`
  - `scripts/ai/Valakas.java`
  - `scripts/ai/Baium.java`
  - `scripts/achievements/AchievementMetricListeners.java`
  - `scripts/achievements/AchievementUI.java`
  - `scripts/achievements/AchievementCondition.java`

### Risco

- Baixo, desde que as trocas sejam estritamente de tipagem e nao de logica.

### Saida esperada

- Reducao forte do volume de `unchecked` em `scripts`.

### Status

- Concluida.
- Resultado obtido:
  - `unchecked 197 -> 98`
- Arquivos efetivamente saneados:
  - `scripts/ai/Antharas.java`
  - `scripts/ai/Valakas.java`
  - `scripts/ai/Baium.java`
  - `scripts/achievements/AchievementMetricListeners.java`
  - `scripts/achievements/AchievementUI.java`
  - `scripts/achievements/AchievementCondition.java`

## Onda 3 - Collections e Generics em Commons

### Objetivo

Endurecer contratos em estruturas reutilizadas por todo o servidor.

### Itens

- Tipar `ArrayList`, `TreeMap`, `CopyOnWriteArrayList`, `ArrayDeque` e mapas crus.
- Avaliar `@SuppressWarnings("unchecked")` apenas quando o cast for estruturalmente inevitavel e encapsulado.
- Prioridade de arquivos:
  - `java/l2/commons/net/nio/impl/SelectorThread.java`
  - `java/l2/commons/time/cron/SchedulingPattern.java`
  - `java/l2/commons/collections/LazyArrayList.java`
  - `java/l2/commons/lang/ArrayUtils.java`
  - `java/l2/commons/text/StrTable.java`
  - `java/l2/commons/threading/SteppingRunnableQueueManager.java`
  - `java/l2/commons/threading/RunnableStatsManager.java`
  - `java/l2/commons/util/TroveUtils.java`

### Risco

- Medio, porque classes de `commons` tem alta superficie de impacto.

### Saida esperada

- API interna mais tipada.
- Queda consistente de `unchecked` sem proliferar suppressions.

### Status

- Concluida.
- Resultado obtido:
  - `unchecked 98 -> 94`
  - `rawtypes 1 -> 0`
- Arquivos saneados:
  - `java/l2/commons/net/nio/impl/SelectorThread.java`
  - `java/l2/commons/collections/LazyArrayList.java`
  - `java/l2/commons/lang/ArrayUtils.java`
  - `java/l2/commons/text/StrTable.java`
  - `java/l2/commons/threading/SteppingRunnableQueueManager.java`
  - `java/l2/commons/threading/RunnableStatsManager.java`
  - `java/l2/commons/util/TroveUtils.java`
  - `java/l2/commons/time/cron/SchedulingPattern.java`
  - `java/l2/authserver/AuthServer.java`

## Onda 3B - Unchecked de Baixo Risco em Scripts, Admin, Instances e NIO

### Objetivo

Continuar reduzindo `unchecked` residual fora dos alvos principais de `commons`, sem tocar deprecated estrutural.

### Status

- Concluida.
- Resultado obtido:
  - `unchecked 94 -> 90`
- Arquivos saneados:
  - `scripts/events/TvT2/PvPEvent.java`
  - `scripts/events/TvT/TvTArena1.java`
  - `scripts/events/TvT/TvTArena2.java`
  - `scripts/events/TvT/TvTArena3.java`
  - `java/l2/gameserver/handler/admincommands/impl/AdminTeleportBookmark.java`
  - `java/l2/gameserver/instancemanager/Frintezza.java`
  - `instances/GvGInstance.java`
  - `npc/model/FreyaDeaconKeeperInstance.java`
  - `java/l2/commons/net/nio/impl/MMOConnection.java`
  - `java/l2/commons/net/nio/impl/SendablePacket.java`
- Observacao:
  - foi introduzido apenas um `@SuppressWarnings("unchecked")` pontual e encapsulado em helper privado de `SendablePacket`.

## Onda 3C - Unchecked de Baixo Risco em Utilitarios, Services e Scripts Adicionais

### Objetivo

Prosseguir na reducao de `unchecked` residual de baixo risco em utilitarios pequenos e scripts/services com tipagem previsivel.

### Status

- Concluida.
- Resultado obtido:
  - `unchecked 90 -> 84`
- Arquivos saneados:
  - `java/l2/commons/collections/EmptyIterator.java`
  - `java/l2/commons/collections/JoinedIterator.java`
  - `java/l2/commons/collections/MultiValueSet.java`
  - `java/l2/commons/lang/reference/HardReferences.java`
  - `npc/model/FarmMessengerInstance.java`
  - `quests/SagasSuperclass.java`
  - `services/Buffer.java`
  - `services/ACP.java`
- Observacao:
  - as unicas suppressions dessa onda sao pontuais e encapsuladas, apenas para casts estruturais inevitaveis em `EmptyIterator` e `HardReferences`.

## Onda 3D - Unchecked Residual de Baixo Risco

### Objetivo

Reduzir o proximo bloco de `unchecked` residual de baixo risco antes de abrir a frente de deprecated estrutural.

### Itens

- Atacar alvos com collections cruas, `safeGet`, `putAll`, `Pair`, listeners e casts previsiveis.
- Prioridade sugerida:
  - `BanditMessagerInstance`
  - `_375_WhisperOfDreams2`
  - `_610_MagicalPowerofWater2`
  - `_616_MagicalPowerofFire2`
  - `_625_TheFinestIngredientsPart2`
  - `ChangeBaseClass`
  - `CommandClassMaster`
  - `ItemBroker`
  - `TrickOfTrans`
  - `ResidenceManager`

### Risco

- Baixo a medio.
- O risco continua baixo enquanto as trocas forem apenas de tipagem explicita e encapsulamento local de casts inevitaveis.

### Saida esperada

- Queda adicional de `unchecked` sem tocar `deprecation`.

## Onda 4 - Deprecateds Estruturais

### Objetivo

Trocar APIs obsoletas que tendem a piorar em JDKs futuros.

### Itens

- `Thread.getId()` -> avaliar `threadId()` ou reestruturar comparacao de ownership em:
  - `java/l2/commons/util/concurrent/locks/ReentrantReadWriteLock.java`
- `new URL(String)` -> montar `URI` e converter com validacao em:
  - `java/l2/commons/versioning/Locator.java`
- `Functions.spawn(...)` deprecated em:
  - `java/l2/gameserver/model/quest/Quest.java`
- `SimpleSpawner` e `InstantZone.SpawnInfo` deprecated em:
  - `java/l2/gameserver/model/entity/Reflection.java`
- `Msg` deprecated em hotspots de gameplay:
  - `java/l2/gameserver/model/Playable.java`
  - `java/l2/gameserver/model/Player.java`
  - `java/l2/gameserver/model/Creature.java`
  - `java/l2/gameserver/model/Skill.java`
  - `java/l2/gameserver/model/Party.java`
  - `java/l2/gameserver/model/CommandChannel.java`
  - `java/l2/gameserver/model/pledge/Clan.java`

### Risco

- Medio a alto.
- Exige entender substituto correto para cada API deprecated.
- Nao deve ser feito no mesmo PR das ondas `3x`.

### Saida esperada

- Reducao de deprecateds com preservacao de comportamento.

## Onda 5 - Fechamento do Baseline

### Objetivo

Registrar baseline residual e consolidar o estado final apos esgotar as ondas mecanicas de baixo risco.

### Itens

- Rebuildar tudo.
- Comparar novo inventario com `docs/JDK25-WARNINGS-INVENTORY.md`.
- Registrar warnings residuais que exigem mudanca arquitetural ou dependencia externa.

### Status

- Parcialmente concluida.
- `java/l2/authserver/AuthServer.java` ja foi saneado na `Onda 3`.
- Permanecem nesta fase apenas consolidacao final e registro do baseline residual apos esgotar as ondas `3x`.

## Estrategia de Execucao

1. Um PR ou commit por onda.
2. Sempre anexar antes/depois do total de warnings.
3. Nunca misturar `unchecked` mecanico com `deprecated` estrutural no mesmo lote.
4. Quando um cast for inevitavel, encapsular e documentar o motivo.

## Criterios de Aceite por Onda

- Compila com JDK 25.
- Nenhuma regressao funcional evidente no modulo tocado.
- O numero de warnings cai ou, no pior caso, nao aumenta.
- O diff fica restrito aos arquivos planejados.

## Ordem Recomendada de Implementacao

1. `Onda 0`
2. `Onda 1`
3. `Onda 2`
4. `Onda 3`
5. `Onda 3B`
6. `Onda 3C`
7. `Onda 3D`
8. `Onda 5`
9. `Onda 4`

## Observacao Final

`Onda 4` continua por ultimo porque envolve APIs deprecated com potencial de semantic drift. O estado atual ainda justifica explorar mais uma frente `3x` de baixo risco antes de abrir a fase estrutural de deprecateds.
