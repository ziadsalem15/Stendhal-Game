package games.stendhal.server.maps.quests;

import games.stendhal.server.StendhalRPWorld;
import games.stendhal.server.StendhalRPZone;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.TeleporterBehaviour;
import games.stendhal.server.entity.npc.action.EquipItemAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestNotCompletedCondition;

import java.util.LinkedList;
import java.util.List;

/**
 * QUEST: Meet Santa anywhere around the World.
 * 
 * PARTICIPANTS: - Santa Claus
 * 
 * STEPS: - Find Santa - Say hi - Get reward
 * 
 * REWARD: - a present which can be opend to obtain a random good reward: food,
 * money, potions, items, etc...
 * 
 * REPETITIONS: - None
 */
public class MeetSanta extends AbstractQuest {
	private static final String QUEST_SLOT = "meet_santa_07";

	/** the Santa NPC */
	protected SpeakerNPC santa;

	private StendhalRPZone zone;

	@Override
	public void init(String name) {
		super.init(name, QUEST_SLOT);
	}

	private SpeakerNPC createSanta() {
		santa = new SpeakerNPC("Santa") {
			@Override
			protected void createPath() {
				// npc does not move
				setPath(null);
			}

			@Override
			protected void createDialog() {
				add(ConversationStates.IDLE,
					ConversationPhrases.GREETING_MESSAGES,
					new QuestCompletedCondition(QUEST_SLOT),
					ConversationStates.ATTENDING,
					"Hi again.", null);

				List<SpeakerNPC.ChatAction> reward = new LinkedList<SpeakerNPC.ChatAction>();
				reward.add(new EquipItemAction("present"));
				reward.add(new SetQuestAction(QUEST_SLOT, "done"));
				
				add(ConversationStates.IDLE,
					ConversationPhrases.GREETING_MESSAGES,
					new QuestNotCompletedCondition(QUEST_SLOT),
					ConversationStates.ATTENDING,
					"Merry Christmas! I have a present for you.",
					new MultipleActions(reward));

				addJob("I am Santa Claus! Where have you been in these years?");
				addGoodbye();
			}
		};
		npcs.add(santa);
		santa.setEntityClass("santaclausnpc");
		santa.initHP(100);

		// start in int_admin_playground
		zone = StendhalRPWorld.get().getZone("int_admin_playground");
		santa.setPosition(17, 13);
		zone.add(santa);

		return santa;
	}

	@Override
	public void addToWorld() {
		super.addToWorld();
		createSanta();
		new TeleporterBehaviour(santa, "Ho, ho, ho! Merry Christmas!");
	}
}
