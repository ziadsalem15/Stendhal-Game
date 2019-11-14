package games.stendhal.server.maps.quests;

//import games.stendhal.server.maps.deniran.TeacherNPC;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.deniran.TeacherNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class HelpNPCLonJathamTest extends ZonePlayerAndNPCTestImpl
{
	
	private static final String QUEST_SLOT = "lon_jatham";

	private Player player = null;
	private SpeakerNPC npc = null;
	private Engine en = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
	}

	@Override
	@Before
	public void setUp() {

		final StendhalRPZone zone = new StendhalRPZone("deniran");
		new TeacherNPC().configureZone(zone, null);
		
		AbstractQuest quest = new HelpNPCLonJatham();
		quest.addToWorld();

		player = PlayerTestHelper.createPlayer("bob");
	}

	@Test
	public void testForHiAndBye() {

		// haven't started quest yet
		assertNull(player.getQuest(QUEST_SLOT));

		npc = SingletonRepository.getNPCList().get("Lon Jatham");
		en = npc.getEngine();

		en.step(player, "hi");
		assertEquals("Hello curious mind!", getReply(npc));
		en.step(player, "bye");
	}
	
	@Test
	public void testQuest() {

		npc = SingletonRepository.getNPCList().get("Lon Jatham");
		en = npc.getEngine();

		en.step(player, "hi");
		assertEquals("Hello curious mind!", getReply(npc));
		en.step(player, "help");
		assertEquals("Ask me #quest  to learn about my quest of finding new young minds eager to learn.", getReply(npc));
		en.step(player, "job");
		assertEquals("I am a professor of the mystic language of java!", getReply(npc));
		en.step(player, "note");
		assertEquals(null, getReply(npc));
		en.step(player, "quest");
		assertEquals("I always wanted to be a teacher..but to some studens! I want to open a coding school, can you bring me some students?", getReply(npc));
		en.step(player, "bye");
		assertEquals("See you at the next lecture!", getReply(npc));
		
		player.setQuest(QUEST_SLOT, "start");

		assertEquals(player.getQuest(QUEST_SLOT),"start");

		en.step(player, "hi");
		assertEquals("Hey, I'm still waiting for that note, remember? Anyway, what can I do for you?", getReply(npc));
		en.step(player, "quest");
		assertEquals("I always wanted to be a teacher..but to some studens! I want to open a coding school, can you bring me some students?", getReply(npc));
		en.step(player, "bye");
		assertEquals("See you at the next lecture!", getReply(npc));

		// check quest slot
		assertEquals(player.getQuest(QUEST_SLOT),"start");
		

		player.setQuest(QUEST_SLOT, "rejected");

		assertEquals(player.getQuest(QUEST_SLOT),"rejected");
		PlayerTestHelper.equipWithItem(player, "note", "Lon Jatham");

		en.step(player, "hi");
		assertEquals("Hello curious mind!", getReply(npc));
		en.step(player, "quest");
		assertEquals("I always wanted to be a teacher..but to some studens! I want to open a coding school, can you bring me some students?", getReply(npc));
		en.step(player, "note");
		assertEquals("Well...finding people willing to learn coding these days is hard, isn't it?. Thanks for trying at least, i will use arcade by myself..", getReply(npc));
		en.step(player, "bye");
		assertEquals("See you at the next lecture!", getReply(npc));
		// check quest slot
		assertEquals(player.getQuest(QUEST_SLOT),"rejected");
		
		
		player.setQuest(QUEST_SLOT, "done");

		assertEquals(player.getQuest(QUEST_SLOT),"done");

		en.step(player, "hi");
		assertEquals("Hello curious mind!", getReply(npc));
		en.step(player, "quest");
		assertEquals("Just when i tought 3 students means nothing...boy oh boy, teaching is hard enough as it is. Imagine having to teach for hundreds of students... YUCK!", getReply(npc));
		en.step(player, "bye");
		assertEquals("See you at the next lecture!", getReply(npc));

		// check quest slot
		assertEquals(player.getQuest(QUEST_SLOT),"done");
		
		player.setQuest(QUEST_SLOT, "reward");
		assertEquals(player.getQuest(QUEST_SLOT),"reward");
		
		en.step(player, "hi");
		assertEquals("Hey! Is that note for me?", getReply(npc));
		en.step(player, "note");
		assertEquals("Drat! You remembered that I asked you for one, right? I could really use it right now.", getReply(npc));
		en.step(player, "bye");
		
	}
	
}