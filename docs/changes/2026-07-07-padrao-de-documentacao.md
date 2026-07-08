# Padrao de documentacao e changelog categorizado

## Contexto

Foi solicitado um padrao permanente para que toda correcao, refatoracao ou implementacao gere documentacao em `.md` e um registro simples em `.txt`, separado por categorias.

## Objetivo

Formalizar esse fluxo dentro do repositorio para que as proximas entregas sigam o mesmo processo sem depender de combinacao manual.

## Arquivos alterados

- `docs/PADRAO-ENTREGAS.md`
- `docs/CHANGELOG-CATEGORIZADO.txt`
- `README.md`

## Comportamento anterior

O projeto nao possuia uma convencao registrada no repositorio para documentar cada entrega tecnica nem um arquivo cumulativo simples por categorias.

## Comportamento novo

Cada entrega tecnica do projeto passa a ter:

- um documento dedicado em `docs/changes/`
- um registro cumulativo em `docs/CHANGELOG-CATEGORIZADO.txt`

Tambem foi adicionada uma referencia no `README.md` para facilitar o acesso rapido a esses arquivos.

## Configuracoes envolvidas

Nenhuma configuracao de runtime, build ou banco foi alterada.

## Validacao executada

- conferencia manual dos arquivos criados
- conferencia do caminho padrao para os proximos registros
- conferencia dos links adicionados ao `README.md`

## Riscos, limites ou pendencias

- o padrao depende de manutencao disciplinada nas proximas entregas
- sempre que houver mudanca real no repositorio, o `.md` e o `.txt` devem ser atualizados juntos
