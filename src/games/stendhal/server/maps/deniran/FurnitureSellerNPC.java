package games.stendhal.server.maps.deniran;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Outfit;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;

import java.util.Arrays;
import java.util.Map;

public class FurnitureSellerNPC implements ZoneConfigurator {
	private final ShopList shops = SingletonRepository.getShopList();

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
	    final SpeakerNPC npc = new SpeakerNPC("Eddy") {
	    	
        @Override
		protected void createPath() {
            setPath(null);
        }

        @Override
		protected void createDialog() {
            
        	addGreeting("Get your #furniture here!");
        	addReply(Arrays.asList("furniture", "furnitures"), "I sell a lot of stuff: #stools, chairs, beds, tables, you name it!");
        	addReply(Arrays.asList("stool", "stools"), "A stool will cost you just 50 gold. Do you want one?");
        	addJob("I sell the best quality #furniture in Denrian");
        	new SellerAdder().addSeller(this, new SellerBehaviour(shops.get("furnitureshop")), false);
            addGoodbye();
        }
    };

    npc.setOutfit(new Outfit(0, 27, 7, 34, 1));
    npc.setDescription("You see Eddy. He sells furniture for a living.");
    npc.setPosition(45, 45);
    npc.initHP(100);

    zone.add(npc);   
}
}

