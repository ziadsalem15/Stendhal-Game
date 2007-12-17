package games.stendhal.server.actions;

import static org.junit.Assert.*;
import games.stendhal.server.actions.admin.AdministrationAction;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.creature.Cat;
import games.stendhal.server.entity.creature.Pet;
import games.stendhal.server.entity.creature.Sheep;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;

import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPObject.ID;

import static org.hamcrest.core.IsEqual.*;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.PlayerTestHelper;

public class PlayersQueryTest {

	private boolean whoWasExecuted;
	private boolean whereWasExecuted;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
	}

	@After
	public void tearDown() throws Exception {

		MockStendhalRPRuleProcessor.get().clearPlayers();
	}

	@Test
	public void testOnAction() {
		ActionListener al = new PlayersQuery() {
			@Override
			public void onWhere(Player player, RPAction action) {
				whereWasExecuted = true;
			}

			@Override
			public void onWho(Player player, RPAction action) {
				whoWasExecuted = true;
			}
		};
		RPAction action = new RPAction();
		action.put(WellKnownActionConstants.TYPE, "who");
		Player player = PlayerTestHelper.createPlayer();
		assertFalse(whoWasExecuted);
		al.onAction(player, action);
		assertTrue(whoWasExecuted);

		action.put(WellKnownActionConstants.TYPE, "where");

		assertFalse(whereWasExecuted);
		al.onAction(player, action);
		assertTrue(whereWasExecuted);

	}

	@Test
	public void testOnWho() {
		PlayersQuery pq = new PlayersQuery();
		RPAction action = new RPAction();
		action.put(WellKnownActionConstants.TYPE, "who");
		Player player = PlayerTestHelper.createPlayer();
		pq.onWho(player, action);
		assertThat(player.getPrivateText(), equalTo("0 Players online: "));
		player.clearEvents();
		MockStendhalRPRuleProcessor.get().addPlayer(player);
		pq.onWho(player, action);
		assertThat(player.getPrivateText(), equalTo("1 Players online: player(0) "));
		player.clearEvents();

		player.setAdminLevel(AdministrationAction.getLevelForCommand("ghostmode") - 1);
		player.setGhost(true);
		pq.onWho(player, action);
		assertThat(player.getPrivateText(), equalTo("0 Players online: "));
		player.clearEvents();

		player.setAdminLevel(AdministrationAction.getLevelForCommand("ghostmode"));
		player.setGhost(true);
		pq.onWho(player, action);
		assertThat(player.getPrivateText(), equalTo("1 Players online: player(!0) "));
		player.clearEvents();
		
		player.setAdminLevel(AdministrationAction.getLevelForCommand("ghostmode") + 1);
		player.setGhost(true);
		pq.onWho(player, action);
		assertThat(player.getPrivateText(), equalTo("1 Players online: player(!0) "));

	}

	@Test
	public void testOnWhereNoTarget() {
		PlayersQuery pq = new PlayersQuery();
		RPAction action = new RPAction();
		action.put(WellKnownActionConstants.TYPE, "where");
		Player player = PlayerTestHelper.createPlayer();
		MockStendhalRPRuleProcessor.get().addPlayer(player);

		pq.onWhere(player, action);
		assertNull(player.getPrivateText());

	}

	@Test
	public void testOnWhereNotThere() {
		PlayersQuery pq = new PlayersQuery();
		RPAction action = new RPAction();
		action.put(WellKnownActionConstants.TYPE, "where");
		action.put(WellKnownActionConstants.TARGET, "NotThere");

		Player player = PlayerTestHelper.createPlayer();
		MockStendhalRPRuleProcessor.get().addPlayer(player);

		pq.onWhere(player, action);
		assertThat(player.getPrivateText(), equalTo("No player named \"NotThere\" is currently logged in."));

	}

	@Test
	public void testOnWhere() {
		PlayersQuery pq = new PlayersQuery();
		RPAction action = new RPAction();
		action.put(WellKnownActionConstants.TYPE, "where");
		action.put(WellKnownActionConstants.TARGET, "bob");

		Player player = PlayerTestHelper.createPlayer("bob");
		StendhalRPZone zone = new StendhalRPZone("zone");
		zone.add(player);
		MockStendhalRPRuleProcessor.get().addPlayer(player);

		pq.onWhere(player, action);
		assertThat(player.getPrivateText(), equalTo("bob is in zone at (0,0)"));

	}

	@Test
	public void testOnWhereSheep() {
		PlayersQuery pq = new PlayersQuery();
		RPAction action = new RPAction();
		action.put(WellKnownActionConstants.TYPE, "where");
		action.put(WellKnownActionConstants.TARGET, "sheep");

		Player player = PlayerTestHelper.createPlayer();
		MockStendhalRPRuleProcessor.get().addPlayer(player);

		pq.onWhere(player, action);
		assertThat(player.getPrivateText(), equalTo("You currently have no sheep."));
	}

	@Test
	public void testOnWherePetSheep() {
		PlayerTestHelper.generateNPCRPClasses();
		PlayerTestHelper.generateCreatureRPClasses();
		Sheep.generateRPClass();
		PlayersQuery pq = new PlayersQuery();
		RPAction action = new RPAction();
		action.put(WellKnownActionConstants.TYPE, "where");
		action.put(WellKnownActionConstants.TARGET, "pet");

		Player player = PlayerTestHelper.createPlayer();

		pq.onWhere(player, action);
		assertThat(player.getPrivateText(), equalTo("You currently have no pet."));
		final Pet testPet = new Pet() {
			@Override
			public ID getID() {

				return new ID(new RPObject() {
					@Override
					public int getInt(String attribute) {

						return 1;
					}
				});
			}
		};
		final Sheep testSheep = new Sheep() {
			@Override
			public ID getID() {

				return new ID(new RPObject() {
					@Override
					public int getInt(String attribute) {

						return 1;
					}
				});
			}
		};
		player = new Player(new RPObject()) {
			@Override
			public Sheep getSheep() {
				return testSheep;
			}

			@Override
			public Pet getPet() {

				return testPet;
			}
		};

		player.setPet(testPet);

		MockStendhalRPRuleProcessor.get().addPlayer(player);

		pq.onWhere(player, action);
		assertThat(player.getPrivateText(), equalTo("Your pet is at (0,0)"));
		player.clearEvents();

		player.setSheep(testSheep);

		action.put(WellKnownActionConstants.TARGET, "sheep");

		pq.onWhere(player, action);
		assertThat(player.getPrivateText(), equalTo("Your sheep is at (0,0)"));

	}

}
