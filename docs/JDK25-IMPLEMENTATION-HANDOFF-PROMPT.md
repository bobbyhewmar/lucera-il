# Prompt de Handoff para Outra IA

Você vai atuar como implementador técnico da nova fase `Core Unchecked` em um servidor Lucera/Lineage II sob JDK 25.

## Contexto

- Workspace: `d:\Jogos\Lineage II\Servidores\Lucera\Souce\main`
- JDK alvo: `C:\Program Files\Eclipse Adoptium\jdk-25.0.2.10-hotspot`
- Build oficial para validação:
  - `ant -f Full-build.xml commons-jar gameserver-jar compile-scripts compile-authserver`

## Documentos Obrigatórios

- `docs/JDK25-WARNINGS-AUDIT.md`
- `docs/JDK25-CORE-UNCHECKED-PLAN.md`
- `docs/JDK25-WARNINGS-INVENTORY.md`

## Fonte de Verdade

- Ignore o KPI operacional histórico como baseline executável.
- A fonte de verdade desta fase é o backlog literal do `javac`, conforme reconciliado na auditoria.
- Backlog literal visível atual:
  - `25` diagnósticos `unchecked`
- Nota metodológica:
  - medir o lote por `diagnósticos-alvo eliminados`
  - registrar separadamente o `delta visível líquido` do backlog
  - registrar também eventuais diagnósticos desbloqueados no log final

## Objetivo

Executar o lote `Core-U3B` de forma canônica, pronta para produção, sem fallback, sem suppression para mascarar warning e sem misturar correções mecânicas com pontos estruturais do core.

## Lote Atual

- `Core-U3B`
- Alvos fechados:
  - `MonsterInstance.java`
  - `Party.java`
- Meta:
  - atacar listas locais cruas e fluxo de reward/distribuição
  - eliminar os diagnósticos mecânicos do lote sem misturar com contracts estruturais

## Regras Obrigatórias

- Não tocar `deprecation` estrutural.
- Não atualizar dependências.
- Não alterar comportamento funcional.
- Não usar `@SuppressWarnings` local ou global para mascarar warning.
- Não usar `ignore`, exclusão de lint, relaxamento de regra, mudança de flag de compilação ou artifício similar.
- Não considerar fallback como solução real.
- Fallback só pode ser considerado em último caso absoluto, quando a solução canônica for comprovadamente inviável.
- Se parecer que apenas fallback resolve, pare e documente; não implemente automaticamente.
- Toda correção deve ser canônica, tipada corretamente e pronta para produção.
- Preferir tipagem explícita, generics corretos, diamond operator e narrowing canônico com `Class<E>` quando aplicável.

## Fora de Escopo

- `Playable.java`
- `Player.java`
- `NpcInstance.java:L195`
- `Residence.java`
- `GlobalEvent.java`
- `SiegeEvent.java`
- `NpcTemplate.java`
- `DoorTemplate.java`
- qualquer `deprecation` estrutural

## Entrega Esperada

- Arquivos alterados.
- Resumo técnico das trocas.
- Delta literal do backlog `unchecked`.
- Confirmação exata dos warnings eliminados.
- Checagem por nome de arquivo no log final.
- Riscos remanescentes.
- Declaração explícita de que não foram usados fallback, suppression, ignore ou mascaramento do compilador.

## Critério de Aceitação

- Build verde com JDK 25.
- Zero warnings residuais nos arquivos tocados do lote.
- Nenhuma supressão artificial.
- Diff pequeno, cirúrgico e coerente.
- Solução pronta para produção.
