# Correcao e refatoracao do loader de geodata `.l2g` da Lucera

## Contexto

O projeto utiliza geodata `.l2g` da propria linha Lucera como formato nativo e atual. O carregamento dessas regioes precisava de um fluxo mais confiavel, com validacao estrutural clara e diagnostico detalhado para falhas reais.

## Objetivo

Corrigir e tornar mais robusto o carregamento da geodata `.l2g` da Lucera, sem tratar o formato como legado e sem depender de mensagens genericas para falha de leitura.

## Arquivos alterados

- `java/l2/gameserver/geodata/GeoRegionLoader.java`
- `java/l2/gameserver/geodata/GeoEngine.java`

## Comportamento anterior

O `GeoEngine` concentrava internamente a leitura, decriptacao e parse de geodata, com diagnostico limitado e pouca separacao entre etapas de:

- abertura do arquivo
- decode do payload
- validacao de checksum
- parse dos blocos geodata

## Comportamento novo

Foi criado um loader dedicado para geodata:

- `GeoRegionLoader` centraliza o carregamento e parse do conteudo
- o loader reconhece explicitamente `.l2g` Lucera e `.l2j` plain
- o decode do `.l2g` foi isolado e passou a validar header, checksum e tamanho minimo
- o parse passou a validar limites de leitura por bloco e por celula multilevel
- erros estruturais agora retornam mensagem tecnica precisa com contexto como arquivo, offset, block index, block type, layers ou trailing bytes

No `GeoEngine`:

- o carregamento passou a consumir o resultado estruturado do `GeoRegionLoader`
- o `MAX_LAYERS` passa a ser atualizado a partir do resultado parseado
- falhas de formato retornam rejeicao controlada da regiao, com diagnostico mais preciso

## Configuracoes envolvidas

Nenhuma configuracao de runtime foi alterada nesta entrega.

O projeto continua configurado para `.l2g` em:

- `dist/gameserver/config/geodata.properties`

## Validacao executada

- conferencia manual do uso de `GeoRegionLoader` dentro de `GeoEngine`
- conferencia do suporte explicito ao formato `.l2g` Lucera
- conferencia das validacoes de checksum, truncamento e bytes excedentes

## Riscos, limites ou pendencias

- arquivos realmente invalidos continuarao sendo rejeitados
- se surgir outra variante real de `.l2g` dentro do ecossistema Lucera, ela deve receber suporte explicito no loader
- nesta entrega foi registrada a mudanca e validado o fluxo por inspecao de codigo; nao foi executado boot completo do gameserver nesta etapa documental
