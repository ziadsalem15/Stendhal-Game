package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;

import games.stendhal.server.actions.CommandCenter;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import marauroa.common.game.RPAction;
import utilities.PlayerTestHelper;

public class FurnitureTest {

	/**
	 * Test for checking if the furniture item exists in the shop list with the price.
	 */
	@Test
	public void shouldCheckIfFurntiureItemExistsInShopList() {
		
		ShopList shop = ShopList.get();
		Map<String, Integer> shopContents = shop.get("furnitureshop");
		String contents = shopContents.toString();
		String expected = "{stool=50}";
		assertEquals(contents, expected);
		
	}
	
	/**
	 * Test for placing furniture item in given coordinates.
	 */
	@Test
	public void shouldPlaceFurnitureItenInGivenCoordinates() {
		
		final Player pl = PlayerTestHelper.createPlayer("testplayer");
		pl.setAdminLevel(5000);
		MockStendhalRPRuleProcessor.get().addPlayer(pl);
		final StendhalRPZone zone = new StendhalRPZone("zone");
		zone.add(pl);
		
		final RPAction action = new RPAction();
		action.put("type", "summon");
		action.put("furniture", "stool");
		action.put("x", 0);
		action.put("y", 0);
		CommandCenter.execute(pl, action);
		assertEquals(1, pl.getID().getObjectID());
		assertNotNull(pl.getZone().getEntityAt(0, 0));
	}
	
	/**
	 * Test for letting furniture item be equipped in inventory.
	 */
	@Test
	public void shouldHaveFurnitureItemEquippedInInventoryAfterBuying() {
		
		final Player pl = PlayerTestHelper.createPlayer("testplayer");
		pl.setAdminLevel(5000);
		MockStendhalRPRuleProcessor.get().addPlayer(pl);
		final StendhalRPZone zone = new StendhalRPZone("zone");
		zone.add(pl);
		
		final RPAction action = new RPAction();
		action.put("type", "summonat");
		action.put("target", "testplayer");
		action.put("slot", "bag");
		action.put("item", "stool");
		CommandCenter.execute(pl, action);
		assertNotNull(pl.getAllEquipped("stool"));

	}
}
