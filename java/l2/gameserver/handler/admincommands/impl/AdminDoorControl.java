package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.network.l2.components.SystemMsg;

public class AdminDoorControl implements IAdminCommandHandler
{
	@Override
	public boolean useAdminCommand(Enum comm, String[] wordList, String fullString, Player activeChar)
	{
		Commands command = (Commands) comm;
		if(!activeChar.getPlayerAccess().Door)
		{
			return false;
		}
		switch(command)
		{
			case admin_open:
			{
				GameObject target = wordList.length > 1 ? World.getAroundObjectById(activeChar, Integer.parseInt(wordList[1])) : activeChar.getTarget();
				if(target != null && target.isDoor())
				{
					((DoorInstance) target).openMe();
					break;
				}
				activeChar.sendPacket(SystemMsg.INVALID_TARGET);
				break;
			}
			case admin_close:
			{
				GameObject target = wordList.length > 1 ? World.getAroundObjectById(activeChar, Integer.parseInt(wordList[1])) : activeChar.getTarget();
				if(target != null && target.isDoor())
				{
					((DoorInstance) target).closeMe();
					break;
				}
				activeChar.sendPacket(SystemMsg.INVALID_TARGET);
			}
		}
		return true;
	}
	
	@Override
	public Enum[] getAdminCommandEnum()
	{
		return Commands.values();
	}
	
	private enum Commands
	{
		admin_open,
		admin_close;
	}
}
