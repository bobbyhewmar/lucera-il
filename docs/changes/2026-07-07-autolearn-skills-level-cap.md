# Implementacao de level cap configuravel no AutoLearnSkills

## Contexto

O sistema `AutoLearnSkills` existia como configuracao booleana simples. Quando ativo, podia aprender automaticamente skills normais sem um limite configuravel de nivel.

## Objetivo

Adicionar um cap configuravel de nivel ao `AutoLearnSkills`, mantendo a mudanca restrita ao proprio fluxo de auto learn e sem alterar os demais sistemas de aprendizado manual ou original.

## Arquivos alterados

- `java/l2/gameserver/Config.java`
- `java/l2/gameserver/model/Player.java`
- `dist/gameserver/config/altsettings.properties`

## Comportamento anterior

Quando `AutoLearnSkills` estava ativo:

- o personagem aprendia automaticamente todas as skills normais elegiveis
- nao existia cap configuravel de nivel
- `AutoLearnForgottenSkills` apenas definia se skills clicadas entravam ou nao no fluxo automatico

## Comportamento novo

Foi adicionado um novo limite configuravel:

- `AutoLearnSkillsMaxLevel`

Padrao aplicado:

- `AutoLearnSkills = True`
- `AutoLearnSkillsMaxLevel = 75`
- `AutoLearnForgottenSkills = False`

O cap e inclusivo:

- skills com `minLevel <= 75` podem entrar no AutoLearn
- skills com `minLevel > 75` nao entram no AutoLearn

O filtro foi mantido exclusivamente no fluxo de `AutoLearnSkills`, dentro de `Player.rewardSkills(boolean send)`, usando um helper local:

- `isAutoLearnSkillEligible(SkillLearn skillLearn)`

Essa decisao usa `SkillLearn.getMinLevel()` para evitar erro quando o personagem sobe varios niveis de uma vez e ultrapassa o cap em um unico ganho de experiencia.

## Configuracoes envolvidas

- `AutoLearnSkills`
- `AutoLearnSkillsMaxLevel`
- `AutoLearnForgottenSkills`

## Validacao executada

- conferencia manual da nova config em `Config.java`
- conferencia da configuracao padrao em `altsettings.properties`
- conferencia do helper local no fluxo de AutoLearn em `Player.java`
- conferencia de que a regra ficou restrita ao branch de AutoLearn

## Riscos, limites ou pendencias

- jogadores acima do cap continuam dependendo do fluxo original/manual para skills acima do limite, como esperado
- se o cap precisar mudar no futuro, basta ajustar `AutoLearnSkillsMaxLevel`
- nesta entrega foi registrada a mudanca existente e validado o fluxo por inspecao de codigo; nao foi executado teste funcional completo nesta etapa documental
