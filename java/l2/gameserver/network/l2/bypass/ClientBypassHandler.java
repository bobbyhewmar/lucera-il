package l2.gameserver.network.l2.bypass;

import l2.gameserver.model.Player;

public interface ClientBypassHandler
{
	boolean supports(String bypass);

	boolean handle(Player player, String bypass);
}
