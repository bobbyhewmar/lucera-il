CORRECOES
- [FIX] Carregamento de geodata `.l2g` (algumas zonas nao eram carregadas e geravam erros).

REFATORACOES
- [REF] Loader dedicado `GeoRegionLoader` para decode e parse da geodata.

IMPLEMENTACOES
- [ADD] `AutoLearnSkillsMaxLevel` (adiciona level maximo ao ganhar skills automaticamente).

INFRAESTRUTURA
- [INFRA] Stack Docker local com MariaDB 11.4, AuthServer, GameServer e bootstrap automatico do banco.
