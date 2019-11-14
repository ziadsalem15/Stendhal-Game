package games.stendhal.server.maps.deniran;

import games.stendhal.server.core.config.ZoneConfigurator;
//import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Outfit;
//import games.stendhal.server.core.pathfinder.FixedPath;
//import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;


//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
import java.util.Map;

public class TeacherNPC implements ZoneConfigurator {
    
	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}
	 private void buildNPC(final StendhalRPZone zone) {
 	    final SpeakerNPC npc = new SpeakerNPC("Lon Jatham") {
         @Override
		protected void createPath() {
             setPath(null);
             //NPC stand still
         }

         @Override
		protected void createDialog() {
             // Lets the NPC reply with "Hallo" when a player greets him. But we could have set a custom greeting inside the ()
             addGreeting("Hello curious mind!");
             // Lets the NPC reply when a player says "job"
             addJob("I am a professor of the mystic language of java!");
             // Lets the NPC reply when a player asks for help
             addHelp("Ask me #quest  to learn about my quest of finding new young minds eager to learn.");
             //Lets the NPC tell you about the quest
             addQuest("Find me 3 students that are willing to enroll.");
            //custom goodbye added
             addGoodbye("See you at the next lecture!");
         }
     };

     // This determines how the NPC will look like. welcomernpc.png is a picture in data/sprites/npc/
       npc.setOutfit(new Outfit(0, 05, 01, 06, 01));
     // set a description for when a player does 'Look'
     npc.setDescription("You see Lon Jatham, famous professor at the Institute of Deniran Technology looking for bright minds to teach.");
     // Set the initial position to be the first node on the Path you defined above.
     npc.setPosition(14, 7);
     npc.initHP(100);

     zone.add(npc);   
 }
}	