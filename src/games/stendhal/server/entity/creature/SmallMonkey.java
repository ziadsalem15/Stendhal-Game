package games.stendhal.server.entity.creature;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Arrays;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import org.apache.log4j.Logger;


import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.common.game.SyntaxException;

public class SmallMonkey extends Pet {

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(SmallMonkey.class);
	
	//list of entities that have already been robbed
	//is the small monkey moving towards a target
	private List<RPEntity> beenRobbed;
	private boolean isMoving;
	private boolean returned = true;
	private Item stolenItem;
	
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
	RPEntity stealTarget = null;
	
	public static void generateRPClass() {
		try {
			final RPClass smallmonkey = new RPClass("smallmonkey");
			smallmonkey.isA("pet");
		} catch (final SyntaxException e) {
			logger.error("cannot generate RPClass", e);
		}
	}

	/**
	 * Creates a new wild Monkey.
	 */
	public SmallMonkey() {
		this(null);
	}

	/**
	 * Creates a new baby dragon that may be owned by a player.
	 * @param owner The player who should own the monkey
	 */
	public SmallMonkey(final Player owner) {
		super();
		setOwner(owner);
		setUp();
		setRPClass("smallmonkey");
		put("type", "smallmonkey");

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
	public SmallMonkey(final RPObject object, final Player owner) {
		super(object, owner);
		setRPClass("smallmonkey");
		put("type", "smallmonkey");
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

	//find new target
	
	/**
	 * Returns a list of targets. One of it will be stolen from.
	 *
	 * @return list of targets
	 */
	public List<RPEntity> getTargetList() {
			List<RPEntity> returnList = getZone().getPlayerAndCreatures();
			returnList.remove(this);
			if (owner != null && returnList.contains(owner)) 
				returnList.remove(owner);
			return returnList;
			
	}

	/**
	 * Returns the nearest target, which is reachable or otherwise attackable.
	 *
	 * @param range
	 *            attack radius
	 * @return chosen enemy or null if no enemy was found.
	 */
	public RPEntity getNearestTarget() {
		// create list of enemies
		final List<RPEntity> targetList = getTargetList();
		if (targetList.isEmpty()) {
			return null;	
		}
		
		//the final target
		RPEntity chosen = null;
		//// calculate the distance of all possible enemies
		//final Map<RPEntity, Double> distances = new HashMap<RPEntity, Double>();
		double chosenDistance = Double.MAX_VALUE;;
		double distance;
		for (RPEntity target : targetList) {
			if (target.isInvisibleToCreatures()) {
				continue;
			}
			if (owner != null) {
				distance = owner.squaredDistance(target);}
			else {
				distance = this.squaredDistance(target);}
			
			if(chosen == null) {
				chosen = target;
				chosenDistance = distance;
			}
			else if(distance < chosenDistance) {
				chosenDistance = distance;
				chosen = target;
				
			}

		/*// now choose the nearest target for which there is a path, or is
		// attackable otherwise
		
		while ((chosen == null) && !distances.isEmpty()) {
			double shortestDistance = Double.MAX_VALUE;
			for (final Map.Entry<RPEntity, Double> target : distances.entrySet()) {
				final double distance = target.getValue();
				if (distance < shortestDistance) {
					chosen = target.getKey();
					shortestDistance = distance;
				}
			}
			if (shortestDistance >= 1) {
				final List<Node> path = Path.searchPath(this, chosen, getMovementRange());
				if ((path == null) || path.isEmpty()) {
					distances.remove(chosen);
					chosen = null;
				} else {
					// set the path. if not setMovement() will search a new one
					setPath(new FixedPath(path, false));
				}
			}*/
		}
		// return the chosen enemy or null if we could not find one in reach
		return chosen;
	}
	
	/**
	 * Determines what the pet shall do next.
	 */
	@Override
	public void logic() {
		stealTarget = null;
		stealTarget = this.getNearestTarget();
		//this.setTarget(stealTarget);
		if(stealTarget!=null)
		{
			if(beenRobbed == null)
			{
				//create empty list
				beenRobbed = new ArrayList<RPEntity>();
			}
	
               if(beenRobbed.isEmpty() || !beenRobbed.contains(stealTarget))
			{
				if(owner==null || owner.squaredDistance(stealTarget)<=this.getPerceptionRange()+5)
				{
					clearPath();
					this.setMovement(stealTarget, 0, 0, this.getMovementRange());
					//if the small monkey is not next to the target it is still moving
                    isMoving = !nextTo(stealTarget);
                    //if it has stolen from the target, add that target to the list of already robbed entities
                    if(nextTo(stealTarget)) {
                    	this.rob(stealTarget);
                    	beenRobbed.add(stealTarget);
                    }
                }
				
			}
		}
			
		if(!isMoving)
		{
			// call super class to perform common tasks to pets (will move the monkey back to the owner)
			super.logic();
			if(!returned)
			{
				owner.equipOrPutOnGround(stolenItem);
				returned = true;
				System.out.println("RETURNED");
			}
				
		}
		else
		{
			//move monkey closer to target
			this.applyMovement();
		}
	}//logic
	
	//stub for robbing
	private void rob(RPEntity target) {
		List<Item> listOfItems = target.getAllItems();
		System.out.println(listOfItems);
		if(listOfItems.size()>0)
		{
			Item item = listOfItems.get(0);
			target.drop(item);
			System.out.println("DROPPED");
			stolenItem = item;
			returned = false;
		}
	}

	//returns if the monkey is moving
	public boolean moving()
	{
		return isMoving;
	}

	


}
