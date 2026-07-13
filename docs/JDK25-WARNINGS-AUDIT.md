# Auditoria de Warnings JDK 25

## Escopo

- Build auditado: `ant -f Full-build.xml commons-jar gameserver-jar compile-scripts compile-authserver`
- JDK: `C:\Program Files\Eclipse Adoptium\jdk-25.0.2.10-hotspot`
- Auditoria original: warnings deduplicados por `arquivo + linha + categoria + mensagem + detalhes`

## Reconciliação de Métrica

- A fase inicial do trabalho usou um KPI operacional histórico para acompanhar redução incremental por ondas.
- A nova fase de `core unchecked` passa a usar como fonte de verdade o backlog literal do `javac`.
- Conclusão prática:
  - o KPI operacional histórico permanece útil como histórico de progresso
  - a execução técnica e os critérios de aceite da fase atual devem seguir o backlog literal do build real

## Fonte de Verdade Atual

- Fase atual: `Core Unchecked`
- Log literal mais recente de referência:
  - `jdk25-core-u3b.log`
- Backlog literal visível atual:
  - `25` diagnósticos `unchecked`
- Observação metodológica:
  - em ambiente com truncamento de warnings do `javac`, o aceite de cada lote é medido por `diagnósticos-alvo eliminados`
  - o `delta visível líquido` do backlog deve ser registrado separadamente, pois novos diagnósticos podem ser desbloqueados no log sem representar regressão

## KPI Histórico

- Baseline inicial deduplicado:
  - `274` warnings
  - `unchecked 197`
  - `deprecation 67`
  - `removal 8`
  - `path 1`
  - `rawtypes 1`
- Último baseline operacional reportado antes da reconciliação:
  - `69` warnings
  - `unchecked 2`
  - `deprecation 67`
  - `removal 0`
  - `path 0`
  - `rawtypes 0`
- Status:
  - esse KPI histórico fica congelado como métrica de continuidade
  - ele não deve mais ser tratado como backlog executável da fase atual

## Histórico Consolidado

### Ondas Concluídas

- `Onda 0`
  - eliminou `path`
- `Onda 1`
  - eliminou `removal`
- `Onda 2`
  - reduziu `unchecked` repetitivo em scripts prioritários
- `Onda 3`
  - saneou `commons` tipado e removeu `rawtypes` de `AuthServer`
- `Onda 3B`
  - reduziu `unchecked` de baixo risco em scripts, admin, instances e NIO
- `Onda 3C`
  - reduziu `unchecked` de baixo risco em utilitários, services e scripts adicionais
- `Onda 3D`
  - reduziu `unchecked` de baixo risco em `BanditMessagerInstance`, `_375/_610/_616/_625`, `ChangeBaseClass`, `CommandClassMaster`, `ItemBroker`, `TrickOfTrans` e `ResidenceManager`
- `Onda 3E`
  - reduziu `unchecked` em `QuestSell`, `SupportMagic`, `TopPvPPKService`, `VariationSellService` e blocos de community
- `Onda 3F`
  - reduziu `unchecked` em `SavingSnowman`, `FrintezzaGatekeeperInstance`, `_350_EnhanceYourWeapon`, `ACbConfigManager`, `CbPersonalCabinet` e `PawnShop`
- `Onda 3G`
  - reduziu `unchecked` em `Kama56Boss`, `OnNightOpen`, `SpecialTree`, `FrintezzaManager`, `Finder` e `Halloween`
- `Core-U1`
  - eliminou `8` diagnósticos `unchecked` literais em `EventOwner`, `Creature`, `ItemInstance`, `ItemTemplate`, `AggroList`, `CharListenerList` e `Castle`
  - desbloqueou `5` diagnósticos antes ocultos; backlog visível `37 -> 34`
- `Core-U2`
  - eliminou `11` diagnósticos `unchecked` literais em `Quest`, `Clan`, `SubUnit`, `SimpleSpawner`, `Reflection` e `NpcInstance:L1777`
  - desbloqueou `11` diagnósticos antes ocultos; backlog visível permaneceu `34`
- `Core-U3A`
  - encerrou a parte mecânica do eixo de eventos
  - eliminou `10` diagnósticos `unchecked` literais em `GlobalEvent` e `SiegeEvent`
  - desbloqueou `4` diagnósticos; backlog visível `34 -> 28`
- `Core-U3A-struct`
  - encerrou os resíduos estruturais localizados do eixo de eventos
  - eliminou `3` diagnósticos `unchecked` literais em `GlobalEvent:L195/L239` e `SiegeEvent:L254`
  - não desbloqueou novos diagnósticos; backlog visível `28 -> 25`

## Estado Estável do Build

- `path`, `removal` e `rawtypes` permanecem zerados
- `deprecation 67` permanece intocado por regra de escopo
- o backlog ativo desta fase está concentrado em `unchecked` do `core`

## Backlog Literal do Core

### Resumo

- Total literal visível atual: `25` diagnósticos `unchecked`
- Observação:
  - o backlog abaixo é o backlog-base reconciliado da entrada da fase
  - parte dele já foi eliminada nos lotes `Core-U1`, `Core-U2`, `Core-U3A` e `Core-U3A-struct`
  - os próximos lotes devem se orientar pelo log literal mais recente e não apenas por esta fotografia inicial

### Arquivos e Pontos

- `EventOwner.java`: `L17`, `L21`
- `Creature.java`: `L115`
- `Playable.java`: `L54`
- `Player.java`: `L830`
- `ItemInstance.java`: `L59`
- `ItemTemplate.java`: `L687`
- `NpcInstance.java`: `L195`, `L1777`
- `AggroList.java`: `L22`
- `Residence.java`: `L90`
- `Castle.java`: `L46`
- `NpcTemplate.java`: `L34`, `L35`, `L157`, `L158`, `L184`, `L185`, `L216`, `L282`
- `Quest.java`: `L407`
- `CharListenerList.java`: `L22`
- `Clan.java`: `L88`, `L89`, `L90`
- `SubUnit.java`: `L23`, `L24`
- `MonsterInstance.java`: `L377`, `L502`
- `Reflection.java`: `L674`, `L724`
- `Party.java`: `L522`
- `SimpleSpawner.java`: `L34`, `L45`
- `DoorTemplate.java`: `L16`, `L74`

## Agrupamento por Causa Raiz

- Coleções e mapas crus:
  - `Creature`, `ItemInstance`, `ItemTemplate`, `AggroList`, `Castle`, `Quest`, `CharListenerList`, `Clan`, `SubUnit`, `Reflection`, `SimpleSpawner`, `NpcInstance:L1777`
- Fluxo local cru em lista ou entry:
  - `MonsterInstance:L377`, `MonsterInstance:L502`, `Party:L522`
- Narrowing genérico com cast manual:
  - `EventOwner`, `Playable`, `Player`, `NpcInstance:L195`, `Residence`
- Reflection e construtores tipados via `Class` ou `Constructor` cru:
  - `NpcTemplate`, `DoorTemplate`

## Classificação por Risco

- Mecânico no core:
  - `EventOwner.java`
  - `Creature.java`
  - `ItemInstance.java`
  - `ItemTemplate.java`
  - `AggroList.java`
  - `Castle.java`
  - `Quest.java`
  - `CharListenerList.java`
  - `Clan.java`
  - `SubUnit.java`
  - `MonsterInstance.java`
  - `Reflection.java`
  - `Party.java`
  - `SimpleSpawner.java`
  - `NpcInstance.java:L1777`
- Estrutural no core:
  - `Playable.java:L54`
  - `Player.java:L830`
  - `NpcInstance.java:L195`
  - `Residence.java:L90`
  - `NpcTemplate.java`
  - `DoorTemplate.java`
- Misto no core:
  - `NpcInstance.java`

## Quick Wins Canônicos

- `EventOwner.java:L17-L21`
  - quick win claro
  - `Class<E>` já está disponível
  - o narrowing pode ser feito de forma canônica, sem cast cru manual
- Quick wins mecânicos adicionais:
  - `Creature.java:L115`
  - `ItemInstance.java:L59`
  - `ItemTemplate.java:L687`
  - `AggroList.java:L22`
  - `CharListenerList.java:L22`
  - `Castle.java:L46`
  - `Quest.java:L407`
  - `Reflection.java:L674-L724`
  - `SimpleSpawner.java:L34-L45`

## Estratégia Recomendada

- Encerrar as ondas `3x` periféricas.
- Abrir a fase `Core Unchecked` com backlog literal como fonte de verdade.
- Executar em lotes pequenos de `5` a `10` diagnósticos reais por vez.
- Separar estritamente:
  - lotes mecânicos no core
  - lotes estruturais no core
  - lote de `deprecation` estrutural

## Próximo Passo Recomendado

- Abrir `Core-U3B`
- Escopo sugerido:
  - `MonsterInstance.java`
  - `Party.java`
- Natureza:
  - lote mecânico no core com maior sensibilidade de fluxo
- Meta:
  - atacar listas locais cruas e fluxo de reward/distribuição
  - medir o aceite por diagnósticos-alvo eliminados e registrar o delta visível do backlog separadamente

## Artefatos Relacionados

- Auditoria histórica e reconciliação: `docs/JDK25-WARNINGS-AUDIT.md`
- Plano da nova fase: `docs/JDK25-CORE-UNCHECKED-PLAN.md`
- Inventário inicial deduplicado: `docs/JDK25-WARNINGS-INVENTORY.md`
