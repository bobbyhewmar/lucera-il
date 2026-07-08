PADRAO DE REGISTRO
- Cada entrega tecnica deve possuir um documento .md em docs/changes/ e atualizar este arquivo.
- Use linhas curtas no formato [FIX], [ADD], [REF], [INFRA] e [DOC].

CORRECOES
- [FIX] Carregamento de geodata `.l2g` (algumas zonas nao eram carregadas e geravam erros).

REFATORACOES
- [REF] Loader dedicado `GeoRegionLoader` para decode e parse da geodata.

IMPLEMENTACOES
- [ADD] Padrao de documentacao por entrega adicionado ao projeto.
- [ADD] `AutoLearnSkillsMaxLevel` (adiciona level maximo ao ganhar skills automaticamente).

INFRAESTRUTURA
- [INFRA] Stack Docker local com MariaDB 11.4, AuthServer, GameServer e bootstrap automatico do banco.

DOCUMENTACAO
- [DOC] Padrao de entregas registrado em `docs/PADRAO-ENTREGAS.md`.
- [DOC] Registro detalhado das entregas salvo em `docs/changes/`.
- [DOC] Guia rapido Docker atualizado para execucao local simples e rapida.
- [DOC] `README.md` atualizado com links para a documentacao tecnica.
