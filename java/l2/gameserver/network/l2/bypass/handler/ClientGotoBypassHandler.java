package l2.gameserver.network.l2.bypass.handler;

import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ClientTeleportHolder;
import l2.gameserver.model.ClientTeleportData;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.bypass.ClientBypassHandler;
import l2.gameserver.service.teleport.GatekeeperTeleportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientGotoBypassHandler implements ClientBypassHandler
{
	private static final Logger _log = LoggerFactory.getLogger(ClientGotoBypassHandler.class);
	private static final String PREFIX = "goto ";

	@Override
	public boolean supports(String bypass)
	{
		return bypass.startsWith(PREFIX);
	}

	@Override
	public boolean handle(Player player, String bypass)
	{
		if(!Config.ALLOW_CLIENT_BYPASS_GOTO)
		{
			return true;
		}

		Integer teleportId = parse(bypass);
		if(teleportId == null)
		{
			return true;
		}

		ClientTeleportData teleportData = ClientTeleportHolder.getInstance().getTeleport(teleportId);
		if(teleportData == null)
		{
			_log.warn("Unknown client goto teleport id {} requested by {}", teleportId, player);
			return true;
		}

		GatekeeperTeleportService.getInstance().teleport(GatekeeperTeleportService.getInstance().createClientRequest(player, teleportData));
		return true;
	}

	private Integer parse(String bypass)
	{
		try
		{
			return Integer.parseInt(bypass.substring(PREFIX.length()).trim());
		}
		catch(NumberFormatException e)
		{
			return null;
		}
	}
}
