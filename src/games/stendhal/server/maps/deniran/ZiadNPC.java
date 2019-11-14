package games.stendhal.server.maps.deniran;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;

public class ZiadNPC implements ZoneConfigurator {
	
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC dumbNPC = new SpeakerNPC("Ziad") {

			@Override
			protected void createPath() {
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(31, 11));
				nodes.add(new Node(32, 11));
				setPath(new FixedPath(nodes, true));
			}

			@Override
			protected void createDialog() {
				addGreeting("OOoooooooooohh #help?");
	            addQuest("That would be great !");
				addJob("Sugoii");
				addHelp("Surimasa");
				addGoodbye("Aaaaaaaahh");
			}
		};
		
		dumbNPC.setEntityClass("youngnpc");
		dumbNPC.setPosition(31,11);
		dumbNPC.initHP(1000);
		dumbNPC.setDescription("He is vegan");
		zone.add(dumbNPC);
	}
}
