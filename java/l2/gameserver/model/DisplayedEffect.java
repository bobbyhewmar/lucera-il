package l2.gameserver.model;

public class DisplayedEffect
{
	private final int _displayId;
	private final int _displayLevel;

	public DisplayedEffect(int displayId, int displayLevel)
	{
		_displayId = displayId;
		_displayLevel = displayLevel;
	}

	public int getDisplayId()
	{
		return _displayId;
	}

	public int getDisplayLevel()
	{
		return _displayLevel;
	}

	public boolean matches(Effect effect)
	{
		return effect.getDisplayId() == _displayId && effect.getDisplayLevel() == _displayLevel;
	}
}
