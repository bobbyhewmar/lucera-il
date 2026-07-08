package l2.gameserver.network.l2.bypass.handler;

import l2.gameserver.Config;
import l2.gameserver.model.DisplayedEffect;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.bypass.ClientBypassHandler;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.service.EffectDispelService;

public class ClientDispelBypassHandler implements ClientBypassHandler
{
	private static final String PREFIX = "_dispel:";

	@Override
	public boolean supports(String bypass)
	{
		return bypass.startsWith(PREFIX);
	}

	@Override
	public boolean handle(Player player, String bypass)
	{
		if(!Config.ALLOW_CLIENT_BYPASS_DISPEL)
		{
			return true;
		}

		DisplayedEffect displayedEffect = parse(bypass);
		if(displayedEffect == null)
		{
			return true;
		}

		EffectDispelService.DispelResult result = EffectDispelService.getInstance().dispelSelfEffect(player, displayedEffect);
		if(result == EffectDispelService.DispelResult.NOT_FOUND)
		{
			player.sendPacket(new SystemMessage(92).addSkillName(displayedEffect.getDisplayId(), displayedEffect.getDisplayLevel()));
		}

		return true;
	}

	private DisplayedEffect parse(String bypass)
	{
		String[] values = bypass.substring(PREFIX.length()).split(",");
		if(values.length != 2)
		{
			return null;
		}

		try
		{
			return new DisplayedEffect(Integer.parseInt(values[0].trim()), Integer.parseInt(values[1].trim()));
		}
		catch(NumberFormatException e)
		{
			return null;
		}
	}
}
