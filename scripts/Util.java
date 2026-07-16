import l2.gameserver.Config;
import l2.gameserver.cache.Msg;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExPledgeCrestLarge;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SystemMessage2;
import l2.gameserver.service.teleport.GatekeeperTeleportService;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.utils.CapchaUtil;
import l2.gameserver.utils.Location;

public class Util extends Functions
{
	private static final int BaseCapchaCID = 67108864;
	
	public static void RequestCapcha(String onSuccess, Long store_id, Integer time)
	{
		Player player = GameObjectsStorage.getAsPlayer(store_id);
		if(player == null || !player.isOnline() || player.isLogoutStarted())
		{
			return;
		}
		int capcha = CapchaUtil.RndCapcha();
		int bgcolor = CapchaUtil.RndRGB888Color();
		int cid = CapchaUtil.getId(capcha) | 67108864;
		byte[] img = CapchaUtil.getCapchaImage(capcha, bgcolor);
		player.sendPacket(new ExPledgeCrestLarge(cid, img));
		NpcHtmlMessage html = new NpcHtmlMessage(player, null);
		html.setFile("capcha.htm");
		html = html.replace("%SN%", String.valueOf(Config.REQUEST_ID));
		html = html.replace("%CID%", String.valueOf(cid));
		player.setVar("capacha-code", String.valueOf(capcha), -1);
		player.setVar("capacha-time", String.valueOf(System.currentTimeMillis() / 1000 + (long) time.intValue()), -1);
		player.setVar("capacha-success", onSuccess, -1);
		player.sendPacket(html);
		player.sendMessage(new CustomMessage("scripts.Util.CapchaConfirm.RequestCapcha", player, time));
	}
	
	public void Gatekeeper(String[] param)
	{
		if(param.length < 4)
		{
			throw new IllegalArgumentException();
		}
		Player player = getSelf();
		if(player == null)
		{
			return;
		}
		Location destination = new Location(Integer.parseInt(param[0]), Integer.parseInt(param[1]), Integer.parseInt(param[2]));
		long price = Long.parseLong(param[param.length - 1]);
		int castleId = param.length > 4 ? Integer.parseInt(param[3]) : 0;
		GatekeeperTeleportService service = GatekeeperTeleportService.getInstance();
		service.teleport(service.createGatekeeperRequest(player, player.getLastNpc(), destination, price, castleId));
	}
	
	public void SSGatekeeper(String[] param)
	{
		if(param.length < 4)
		{
			throw new IllegalArgumentException();
		}
		Player player = getSelf();
		if(player == null)
		{
			return;
		}
		int type = Integer.parseInt(param[3]);
		if(!NpcInstance.canBypassCheck(player, player.getLastNpc()))
		{
			return;
		}
		if(Config.ALT_ENABLE_SEVEN_SING_TELEPORTER_PROTECTION && type > 0)
		{
			int player_cabal = SevenSigns.getInstance().getPlayerCabal(player);
			int period = SevenSigns.getInstance().getCurrentPeriod();
			if(period == 1 && player_cabal == 0)
			{
				player.sendPacket(Msg.USED_ONLY_DURING_A_QUEST_EVENT_PERIOD);
				return;
			}
			int winner;
			if(period == 3 && (winner = SevenSigns.getInstance().getCabalHighestScore()) != 0)
			{
				if(winner != player_cabal)
				{
					return;
				}
				if(type == 1 && SevenSigns.getInstance().getSealOwner(1) != player_cabal)
				{
					return;
				}
				if(type == 2 && SevenSigns.getInstance().getSealOwner(2) != player_cabal)
				{
					return;
				}
			}
		}
		player.teleToLocation(Integer.parseInt(param[0]), Integer.parseInt(param[1]), Integer.parseInt(param[2]));
	}
	
	public void QuestGatekeeper(String[] param)
	{
		if(param.length < 5)
		{
			throw new IllegalArgumentException();
		}
		Player player = getSelf();
		if(player == null)
		{
			return;
		}
		Location destination = new Location(Integer.parseInt(param[0]), Integer.parseInt(param[1]), Integer.parseInt(param[2]));
		long count = Long.parseLong(param[3]);
		int item = Integer.parseInt(param[4]);
		GatekeeperTeleportService service = GatekeeperTeleportService.getInstance();
		service.teleport(service.createQuestGatekeeperRequest(player, player.getLastNpc(), destination, item, count));
	}
	
	public void ReflectionGatekeeper(String[] param)
	{
		if(param.length < 5)
		{
			throw new IllegalArgumentException();
		}
		Player player = getSelf();
		if(player == null)
		{
			return;
		}
		player.setReflection(Integer.parseInt(param[4]));
		Gatekeeper(param);
	}
	
	public void TokenJump(String[] param)
	{
		Player player = getSelf();
		if(player == null)
		{
			return;
		}
		if(player.getLevel() <= 19)
		{
			QuestGatekeeper(param);
		}
		else
		{
			show("Only for newbies", player);
		}
	}
	
	public void NoblessTeleport()
	{
		Player player = getSelf();
		if(player == null)
		{
			return;
		}
		if(player.isNoble() || Config.ALLOW_NOBLE_TP_TO_ALL)
		{
			show("scripts/noble.htm", player);
		}
		else
		{
			show("scripts/nobleteleporter-no.htm", player);
		}
	}
	
	public void PayPage(String[] param)
	{
		if(param.length < 2)
		{
			throw new IllegalArgumentException();
		}
		Player player = getSelf();
		if(player == null)
		{
			return;
		}
		String page = param[0];
		int item = Integer.parseInt(param[1]);
		long price = Long.parseLong(param[2]);
		if(getItemCount(player, item) < price)
		{
			player.sendPacket(item == 57 ? SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA : SystemMsg.INCORRECT_ITEM_COUNT);
			return;
		}
		removeItem(player, item, price);
		show(page, player);
	}
	
	public void MakeEchoCrystal(String[] param)
	{
		if(param.length < 2)
		{
			throw new IllegalArgumentException();
		}
		Player player = getSelf();
		if(player == null)
		{
			return;
		}
		if(!NpcInstance.canBypassCheck(player, player.getLastNpc()))
		{
			return;
		}
		int crystal = Integer.parseInt(param[0]);
		int score = Integer.parseInt(param[1]);
		if(crystal < 4411 || crystal > 4417)
		{
			return;
		}
		if(getItemCount(player, score) == 0)
		{
			player.getLastNpc().onBypassFeedback(player, "Chat 1");
			return;
		}
		if(getItemCount(player, 57) < 200)
		{
			player.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
			return;
		}
		removeItem(player, 57, (long) 200);
		addItem(player, crystal, (long) 1);
	}
	
	public void TakeNewbieWeaponCoupon()
	{
		Player player = getSelf();
		if(player == null)
		{
			return;
		}
		if(!Config.ALT_ALLOW_SHADOW_WEAPONS)
		{
			show(new CustomMessage("common.Disabled", player), player);
			return;
		}
		if(player.getLevel() > 19 || player.getClassId().getLevel() > 1)
		{
			show("Your level is too high!", player);
			return;
		}
		if(player.getLevel() < 6)
		{
			show("Your level is too low!", player);
			return;
		}
		if(player.getVarB("newbieweapon"))
		{
			show("Your already got your newbie weapon!", player);
			return;
		}
		addItem(player, 7832, (long) 5);
		player.setVar("newbieweapon", "true", -1);
	}
	
	public void TakeAdventurersArmorCoupon()
	{
		Player player = getSelf();
		if(player == null)
		{
			return;
		}
		if(!Config.ALT_ALLOW_SHADOW_WEAPONS)
		{
			show(new CustomMessage("common.Disabled", player), player);
			return;
		}
		if(player.getLevel() > 39 || player.getClassId().getLevel() > 2)
		{
			show("Your level is too high!", player);
			return;
		}
		if(player.getLevel() < 20 || player.getClassId().getLevel() < 2)
		{
			show("Your level is too low!", player);
			return;
		}
		if(player.getVarB("newbiearmor"))
		{
			show("Your already got your newbie weapon!", player);
			return;
		}
		addItem(player, 7833, (long) 1);
		player.setVar("newbiearmor", "true", -1);
	}
	
	public void enter_dc()
	{
		Player player = getSelf();
		NpcInstance npc = getNpc();
		if(player == null || npc == null)
		{
			return;
		}
		if(!NpcInstance.canBypassCheck(player, npc))
		{
			return;
		}
		player.setVar("DCBackCoords", player.getLoc().toXYZString(), -1);
		player.teleToLocation(-114582, -152635, -6742);
	}
	
	public void exit_dc()
	{
		Player player = getSelf();
		NpcInstance npc = getNpc();
		if(player == null || npc == null)
		{
			return;
		}
		if(!NpcInstance.canBypassCheck(player, npc))
		{
			return;
		}
		String var = player.getVar("DCBackCoords");
		if(var == null || var.isEmpty())
		{
			player.teleToLocation(new Location(43768, -48232, -800), 0);
			return;
		}
		player.teleToLocation(Location.parseLoc(var), 0);
		player.unsetVar("DCBackCoords");
	}
	
	public void CapchaConfirm(String[] param)
	{
		if(param.length < 1)
		{
			throw new IllegalArgumentException();
		}
		Player player = getSelf();
		if(player == null)
		{
			return;
		}
		String scapcha = player.getVar("capacha-code");
		String sendtime = player.getVar("capacha-time");
		String ssuccess = player.getVar("capacha-success");
		if(scapcha == null || sendtime == null || ssuccess == null)
		{
			return;
		}
		try
		{
			long endtime = Long.parseLong(sendtime);
			String code = param[0];
			if(endtime < System.currentTimeMillis() / 1000)
			{
				player.sendMessage(new CustomMessage("scripts.Util.CapchaConfirm.TimeExpired", player));
				return;
			}
			int capcha = Integer.parseInt(scapcha);
			if(!CapchaUtil.IsValidEntry(capcha, code))
			{
				player.sendMessage(new CustomMessage("scripts.Util.CapchaConfirm.WrongCode", player));
				return;
			}
			Scripts.getInstance().callScripts(player, ssuccess.split(":")[0], ssuccess.split(":")[1]);
			player.sendMessage(new CustomMessage("scripts.Util.CapchaConfirm.Success", player));
		}
		catch(Exception e)
		{
			
		}
	}
}
