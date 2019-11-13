package games.stendhal.server.maps.deniran;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;

public class AINPC implements ZoneConfigurator {
	
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC aiNPC = new SpeakerNPC("Monty Hall") {

			@Override
			protected void createPath() {
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(16, 18));
				nodes.add(new Node(19, 18));
				setPath(new FixedPath(nodes, true));
			}

			@Override
			protected void createDialog() {
				addGreeting("I never ever switch doors #help?");
				addJob("I entertain people and care for animals");
				addHelp("There is no such thing as robots!");
				addGoodbye("see you later aligator");
			}
		};
		
		aiNPC.setEntityClass("youngnpc");
		aiNPC.setPosition(16,18);
		aiNPC.initHP(1000);
		aiNPC.setDescription("Goats are his favourite animals");
		zone.add(aiNPC);
	}
}
