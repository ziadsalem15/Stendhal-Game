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

public class TeacherNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "int_deniran";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public TeacherNPCTest() {
		setNpcNames("Lon Jatham");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new TeacherNPC(), ZONE_NAME);
	}

	/**
	 * Tests for dialogue.
	 */
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Lon Jatham");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hi Lon"));
		assertEquals("Hello curious mind!", getReply(npc));
	   }
	
	public void testQuestHelpAndJob() {
		final SpeakerNPC npc = getNPC("Lon Jatham");
		final Engine en = npc.getEngine();
		
		assertTrue(en.step(player, "quest"));
		assertEquals("Find me 3 students that are willing to enroll.", getReply(npc));
		
		assertTrue(en.step(player, "help"));
		assertEquals("Ask me #quest  to learn about my quest of finding new young minds eager to learn.", getReply(npc));
		
		assertTrue(en.step(player, "job"));
		assertEquals("I am a professor of the mystic language of java!", getReply(npc));
		
		assertTrue(en.step(player, "bye"));
		assertEquals("See you at the next lecture!", getReply(npc));
	}
}