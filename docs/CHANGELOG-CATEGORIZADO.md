CORRECOES
- [FIX] Carregamento de geodata `.l2g` (algumas zonas nao eram carregadas e geravam erros).

REFATORACOES
- [REF] Loader dedicado `GeoRegionLoader` para decode e parse da geodata.
- [REF] Configs `Npc*` centralizadas em `formulas.properties`, com remocao da rota paralela `Npcs/npcs.properties` e normalizacao da chave `NpcMaxMpModifier`.

IMPLEMENTACOES
- [ADD] `AutoLearnSkillsMaxLevel` (adiciona level maximo ao ganhar skills automaticamente).
- [ADD] Teleporte via client-side com bypass `_goto`, com resolucao orientada a dados e execucao centralizada no servidor.
- [ADD] Remocao de buffs via `_dispel` (Alt+Click), alinhada ao comportamento das cronicas mais novas do jogo.

INFRAESTRUTURA
- [INFRA] Stack Docker local com MariaDB 11.4, AuthServer, GameServer e bootstrap automatico do banco.

DOCUMENTACAO
- [DOC] Roadmap arquitetural por fases e master prompt de evolucao do projeto.
