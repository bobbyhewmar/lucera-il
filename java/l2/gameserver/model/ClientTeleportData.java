package l2.gameserver.model;

import l2.gameserver.utils.Location;

public class ClientTeleportData
{
	private final int _id;
	private final Location _destination;
	private final int _itemId;
	private final long _price;
	private final int _minLevel;
	private final int _maxLevel;
	private final int _castleId;
	private final boolean _gatekeeperRestrictions;

	public ClientTeleportData(int id, Location destination, int itemId, long price, int minLevel, int maxLevel, int castleId, boolean gatekeeperRestrictions)
	{
		_id = id;
		_destination = destination;
		_itemId = itemId;
		_price = price;
		_minLevel = minLevel;
		_maxLevel = maxLevel;
		_castleId = castleId;
		_gatekeeperRestrictions = gatekeeperRestrictions;
	}

	public int getId()
	{
		return _id;
	}

	public Location getDestination()
	{
		return _destination;
	}

	public int getItemId()
	{
		return _itemId;
	}

	public long getPrice()
	{
		return _price;
	}

	public int getMinLevel()
	{
		return _minLevel;
	}

	public int getMaxLevel()
	{
		return _maxLevel;
	}

	public int getCastleId()
	{
		return _castleId;
	}

	public boolean isGatekeeperRestrictions()
	{
		return _gatekeeperRestrictions;
	}
}
