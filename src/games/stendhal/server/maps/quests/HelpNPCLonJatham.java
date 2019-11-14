package games.stendhal.server.maps.quests;

import games.stendhal.server.entity.npc.*;
import games.stendhal.server.entity.npc.action.*;
import games.stendhal.server.entity.npc.condition.*;
import games.stendhal.server.entity.player.*;
import java.util.*;

public class HelpNPCLonJatham extends AbstractQuest {

    public static final String QUEST_SLOT = "lon_jatham";
    

    @Override
    public String getSlotName() {
        return QUEST_SLOT;
    }
    
    public void prepareQuestStep() {

        // get a reference to the Lon Jatham npc
        SpeakerNPC npc = npcs.get("Lon Jatham");

     // if the player asks for a quest, go to state QUEST_OFFERED
        npc.add(ConversationStates.ATTENDING,
            ConversationPhrases.QUEST_MESSAGES, 
            new QuestNotCompletedCondition(QUEST_SLOT),
            ConversationStates.QUEST_OFFERED, 
            "I always wanted to be a teacher..but to some studens! I want to open a coding school, "
       			 + "can you bring me some students?",
            null); 
        
     // in state QUEST_OFFERED, accept "yes" and go back to ATTENDING
        npc.add(
            ConversationStates.QUEST_OFFERED,
            ConversationPhrases.YES_MESSAGES,
            null,
            ConversationStates.ATTENDING,
            "Great! I will be waiting here, preparing the coursework, writing the tests, starting the machines..ooh, ARCADE!!."
            + " Make sure you bring at least 3 students, otherwise I dont know if it's going to be worth it...",
            new SetQuestAction(QUEST_SLOT, "start"));
        

        // in state QUEST_OFFERED, accept "no" and go back to ATTENDING
           npc.add(
               ConversationStates.QUEST_OFFERED,
               ConversationPhrases.NO_MESSAGES,
               null,
               ConversationStates.ATTENDING,
               "Well...finding people willing to learn coding these days is hard, isn't it?. "
               + "Thanks for trying at least, i will use arcade by myself..",
               new SetQuestAction(QUEST_SLOT, "rejected"));
        
     // send him away if he has completed the quest already.
        npc.add(ConversationStates.ATTENDING,
            ConversationPhrases.QUEST_MESSAGES,
            new QuestCompletedCondition(QUEST_SLOT),
            ConversationStates.ATTENDING, 
            "Just when i tought 3 students means nothing...boy oh boy, teaching is hard enough as it is. "
            + "Imagine having to teach for hundreds of students... YUCK!",
            null); 

    }
    
    
    // check if player has note, and complete the quest if so
    private void prepareBringingStep() {
        SpeakerNPC npc = npcs.get("Lon Jatham");

     // player has the quest active and has a beer with him, ask for it
        npc.add(
            ConversationStates.IDLE,
            ConversationPhrases.GREETING_MESSAGES,
            new AndCondition(new QuestActiveCondition(QUEST_SLOT), new PlayerHasItemWithHimCondition("note")),
            ConversationStates.QUEST_ITEM_BROUGHT, 
            "Hey! Is that note for me?",
            null);
        
     // player has accepted the quest but did not bring a beer, remind him
        npc.add(
            ConversationStates.IDLE,
            ConversationPhrases.GREETING_MESSAGES,
            new AndCondition(new QuestActiveCondition(QUEST_SLOT), new NotCondition(new PlayerHasItemWithHimCondition("note"))),
            ConversationStates.ATTENDING, 
            "Hey, I'm still waiting for that note, remember? Anyway, what can I do for you?",
            null);
        
     // okay, the player wants to give us a note :-)
        List<ChatAction> reward = new LinkedList<ChatAction>();
        reward.add(new DropItemAction("note"));
        reward.add(new EquipItemAction("money", 20));
        reward.add(new IncreaseXPAction(50));
        reward.add(new IncreaseKarmaAction(10));
        reward.add(new SetQuestAction(QUEST_SLOT, "done"));
        npc.add(
             ConversationStates.QUEST_ITEM_BROUGHT,
             ConversationPhrases.YES_MESSAGES,
             new PlayerHasItemWithHimCondition("note"),
             ConversationStates.ATTENDING,
             "Thanks for bringing me students!!",
             new MultipleActions(reward));

        // the player has a note but wants to keep it for himself :-(
        npc.add(
            ConversationStates.QUEST_ITEM_BROUGHT,
            ConversationPhrases.NO_MESSAGES,
            null,
            ConversationStates.ATTENDING,
            "Drat! You remembered that I asked you for one, right? I could really use it right now.",
            null);
        }
 @Override   
public void addToWorld() {
	prepareQuestStep();
	prepareBringingStep();
}
    
    @Override
    public List<String> getHistory(final Player player) {
        final List<String> res = new ArrayList<String>();
        return res;
    }


    @Override
    public String getName() {
        return "HelpNPCLonJatham";
    }
    
    
}