package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.DisplayedEffect;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.service.EffectDispelService;

public class RequestDispel extends L2GameClientPacket
{
	private int _objectId;
	private int _id;
	private int _level;
	
	@Override
	protected void readImpl() throws Exception
	{
		_objectId = readD();
		_id = readD();
		_level = readD();
	}
	
	@Override
	protected void runImpl() throws Exception
	{
		Player activeChar = getClient().getActiveChar();
		if(activeChar == null || activeChar.getObjectId() != _objectId && activeChar.getPet() == null)
		{
			return;
		}
		Playable target = activeChar;
		if(activeChar.getObjectId() != _objectId)
		{
			target = activeChar.getPet();
		}
		DisplayedEffect displayedEffect = new DisplayedEffect(_id, _level);
		EffectDispelService.DispelResult result = EffectDispelService.getInstance().dispelSelfEffect(target, displayedEffect);
		if(result == EffectDispelService.DispelResult.NOT_FOUND)
		{
			activeChar.sendPacket(new SystemMessage(92).addSkillName(displayedEffect.getDisplayId(), displayedEffect.getDisplayLevel()));
		}
	}
}
