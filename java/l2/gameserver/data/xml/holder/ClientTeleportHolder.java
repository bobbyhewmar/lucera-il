package l2.gameserver.data.xml.holder;

import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.ClientTeleportData;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.HashIntObjectMap;

public class ClientTeleportHolder extends AbstractHolder
{
	private static final ClientTeleportHolder _instance = new ClientTeleportHolder();
	private final IntObjectMap<ClientTeleportData> _teleports = new HashIntObjectMap<>();

	public static ClientTeleportHolder getInstance()
	{
		return _instance;
	}

	public void addTeleport(ClientTeleportData teleportData)
	{
		_teleports.put(teleportData.getId(), teleportData);
	}

	public ClientTeleportData getTeleport(int id)
	{
		return _teleports.get(id);
	}

	@Override
	public int size()
	{
		return _teleports.size();
	}

	@Override
	public void clear()
	{
		_teleports.clear();
	}
}
