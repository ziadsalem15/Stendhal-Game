package games.stendhal.server.maps.quests;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
//import games.stendhal.server.entity.item.Item;
//import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import games.stendhal.server.maps.ados.wall.GreeterSoldierNPC;
import games.stendhal.server.maps.ados.snake_pit.PurpleCrystalNPC;
import games.stendhal.server.maps.fado.hut.BlueCrystalNPC;
import games.stendhal.server.maps.nalwor.forest.RedCrystalNPC;
import games.stendhal.server.maps.semos.mountain.YellowCrystalNPC;
import games.stendhal.server.maps.nalwor.river.PinkCrystalNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.RPClass.ItemTestHelper;

public class EmotionCrystalsTest {

	private SpeakerNPC npc;
	private Engine en;
	private EmotionCrystals quest;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendhalRPRuleProcessor.get();
		MockStendlRPWorld.reset();
		MockStendlRPWorld.get();
		QuestHelper.setUpBeforeClass();
		ItemTestHelper.generateRPClasses();
	}

	@After
	public void tearDown() throws Exception {
		MockStendhalRPRuleProcessor.get().clearPlayers();
	}

	@Before
	public void setUp() {

		PlayerTestHelper.removeAllPlayers();
		StendhalRPZone zone = new StendhalRPZone("admin_test");

		new GreeterSoldierNPC().configureZone(zone, null);
		new RedCrystalNPC().configureZone(zone, null);
		new PurpleCrystalNPC().configureZone(zone, null);
		new YellowCrystalNPC().configureZone(zone, null);
		new PinkCrystalNPC().configureZone(zone, null);
		new BlueCrystalNPC().configureZone(zone, null);

		npc = SingletonRepository.getNPCList().get("Julius");
		quest = new EmotionCrystals();
		quest.addToWorld();
	}


	@Test
	public void testTravelLogForEmotionCrystals()
	{
		final List<String> history = new LinkedList<String>();

		final Player player = PlayerTestHelper.createPlayer("testplayer");
		en = npc.getEngine();

		assertTrue(quest.getHistory(player).isEmpty());

		//Start quest by talking to Julius.
		en.step(player, "hi");
		en.step(player, "quest");
		assertEquals("I don't get to see my wife very often because I am so busy guarding this entrance. I would like to do something for her. Would you help me?",
				getReply(npc));
		en.step(player, "yes");
		assertEquals("Thank you. I would like to gather five #emotion #crystals as a gift for my wife. Please find all that you can and bring them to me.",
				getReply(npc));
		assertTrue(player.hasQuest("emotion_crystals"));

		//Check updated travel log to report that quest was accepted and in progress.
		history.add("I have talked to Julius, the soldier that guards the entrance to Ados.");
		history.add("I promised to gather five crystals from all across Faiumoni.");
		assertEquals(history, quest.getHistory(player));

		//Get red emotion crystal.
		npc = SingletonRepository.getNPCList().get("Red Crystal");
		en = npc.getEngine();
		en.step(player, "hi");
		assertTrue(npc.isTalking());
		en.step(player, "riddle");
		en.step(player, "anger");
		player.setQuest("emotion_crystal", 0, "riddle_solved");

		//Check updated travel log to report that the red crystal was found after answering the riddle.
		history.add("I have found the following crystals: red emotion crystal");
		assertEquals(history, quest.getHistory(player));


		//Get purple emotion crystal.
		npc = SingletonRepository.getNPCList().get("Purple Crystal");
		en = npc.getEngine();
		en.step(player, "hi");
		assertTrue(npc.isTalking());
		en.step(player, "riddle");
		en.step(player, "fear");
		player.setQuest("emotion_crystal", 1, "riddle_solved");

		//Check updated travel log to report that the purple crystal was found after answering the riddle.
		history.remove("I have found the following crystals: red emotion crystal");
		history.add("I have found the following crystals: red emotion crystal and purple emotion crystal");
		assertEquals(history, quest.getHistory(player));

		///Get yellow emotion crystal.
		npc = SingletonRepository.getNPCList().get("Yellow Crystal");
		en = npc.getEngine();
		en.step(player, "hi");
		assertTrue(npc.isTalking());
		en.step(player, "riddle");
		en.step(player, "joy");
		player.setQuest("emotion_crystal", 2, "riddle_solved");

		//Check updated travel log to report that the yellow crystal was found after answering the riddle.
		history.remove("I have found the following crystals: red emotion crystal and purple emotion crystal");
		history.add("I have found the following crystals: red emotion crystal, purple emotion crystal, and yellow emotion crystal");
		assertEquals(history, quest.getHistory(player));

		//Check if travel log states as yellow crystal being found after dropping it.
		player.drop("yellow emotion crystal");
		assertEquals(history, quest.getHistory(player));
	}
}
