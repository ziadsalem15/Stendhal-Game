package games.stendhal.server.maps.deniran;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.maps.deniran.furnitureshop.*;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.server.game.db.DatabaseFactory;
import utilities.PlayerTestHelper;


public class FurnitureSellerNPCTest{
private SpeakerNPC npc;
private Player player;
private Engine en;

@BeforeClass
public static void setUpBeforeClass()throws Exception
    {
    MockStendlRPWorld.get();
    StendhalRPZone zone = new StendhalRPZone("admin_test");
    new FurnitureSellerNPC().configureZone(zone,null);
    new DatabaseFactory().initializeDatabase();
    }

@Before
public void setUp() 
    { 
    npc = SingletonRepository.getNPCList().get("Eddy");
    en = npc.getEngine();
    StendhalRPZone srpz = new StendhalRPZone("int_semos_furniture_shop");
    SingletonRepository.getRPWorld().addRPZone(srpz);
    player = PlayerTestHelper.createPlayer("bob");
    player.teleport(srpz,10,10,null,null);
    }

@Test
public void createDialogTest()
    {
    assertTrue(en.step(player,"hi"));
    assertEquals("Hiya! Welcome to Deniran. You can buy your #furniture here.",getReply(npc));
    assertTrue(en.step(player, "bye"));
    assertEquals("Bye.",getReply(npc));
    }
}
