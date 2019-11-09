package games.stendhal.server.entity.creature;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.common.game.SyntaxException;

public class Monkey extends Pet {

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(Monkey.class);

	@Override
	void setUp() {
		HP = 300;
		incHP = 4; 
		ATK = 10;
		DEF = 30;
		XP = 100;
		baseSpeed = 0.9;

		setAtk(ATK);
		setDef(DEF);
		setXP(XP);
		setBaseHP(HP);
		setHP(HP);
	}

	public static void generateRPClass() {
		try {
			final RPClass monkey = new RPClass("monkey");
			monkey.isA("pet");
		} catch (final SyntaxException e) {
			logger.error("cannot generate RPClass", e);
		}
	}

	/**
	 * Creates a new wild Monkey.
	 */
	public Monkey() {
		this(null);
	}

	/**
	 * Creates a new baby dragon that may be owned by a player.
	 * @param owner The player who should own the monkey
	 */
	public Monkey(final Player owner) {
		super();
		setOwner(owner);
		setUp();
		setRPClass("monkey");
		put("type", "monkey");

		if (owner != null) {
			// add pet to zone and create RPObject.ID to be used in setPet()
			owner.getZone().add(this);
			owner.setPet(this);
		}

		update();
	}

	/**
	 * Creates a Monkey based on an existing monkey RPObject, and assigns it to a
	 * player.
	 *
	 * @param object object containing the data for the Monkey
	 * @param owner
	 *            The player who should own the monkey
	 */
	public Monkey(final RPObject object, final Player owner) {
		super(object, owner);
		setRPClass("monkey");
		put("type", "monkey");
		update();
	}

	@Override
	protected
	List<String> getFoodNames() {
		return Arrays.asList("ham", "pizza", "meat");
	}

	/**
	 * Does this domestic animal take part in combat?
	 *
	 * @return true, if it can be attacked by creatures, false otherwise
	 */
	@Override
	protected boolean takesPartInCombat() {
		return false;
	}


}
