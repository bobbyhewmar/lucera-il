# Padrao de Entregas e Registro de Mudancas

Este repositorio passa a seguir este padrao para toda correcao, refatoracao ou implementacao feita no projeto.

## Regra base

- Toda entrega tecnica deve gerar um documento `.md` dedicado em `docs/changes/`.
- Toda entrega tecnica deve atualizar `docs/CHANGELOG-CATEGORIZADO.txt`.
- O registro vale para correcoes, refatoracoes, implementacoes, infraestrutura e documentacao tecnica relacionadas a mudancas reais no repositorio.

## Estrutura obrigatoria do documento `.md`

Nome recomendado:

`docs/changes/YYYY-MM-DD-resumo-curto.md`

Conteudo minimo:

- contexto
- objetivo
- arquivos alterados
- comportamento anterior
- comportamento novo
- configuracoes envolvidas
- validacao executada
- riscos, limites ou pendencias

## Estrutura obrigatoria do arquivo `.txt`

O arquivo `docs/CHANGELOG-CATEGORIZADO.txt` deve ser atualizado por categorias.

Categorias padrao:

- Correcoes
- Refatoracoes
- Implementacoes
- Infraestrutura
- Documentacao

Formato recomendado para cada linha:

- `[FIX] resumo curto da correcao`
- `[ADD] resumo curto da implementacao`
- `[REF] resumo curto da refatoracao`
- `[INFRA] resumo curto da mudanca de infraestrutura`
- `[DOC] resumo curto da documentacao`

## Regras de manutencao

- Nao remover registros antigos sem motivo tecnico claro.
- Preferir frases curtas, diretas e prontas para postagem em forum.
- Se uma mesma entrega se encaixar em mais de uma categoria, registrar em todas as categorias relevantes.
- Se o trabalho for apenas analise, conversa ou prompt sem alteracao real no repositorio, nao ha obrigacao de criar registro.

## Objetivo do padrao

Garantir historico tecnico simples, rastreavel e consistente para as proximas entregas do projeto.
