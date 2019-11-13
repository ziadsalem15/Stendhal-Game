package games.stendhal.server.maps.deniran.furnitureshop;

import games.stendhal.common.grammar.ItemParserResult;
import games.stendhal.server.actions.CommandCenter;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Outfit;
import games.stendhal.server.entity.npc.EventRaiser;
//import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPAction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FurnitureSellerNPC implements ZoneConfigurator {
//	private final ShopList shops = SingletonRepository.getShopList();

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
			class FurnitureSellerBehaviour extends SellerBehaviour {
				FurnitureSellerBehaviour(final Map<String, Integer> items) {
					super(items);
				}

				@Override
				public boolean transactAgreedDeal(ItemParserResult res, final EventRaiser seller, final Player player) {
	
						if (!player.drop("money", getCharge(res, player))) {
							seller.say("You don't seem to have enough money.");
							return false;
						}
						seller.say("Here you go, a stool!");

						RPAction action = new RPAction();
						action.put("type", "summon");
						action.put("furniture", "stool");
						action.put("x", player.getX() + 1);
						action.put("y", player.getY() + 1);
						CommandCenter.execute(player, action);

						player.notifyWorldAboutChanges();

						return true;
					
				}
			}

			final Map<String, Integer> items = new HashMap<String, Integer>();
			items.put("stool", 50);

			addGreeting("Hiya! Welcome to Deniran. You can buy your #furniture here.");
			addJob("I sell the best quality #furniture in Denrian!");
			addHelp("As of right now, I sell only stools but I'm planning to sell a lot more later. To buy something, just tell me you want to #buy #stool and I'll set one right beside you.");
			addGoodbye();
			new SellerAdder().addSeller(this, new FurnitureSellerBehaviour(items));
			addReply(Arrays.asList("furniture", "furnitures"), "I sell a lot of stuff: #stools, chairs, beds, tables, you name it!");
        	addReply(Arrays.asList("stool", "stools"), "A stool will cost you just 50 gold.");

        }
    };

    npc.setOutfit(new Outfit(0, 27, 7, 34, 1));
    npc.setDescription("You see Eddy. He sells furniture for a living.");
    npc.setPosition(45, 45);
    npc.initHP(100);

    zone.add(npc);   
}
}

