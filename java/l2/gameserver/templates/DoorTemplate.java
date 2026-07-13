package l2.gameserver.templates;

import l2.commons.geometry.Polygon;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.ai.DoorAI;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

public class DoorTemplate extends CharTemplate
{
	public static final Constructor<? extends DoorAI> DEFAULT_AI_CONSTRUCTOR = resolveAIConstructor(DoorAI.class);
	private static final Logger _log = LoggerFactory.getLogger(DoorTemplate.class);
	private final int _id;
	private final String _name;
	private final DoorType _doorType;
	private final boolean _unlockable;
	private final boolean _isHPVisible;
	private final boolean _opened;
	private final boolean _targetable;
	private final Polygon _polygon;
	private final Location _loc;
	private final int _key;
	private final int _openTime;
	private final int _rndTime;
	private final int _closeTime;
	private final int _masterDoor;
	private final StatsSet _aiParams;
	private Class<? extends DoorAI> _classAI = DoorAI.class;
	private Constructor<? extends DoorAI> _constructorAI = DEFAULT_AI_CONSTRUCTOR;
	
	public DoorTemplate(StatsSet set)
	{
		super(set);
		_id = set.getInteger("uid");
		_name = set.getString("name");
		_doorType = set.getEnum("door_type", DoorType.class, DoorType.DOOR);
		_unlockable = set.getBool("unlockable", false);
		_isHPVisible = set.getBool("show_hp", false);
		_opened = set.getBool("opened", false);
		_targetable = set.getBool("targetable", true);
		_loc = (Location) set.get("pos");
		_polygon = (Polygon) set.get("shape");
		_key = set.getInteger("key", 0);
		_openTime = set.getInteger("open_time", 0);
		_rndTime = set.getInteger("random_time", 0);
		_closeTime = set.getInteger("close_time", 0);
		_masterDoor = set.getInteger("master_door", 0);
		_aiParams = (StatsSet) set.getObject("ai_params", StatsSet.EMPTY);
		setAI(set.getString("ai", "DoorAI"));
	}
	
	private void setAI(String ai)
	{
		Class<?> classAI;
		try
		{
			classAI = Class.forName("l2.gameserver.ai." + ai);
		}
		catch(ClassNotFoundException e)
		{
			classAI = Scripts.getInstance().getClasses().get("ai.door." + ai);
		}
		if(classAI == null)
		{
			_log.error("Not found ai class for ai: " + ai + ". DoorId: " + _id);
		}
		else
		{
			_classAI = classAI.asSubclass(DoorAI.class);
			_constructorAI = resolveAIConstructor(_classAI);
		}
		if(_classAI.isAnnotationPresent(Deprecated.class))
		{
			_log.error("Ai type: " + ai + ", is deprecated. DoorId: " + _id);
		}
	}
	
	public CharacterAI getNewAI(DoorInstance door)
	{
		try
		{
			return _constructorAI.newInstance(door);
		}
		catch(Exception e)
		{
			_log.error("Unable to create ai of doorId " + _id, e);
			return new DoorAI(door);
		}
	}
	
	@Override
	public int getNpcId()
	{
		return _id;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public DoorType getDoorType()
	{
		return _doorType;
	}
	
	public boolean isUnlockable()
	{
		return _unlockable;
	}
	
	public boolean isHPVisible()
	{
		return _isHPVisible;
	}
	
	public Polygon getPolygon()
	{
		return _polygon;
	}
	
	public int getKey()
	{
		return _key;
	}
	
	public boolean isOpened()
	{
		return _opened;
	}
	
	public Location getLoc()
	{
		return _loc;
	}
	
	public int getOpenTime()
	{
		return _openTime;
	}
	
	public int getRandomTime()
	{
		return _rndTime;
	}
	
	public int getCloseTime()
	{
		return _closeTime;
	}
	
	public boolean isTargetable()
	{
		return _targetable;
	}
	
	public int getMasterDoor()
	{
		return _masterDoor;
	}
	
	public StatsSet getAIParams()
	{
		return _aiParams;
	}

	private static Constructor<? extends DoorAI> resolveAIConstructor(Class<? extends DoorAI> type)
	{
		for(Constructor<?> constructor : type.getConstructors())
		{
			Class<?>[] parameterTypes = constructor.getParameterTypes();
			if(parameterTypes.length == 1 && parameterTypes[0].isAssignableFrom(DoorInstance.class))
			{
				try
				{
					return type.getConstructor(parameterTypes);
				}
				catch(NoSuchMethodException e)
				{
					throw new IllegalStateException("Missing compatible door AI constructor for " + type.getName(), e);
				}
			}
		}
		throw new IllegalStateException("Missing compatible door AI constructor for " + type.getName());
	}
	
	
	public enum DoorType
	{
		DOOR,
		WALL;
	}
}
