package l2.gameserver.data.xml.parser;

import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ClientTeleportHolder;
import l2.gameserver.model.ClientTeleportData;
import l2.gameserver.utils.Location;
import org.dom4j.Element;

import java.io.File;
import java.util.Iterator;

public class ClientTeleportParser extends AbstractFileParser<ClientTeleportHolder>
{
	private static final ClientTeleportParser _instance = new ClientTeleportParser();

	private ClientTeleportParser()
	{
		super(ClientTeleportHolder.getInstance());
	}

	public static ClientTeleportParser getInstance()
	{
		return _instance;
	}

	@Override
	public File getXMLFile()
	{
		return new File(Config.DATAPACK_ROOT, "data/teleport/client_teleports.xml");
	}

	@Override
	public String getDTDFileName()
	{
		return "client_teleports.dtd";
	}

	@Override
	protected void readData(Element rootElement) throws Exception
	{
		Iterator iterator = rootElement.elementIterator("teleport");
		while(iterator.hasNext())
		{
			Element element = (Element) iterator.next();
			int id = Integer.parseInt(element.attributeValue("id"));
			int itemId = Integer.parseInt(element.attributeValue("item_id", "57"));
			long price = Long.parseLong(element.attributeValue("price", "0"));
			int minLevel = Integer.parseInt(element.attributeValue("min_level", "0"));
			int maxLevel = Integer.parseInt(element.attributeValue("max_level", "0"));
			int castleId = Integer.parseInt(element.attributeValue("castle_id", "0"));
			boolean gatekeeperRestrictions = Boolean.parseBoolean(element.attributeValue("gatekeeper_restrictions", "true"));
			Location destination = Location.parseLoc(element.attributeValue("loc"));
			getHolder().addTeleport(new ClientTeleportData(id, destination, itemId, price, minLevel, maxLevel, castleId, gatekeeperRestrictions));
		}
	}
}
