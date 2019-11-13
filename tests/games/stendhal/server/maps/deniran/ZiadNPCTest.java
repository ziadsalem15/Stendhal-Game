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

public class ZiadNPCTest extends ZonePlayerAndNPCTestImpl 
{
	
	private static final String ZONE_NAME = "int_deniran";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public ZiadNPCTest() {
		setNpcNames("Ziad");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new ZiadNPC(), ZONE_NAME);
	}

	/**
	 * Tests for dialogue.
	 */
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Ziad");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hi Ziad"));
		assertEquals("Hello !", getReply(npc));
	   }
	
	public void testQuestHelpAndJob() {
		final SpeakerNPC npc = getNPC("Ziad");
		final Engine en = npc.getEngine();
		
		assertTrue(en.step(player, "quest"));
		assertEquals("That would be great !", getReply(npc));
		
		assertTrue(en.step(player, "note"));
		assertEquals("I will write my name here.", getReply(npc));
		
		assertTrue(en.step(player, "question"));
		assertEquals("Which type of AI we study?", getReply(npc));
		
		assertTrue(en.step(player, "bye"));
		assertEquals("Bye, see you later!", getReply(npc));
		}
}

