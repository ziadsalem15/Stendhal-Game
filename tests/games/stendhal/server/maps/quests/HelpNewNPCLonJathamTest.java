package games.stendhal.server.maps.quests;

//package games.stendhal.server.maps.deniran;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.deniran.TeacherNPC;
import marauroa.common.Log4J;
import marauroa.common.game.RPObject.ID;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class HelpNewNPCLonJathamTest extends ZonePlayerAndNPCTestImpl
{

	private Player player = null;
	private SpeakerNPC npcLon = null;
	private SpeakerNPC npcFirst = null;
	private Engine en = null;
	private Engine enFirst = null;

	
	private String questSlot;
	private static final String ZONE_NAME = "int_deniran";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public HelpNewNPCLonJathamTest() {
		super(ZONE_NAME, "Lon Jatham");
	}

	@Override
	@Before
	public void setUp() throws Exception 
	{
		super.setUp();
		
		final StendhalRPZone zone = new StendhalRPZone(ZONE_NAME);
		
		new TeacherNPC().configureZone(zone, null); 
		
		quest = new HelpNewNPCLonJatham();
		quest.addToWorld();
		
		questSlot = quest.getSlotName();
		
	}
	@Test
	public void testQuest() {
		npcFirst = SingletonRepository.getNPCList().get("First NPC");
		npcLon = SingletonRepository.getNPCList().get("Lon Jatham");
		en = npcLon.getEngine();
		enFirst = npcFirst.getEngine();

		en.step(player, "hi");
		assertEquals("Yo matey! You look like you need #help.", getReply(npcLon));
		en.step(player, "note");
		assertEquals("I can't see that you got a valid note with my signature!", getReply(npcLon));
		en.step(player, "task");
		assertEquals("Perhaps if you find some students ...", getReply(npcLon));
		en.step(player, "note");
		assertEquals("I can't see that you got a valid note with my signature!", getReply(npcLon));
		en.step(player, "bye");
		assertEquals("See you later!", getReply(npcLon));
		
		PlayerTestHelper.equipWithItem(player, "note", "Can you join my institute?");
		
		en.step(player, "hi");
		assertEquals("Yo matey! You look like you need #help.", getReply(npcFirst));
		en.step(player, "note");
		assertEquals("What should I do with this? ", getReply(npcFirst));
		en.step(player, "task");
		assertEquals("Yes, that would be great !", getReply(npcFirst));
		en.step(player, "note");
		assertEquals("I have written my name here ! ", getReply(npcFirst));
		en.step(player, "bye");
		assertEquals("See you later!", getReply(npcFirst));
		
		PlayerTestHelper.equipWithItem(player, "note", "Here are the names !");
		
		en.step(player, "note");
		assertEquals("Hurray, here is the new student, thanks for your help !", getReply(npcLon));
		
	}
}