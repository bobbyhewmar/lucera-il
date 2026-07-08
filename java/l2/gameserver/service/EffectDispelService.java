package l2.gameserver.service;

import l2.gameserver.model.DisplayedEffect;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Skill;

public class EffectDispelService
{
	private static final EffectDispelService _instance = new EffectDispelService();

	public static EffectDispelService getInstance()
	{
		return _instance;
	}

	public DispelResult dispelSelfEffect(Playable target, DisplayedEffect displayedEffect)
	{
		for(Effect effect : target.getEffectList().getAllEffects())
		{
			if(!displayedEffect.matches(effect))
			{
				continue;
			}

			if(!canDispel(effect))
			{
				return DispelResult.NOT_DISPELLABLE;
			}

			effect.exit();
			return DispelResult.DISPELLED;
		}

		return DispelResult.NOT_FOUND;
	}

	public boolean canDispel(Effect effect)
	{
		Skill skill = effect.getSkill();
		return effect.isCancelable() && !effect.isOffensive() && !skill.isMusic() && skill.isSelfDispellable() && skill.getSkillType() != Skill.SkillType.TRANSFORMATION;
	}

	public enum DispelResult
	{
		DISPELLED,
		NOT_FOUND,
		NOT_DISPELLABLE
	}
}
