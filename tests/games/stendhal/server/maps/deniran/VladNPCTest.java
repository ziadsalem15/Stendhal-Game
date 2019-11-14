package games.stendhal.server.maps.deniran;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class VladNPCTest extends ZonePlayerAndNPCTestImpl{
	
	private static final String ZONE_NAME = "int_deniran";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public VladNPCTest() {
		setNpcNames("Vlad");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new VladNPC(), ZONE_NAME);
	}

	/**
	 * Tests for dialogue.
	 */
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Vlad");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hi Vlad"));
		assertEquals("I never ever switch doors #help?", getReply(npc));
	   }
	
	public void testQuestHelpAndJob() {
		final SpeakerNPC npc = getNPC("Vlad");
		final Engine en = npc.getEngine();
		
		assertTrue(en.step(player, "quest"));
		assertEquals("That would be great !", getReply(npc));
		
		assertTrue(en.step(player, "job"));
		assertEquals("I entertain people and care for animals", getReply(npc));
		
		assertTrue(en.step(player, "note"));
		assertEquals("I will write my name here.", getReply(npc));
		
		assertTrue(en.step(player, "help"));
		assertEquals("There is no such thing as robots!", getReply(npc));
		
		assertTrue(en.step(player, "bye"));
		assertEquals("see you later aligator", getReply(npc));
		}

}
