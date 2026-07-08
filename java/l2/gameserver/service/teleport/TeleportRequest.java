package l2.gameserver.service.teleport;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public class TeleportRequest
{
	private final Player _player;
	private final NpcInstance _npc;
	private final Location _destination;
	private final int _itemId;
	private final long _price;
	private final int _minLevel;
	private final int _maxLevel;
	private final int _castleId;
	private final boolean _checkNpcBypass;
	private final boolean _checkGatekeeperNpcConditions;
	private final boolean _checkGatekeeperRestrictions;
	private final int _randomOffsetMin;
	private final int _randomOffsetMax;

	public TeleportRequest(Player player, NpcInstance npc, Location destination, int itemId, long price, int minLevel, int maxLevel, int castleId, boolean checkNpcBypass, boolean checkGatekeeperNpcConditions, boolean checkGatekeeperRestrictions, int randomOffsetMin, int randomOffsetMax)
	{
		_player = player;
		_npc = npc;
		_destination = destination;
		_itemId = itemId;
		_price = price;
		_minLevel = minLevel;
		_maxLevel = maxLevel;
		_castleId = castleId;
		_checkNpcBypass = checkNpcBypass;
		_checkGatekeeperNpcConditions = checkGatekeeperNpcConditions;
		_checkGatekeeperRestrictions = checkGatekeeperRestrictions;
		_randomOffsetMin = randomOffsetMin;
		_randomOffsetMax = randomOffsetMax;
	}

	public Player getPlayer()
	{
		return _player;
	}

	public NpcInstance getNpc()
	{
		return _npc;
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

	public boolean isCheckNpcBypass()
	{
		return _checkNpcBypass;
	}

	public boolean isCheckGatekeeperNpcConditions()
	{
		return _checkGatekeeperNpcConditions;
	}

	public boolean isCheckGatekeeperRestrictions()
	{
		return _checkGatekeeperRestrictions;
	}

	public int getRandomOffsetMin()
	{
		return _randomOffsetMin;
	}

	public int getRandomOffsetMax()
	{
		return _randomOffsetMax;
	}
}
