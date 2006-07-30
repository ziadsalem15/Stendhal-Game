import games.stendhal.common.Direction;
import games.stendhal.server.*
import games.stendhal.server.entity.*
import games.stendhal.server.entity.creature.*
import games.stendhal.server.entity.item.*
import games.stendhal.server.entity.npc.*;
import games.stendhal.server.events.TurnEvent;
import games.stendhal.server.scripting.*
import games.stendhal.server.pathfinder.Path

/**
 * Manages friendly entities
 */
public class Friends implements TurnEvent {
	private StendhalGroovyScript game;
	private StendhalRPRuleProcessor rules;
	private int turnCounter = 0;

	/**
	 * Constructor
	 *
	 * @param game StendhalGroovyScript
	 */
	public Friends(StendhalGroovyScript game, StendhalRPRuleProcessor rules) {
		this.game = game;
		this.rules = rules;
	}

	/**
	 * Creates a soldier
	 *
	 * @param name Name of the NPC
	 * @param x x-postion
	 * @param y y-postion
	 */
	public void createSoldier(String name, int x, int y) {
		ScriptingNPC npc = new ScriptingNPC(name);		
		npc.setClass("youngsoldiernpc");
		npc.setHP((int) (Math.random() * 80) + 10);
		npc.set(x, y);
		npc.setDirection(Direction.DOWN);
		game.add(npc)
	}

	/**
	 * Creates three soldiers to block the entrance
	 */
	public void createSoldiers() {

		// main entrance
		createSoldier("Soldier", 55, 47);
		createSoldier("Soldier", 56, 47);
		createSoldier("Soldier", 57, 47);

		// backdoor
		createSoldier("Soldier", 43, 23);
	}

	/**
	 * Creates a sheep for the Orcs to target
	 */
	public void createSheep() {
		Creature creature = new Sheep();
		creature.setx(56);
		creature.sety(46);
		game.add(creature);
	}

	public void onTurnReached(int currentTurn) {
		int wait = 6;
		switch (turnCounter) {

			case 0:
				shout("Katinka shouts: Help. There are two Orcs approaching our Wildlife Refuge");
				wait = 5 * 3;
				break;

			case 1:
				shout("Head of Soldiers shouts: Katinka, stay calm.");
				break;

			case 2:
				shout("Head of Soldiers shouts: I will send one of our soldiers called Marcus to help you.");
				wait = 60 * 3;
				break;

			case 3:
				shout("XMarcus shouts: I killed those two Orcs. But further investigation showed:");
				break;

			case 4:
				shout("Marcus shouts: They were only in the vanguard of a huge bunch of Warrior Orcs.");
				break;

			case 5:
				shout("Marcus shouts: We need reinforcements within about 10 minutes.");
				wait = 10 * 3;
				break;

			case 6:
				shout("Io Flotto shouts: I created a portal near Carmen in the south west of Semos.");
				break;

			case 7:
				shout("Io Flotto shouts: You can use it to get to the Ados Wildlife Refuge in time.");
				wait = 120 * 3;
				break;

			case 8:
				shout("Katinka shouts: Argh! They have eaten our boars. Help us!");
				return;
				// shout("Dr. Feelgood shouts: Help! Help us! The Ados Wildlife Refuge is under heavy attack by a bunch of hungry Orc Warriors.");
			
		}
		TurnNotifier.get().notifyInTurns(wait, this);
		turnCounter++;
	}
	
	public void shout(String text) {
		List players = rules.getPlayers();
		for (player in players) {
			player.sendPrivateText(text);
		}
	}
}

public class MapManager {
	private StendhalGroovyScript game;
	private Map storage;

	/**
	 * Constructor
	 *
	 * @param game StendhalGroovyScript
	 * @param storage Storage
	 */
	public MapManager(StendhalGroovyScript game, Map storage) {
		this.game = game;
		this.storage = storage;
		if (storage.get("signs") == null) {
			storage.put("signs", new HashSet());
		}
	}

	public void putSign(int x, int y, String text) {
		Sign sign=new Sign()
		sign.setx(x)
		sign.sety(y)
		sign.setText(text)
		storage.get("signs").add(sign);
		game.add(sign)
	}

	public void removeSigns() {
		Set signs = storage.get("signs");
		if (signs != null) {
			for (sign in signs) {
				game.remove(sign);
			}
		}
	}

	public void putSigns() {
		removeSigns();
		putSign(48, 38, "Elephants");
		putSign(48, 41, "Lions and Tigers");
		putSign(54, 30, "Crabs");
		putSign(61, 41, "Boars and Deers");
		putSign(66, 38, "Bears and Black Bears");
	}

	public void putSignsAway() {
		removeSigns();
		putSign(49, 39, "Elephants");
		putSign(47, 40, "Lions and Tigers");
		putSign(53, 29, "Crabs");
		putSign(62, 40, "Boars and Deers");
		putSign(67, 39, "Bears and Black Bears");
		putSign(67, 25, "Bears and Black Bears");
	}
}

MapManager mapManager = new MapManager(game, storage);
game.setZone("0_ados_outside_nw");

if (player == null || ((args.length > 0) && (args[0].equals("reset")))) {
	mapManager.putSigns();
} else {
	mapManager.putSignsAway();

	Friends friends = new Friends(game, rules);
	friends.createSoldiers();
	friends.createSheep();
	TurnNotifier.get().notifyInTurns(0, friends);
}