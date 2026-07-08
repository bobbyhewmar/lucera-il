package l2.gameserver.network.l2.bypass;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.bypass.handler.ClientDispelBypassHandler;
import l2.gameserver.network.l2.bypass.handler.ClientGotoBypassHandler;

public class ClientBypassDispatcher
{
	private static final ClientBypassDispatcher _instance = new ClientBypassDispatcher();
	private final ClientBypassHandler[] _handlers = {new ClientDispelBypassHandler(), new ClientGotoBypassHandler()};

	public static ClientBypassDispatcher getInstance()
	{
		return _instance;
	}

	public boolean supports(String bypass)
	{
		for(ClientBypassHandler handler : _handlers)
		{
			if(handler.supports(bypass))
			{
				return true;
			}
		}

		return false;
	}

	public boolean handle(Player player, String bypass)
	{
		for(ClientBypassHandler handler : _handlers)
		{
			if(!handler.supports(bypass))
			{
				continue;
			}

			return handler.handle(player, bypass);
		}

		return false;
	}
}
