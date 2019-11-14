package games.stendhal.server.maps.deniran;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;

public class RaresNPC implements ZoneConfigurator {
	
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC pythonNPC = new SpeakerNPC("Rares") {

			@Override
			protected void createPath() {
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(27, 14));
				nodes.add(new Node(28, 14));
				setPath(new FixedPath(nodes, true));
			}

			@Override
			protected void createDialog() {
				addGreeting("Hello !");
				addJob("I do nothing with my life :(");
				addQuest("That would be great !");
				addHelp("If only someone would teach me a bit of Python...");
				addGoodbye("Bye bye");
			}
		};
		
		pythonNPC.setEntityClass("youngnpc");
		pythonNPC.setPosition(27,14);
		pythonNPC.initHP(1000);
		pythonNPC.setDescription("Snakecharmer had just been traumatized by coding in Lisp. All he wants to do is a little Python...");
		zone.add(pythonNPC);
	}
}
