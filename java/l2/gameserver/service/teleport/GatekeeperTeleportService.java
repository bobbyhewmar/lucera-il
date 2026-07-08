package l2.gameserver.service.teleport;

import l2.gameserver.Config;
import l2.gameserver.cache.Msg;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.ClientTeleportData;
import l2.gameserver.model.Player;
import l2.gameserver.model.TeleportLocation;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;

public class GatekeeperTeleportService
{
	private static final GatekeeperTeleportService _instance = new GatekeeperTeleportService();

	public static GatekeeperTeleportService getInstance()
	{
		return _instance;
	}

	public TeleportRequest createGatekeeperRequest(Player player, NpcInstance npc, Location destination, long price, int castleId)
	{
		TeleportLocation teleportLocation = findNpcTeleport(npc, destination);
		return new TeleportRequest(player, npc, destination, 57, price, getMinLevel(teleportLocation), getMaxLevel(teleportLocation), castleId, true, true, true, 50, 100);
	}

	public TeleportRequest createQuestGatekeeperRequest(Player player, NpcInstance npc, Location destination, int itemId, long price)
	{
		TeleportLocation teleportLocation = findNpcTeleport(npc, destination);
		int randomOffsetMin = Config.ALT_SPREADING_AFTER_TELEPORT ? 20 : 0;
		int randomOffsetMax = Config.ALT_SPREADING_AFTER_TELEPORT ? 70 : 0;
		return new TeleportRequest(player, npc, destination, itemId, price, getMinLevel(teleportLocation), getMaxLevel(teleportLocation), 0, true, false, true, randomOffsetMin, randomOffsetMax);
	}

	public TeleportRequest createClientRequest(Player player, ClientTeleportData teleportData)
	{
		int randomOffsetMin = teleportData.isGatekeeperRestrictions() ? 50 : 0;
		int randomOffsetMax = teleportData.isGatekeeperRestrictions() ? 100 : 0;
		return new TeleportRequest(player, null, teleportData.getDestination(), teleportData.getItemId(), teleportData.getPrice(), teleportData.getMinLevel(), teleportData.getMaxLevel(), teleportData.getCastleId(), false, false, teleportData.isGatekeeperRestrictions(), randomOffsetMin, randomOffsetMax);
	}

	public boolean teleport(TeleportRequest request)
	{
		Player player = request.getPlayer();
		if(player == null)
		{
			return false;
		}

		if(request.isCheckNpcBypass() && !NpcInstance.canBypassCheck(player, request.getNpc()))
		{
			return false;
		}

		if(request.isCheckGatekeeperNpcConditions() && !checkGatekeeperNpcConditions(player, request.getNpc()))
		{
			return false;
		}

		if(request.isCheckGatekeeperRestrictions() && !checkGatekeeperRestrictions(request))
		{
			return false;
		}

		if(!consumePayment(player, request.getItemId(), request.getPrice()))
		{
			return false;
		}

		player.teleToLocation(resolveDestination(player, request));
		return true;
	}

	public TeleportLocation findNpcTeleport(NpcInstance npc, Location destination)
	{
		if(npc == null || npc.getTemplate().getTeleportList() == null)
		{
			return null;
		}

		TeleportLocation[][] teleportLocations = (TeleportLocation[][]) npc.getTemplate().getTeleportList().getValues(new TeleportLocation[npc.getTemplate().getTeleportList().size()][]);
		for(TeleportLocation[] teleLocationList : teleportLocations)
		{
			for(TeleportLocation teleportLocation : teleLocationList)
			{
				if(teleportLocation.getX() == destination.getX() && teleportLocation.getY() == destination.getY() && teleportLocation.getZ() == destination.getZ())
				{
					return teleportLocation;
				}
			}
		}

		return null;
	}

	private boolean checkGatekeeperNpcConditions(Player player, NpcInstance npc)
	{
		if(npc == null)
		{
			return true;
		}

		int npcId = npc.getNpcId();
		switch(npcId)
		{
			case 30483:
			{
				if(player.getLevel() <= Config.CRUMA_GATEKEEPER_LVL)
				{
					return true;
				}

				player.sendPacket(new NpcHtmlMessage(player, npc).setFile("teleporter/30483-no.htm"));
				return false;
			}
			case 32864:
			case 32865:
			case 32866:
			case 32867:
			case 32868:
			case 32869:
			case 32870:
			{
				if(player.getLevel() >= 80)
				{
					return true;
				}

				player.sendPacket(new NpcHtmlMessage(player, npc).setFile("teleporter/" + npcId + "-no.htm"));
				return false;
			}
		}

		return true;
	}

	private boolean checkGatekeeperRestrictions(TeleportRequest request)
	{
		Player player = request.getPlayer();
		if(player.getMountType() == 2)
		{
			player.sendPacket(new NpcHtmlMessage(player, request.getNpc()).setFile("scripts/wyvern-no.htm"));
			return false;
		}

		if(request.getMinLevel() > 0 && player.getLevel() < request.getMinLevel())
		{
			player.sendMessage(new CustomMessage("Gatekeeper.LevelToLow", player, request.getMinLevel()));
			return false;
		}

		if(request.getMaxLevel() > 0 && player.getLevel() > request.getMaxLevel())
		{
			player.sendMessage(new CustomMessage("Gatekeeper.LevelToHigh", player, request.getMaxLevel()));
			return false;
		}

		if(!player.getReflection().isDefault())
		{
			return true;
		}

		Castle castle = request.getCastleId() > 0 ? ResidenceHolder.getInstance().getResidence(Castle.class, request.getCastleId()) : null;
		if(castle != null && castle.getSiegeEvent().isInProgress())
		{
			player.sendPacket(Msg.YOU_CANNOT_TELEPORT_TO_A_VILLAGE_THAT_IS_IN_A_SIEGE);
			return false;
		}

		return true;
	}

	private boolean consumePayment(Player player, int itemId, long price)
	{
		if(price <= 0)
		{
			return true;
		}

		if(itemId == 57)
		{
			if(player.getAdena() < price)
			{
				player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
				return false;
			}

			return player.reduceAdena(price, true);
		}

		if(ItemFunctions.getItemCount(player, itemId) < price)
		{
			player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
			return false;
		}

		return ItemFunctions.removeItem(player, itemId, price, true) == price;
	}

	private Location resolveDestination(Player player, TeleportRequest request)
	{
		Location destination = request.getDestination();
		if(request.getRandomOffsetMax() > 0)
		{
			return Location.findPointToStay(destination.getX(), destination.getY(), destination.getZ(), request.getRandomOffsetMin(), request.getRandomOffsetMax(), player.getGeoIndex());
		}

		return destination.clone().correctGeoZ(player.getGeoIndex());
	}

	private int getMinLevel(TeleportLocation teleportLocation)
	{
		return teleportLocation == null ? 0 : teleportLocation.getMinLevel();
	}

	private int getMaxLevel(TeleportLocation teleportLocation)
	{
		return teleportLocation == null ? 0 : teleportLocation.getMaxLevel();
	}
}
