package l2.gameserver.data.xml.holder;

import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.EventType;
import l2.gameserver.model.entity.events.GlobalEvent;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.TreeIntObjectMap;

public final class EventHolder extends AbstractHolder
{
	private static final EventHolder _instance = new EventHolder();
	private final IntObjectMap<GlobalEvent> _events = new TreeIntObjectMap<>();
	
	public static EventHolder getInstance()
	{
		return _instance;
	}
	
	public void addEvent(EventType type, GlobalEvent event)
	{
		_events.put(type.step() + event.getId(), event);
	}
	
	public GlobalEvent getEvent(EventType type, int id)
	{
		return _events.get(type.step() + id);
	}

	public <E extends GlobalEvent> E getEvent(EventType type, int id, Class<E> typeClass)
	{
		return typeClass.cast(getEvent(type, id));
	}
	
	public void findEvent(Player player)
	{
		for(GlobalEvent event : _events.values())
		{
			if(!event.isParticle(player))
				continue;
			player.addEvent(event);
		}
	}
	
	public void callInit()
	{
		for(GlobalEvent event : _events.values())
		{
			event.initEvent();
		}
	}
	
	@Override
	public int size()
	{
		return _events.size();
	}
	
	@Override
	public void clear()
	{
		_events.clear();
	}
}
