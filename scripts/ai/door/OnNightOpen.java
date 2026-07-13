package ai.door;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.GameTimeController;
import l2.gameserver.ai.DoorAI;
import l2.gameserver.listener.game.OnDayNightChangeListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.DoorInstance;

public class OnNightOpen extends DoorAI
{
	public OnNightOpen(DoorInstance actor)
	{
		super(actor);
		GameTimeController.getInstance().addListener(new NightDoorOpenController(actor));
	}
	
	@Override
	public boolean isGlobalAI()
	{
		return true;
	}
	
	private static class NightDoorOpenController implements OnDayNightChangeListener
	{
		private final HardReference<? extends Creature> _actRef;
		
		public NightDoorOpenController(DoorInstance actor)
		{
			_actRef = actor.getRef();
		}
		
		@Override
		public void onDay()
		{
		}
		
		@Override
		public void onNight()
		{
			Creature creature = _actRef.get();
			if(creature instanceof DoorInstance door)
			{
				door.openMe();
				_log.info("Zaken door is opened for 5 min.");
			}
			else
			{
				_log.warn("Zaken door is null");
			}
		}
	}
}
