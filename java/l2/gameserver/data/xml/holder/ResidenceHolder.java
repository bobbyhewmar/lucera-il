package l2.gameserver.data.xml.holder;

import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.residence.Residence;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.TreeIntObjectMap;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ResidenceHolder extends AbstractHolder
{
	private static final ResidenceHolder _instance = new ResidenceHolder();
	private final IntObjectMap<Residence> _residences = new TreeIntObjectMap<>();
	private final Map<Class<? extends Residence>, List<Residence>> _fastResidencesByType = new HashMap<>(4);
	
	private ResidenceHolder()
	{
	}
	
	public static ResidenceHolder getInstance()
	{
		return _instance;
	}
	
	public void addResidence(Residence r)
	{
		_residences.put(r.getId(), r);
	}
	
	public Residence getResidence(int id)
	{
		return _residences.get(id);
	}
	
	public <R extends Residence> R getResidence(Class<R> type, int id)
	{
		Residence residence = getResidence(id);
		if(residence == null || residence.getClass() != type)
		{
			return null;
		}
		return type.cast(residence);
	}
	
	public <R extends Residence> List<R> getResidenceList(Class<R> t)
	{
		return typedResidencesView(_fastResidencesByType.get(t), t);
	}
	
	public Collection<Residence> getResidences()
	{
		return _residences.values();
	}
	
	public Residence getResidenceByObject(GameObject object)
	{
		return getResidenceByCoord(object.getX(), object.getY(), object.getZ(), object.getReflection());
	}

	public <R extends Residence> R getResidenceByObject(Class<R> type, GameObject object)
	{
		return getResidenceByCoord(type, object.getX(), object.getY(), object.getZ(), object.getReflection());
	}

	public Residence getResidenceByCoord(int x, int y, int z, Reflection ref)
	{
		for(Residence residence : getResidences())
		{
			if(!residence.checkIfInZone(x, y, z, ref))
				continue;
			return residence;
		}
		return null;
	}
	
	public <R extends Residence> R getResidenceByCoord(Class<R> type, int x, int y, int z, Reflection ref)
	{
		for(R residence : getResidenceList(type))
		{
			if(!residence.checkIfInZone(x, y, z, ref))
				continue;
			return residence;
		}
		return null;
	}
	
	public <R extends Residence> R findNearestResidence(Class<R> clazz, int x, int y, int z, Reflection ref, int offset)
	{
		R residence = getResidenceByCoord(clazz, x, y, z, ref);
		if(residence == null)
		{
			double closestDistance = offset;
			for(R r : getResidenceList(clazz))
			{
				double distance = r.getZone().findDistanceToZone(x, y, z, false);
				if(closestDistance <= distance)
					continue;
				closestDistance = distance;
				residence = r;
			}
		}
		return residence;
	}
	
	public void callInit()
	{
		for(Residence r : getResidences())
		{
			r.init();
		}
	}
	
	private void buildFastLook()
	{
		for(Residence residence : _residences.values())
		{
			List<Residence> list = _fastResidencesByType.get(residence.getClass());
			if(list == null)
			{
				list = new ArrayList<>();
				_fastResidencesByType.put(residence.getClass(), list);
			}
			list.add(residence);
		}
	}
	
	@Override
	public void log()
	{
		buildFastLook();
		info("total size: " + _residences.size());
		for(Map.Entry<Class<? extends Residence>, List<Residence>> entry : _fastResidencesByType.entrySet())
		{
			info(" - load " + entry.getValue().size() + " " + entry.getKey().getSimpleName().toLowerCase() + "(s).");
		}
	}

	private static <R extends Residence> List<R> typedResidencesView(List<Residence> residences, Class<R> type)
	{
		return residences == null || residences.isEmpty() ? Collections.emptyList() : new TypedResidencesView<>(residences, type);
	}

	private static final class TypedResidencesView<R extends Residence> extends AbstractList<R>
	{
		private final List<Residence> _delegate;
		private final Class<R> _type;

		private TypedResidencesView(List<Residence> delegate, Class<R> type)
		{
			_delegate = delegate;
			_type = type;
		}

		@Override
		public R get(int index)
		{
			return _type.cast(_delegate.get(index));
		}

		@Override
		public int size()
		{
			return _delegate.size();
		}

		@Override
		public void add(int index, R element)
		{
			_delegate.add(index, element);
		}

		@Override
		public R set(int index, R element)
		{
			return _type.cast(_delegate.set(index, element));
		}

		@Override
		public R remove(int index)
		{
			return _type.cast(_delegate.remove(index));
		}
	}
	
	@Override
	public int size()
	{
		return 0;
	}
	
	@Override
	public void clear()
	{
		_residences.clear();
		_fastResidencesByType.clear();
	}
}
