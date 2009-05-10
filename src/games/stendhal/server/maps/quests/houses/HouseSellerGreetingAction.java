package games.stendhal.server.maps.quests.houses;

import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.parser.Sentence;
import games.stendhal.server.entity.player.Player;

/** House owners are offered the chance to buy a spare key when the seller greets them. Others are just greeted with their name. */
final class HouseSellerGreetingAction extends HouseChatAction implements ChatAction {
	protected HouseSellerGreetingAction(final String questslot) {
		super(questslot);
	}

	public void fire(final Player player, final Sentence sentence, final SpeakerNPC engine) {
		String reply = "";
		
		if (HouseUtilities.playerOwnsHouse(player)) {
			reply = " At the cost of "
				+ HouseChatAction.COST_OF_SPARE_KEY
				+ " money you can purchase a spare key for your house. Do you want to buy one now?";
			engine.setCurrentState(ConversationStates.QUESTION_1);
		} else if (player.hasQuest(questslot)) {
			// the player has lost the house. clear the slot so that he can buy a new one if he wants
			player.removeQuest(questslot);
		}
		
		engine.say("Hello, " + player.getTitle() + "." + reply);
	}
	
}
