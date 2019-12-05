package games.stendhal.server.core.rule.defaultruleset;

import java.util.Map;

import org.apache.log4j.Logger;

import games.stendhal.server.core.rp.achievement.Category;
//import games.stendhal.server.entity.npc.ChatCondition;

public class DefaultAchievement {

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(DefaultAchievement.class);

	/** Creature class. */
	private String clazz;

	/** Creature subclass. */
	private String subclass;

	/** Creature name. */
	private String name;

	/** optional creature description. * */

	/** Map Tile Id in the way tileset.png:pos. */
	private int tileid;
			
	private Map<String, String> attributes = null;
	
	private Class< ? > implementation = null;

	private int value;
	
	private String identifier;

	private String title;

	private Category category;

	private String description;

	private int baseScore;

	/** is this achievement visible? */
	private boolean active;

	private String condition;



	/**
	 * create a new achievement
	 *
	 * @param identifier
	 * @param title
	 * @param category
	 * @param description
	 * @param baseScore
	 * @param active
	 * @param condition
	 */
	public DefaultAchievement(String identifier, String title, Category category, String description, int baseScore, boolean active, String condition) {
		this.identifier = identifier;
		this.title = title;
		this.category = category;
		this.condition = condition;
		this.description = description;
		this.baseScore = baseScore;
		this.active = active;
	}


	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(final Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public void setDescription(final String text) {
		this.description = text;
	}

	public String getDescription() {
		return description;
	}

	
//	public String getName() {
//		return title;
//	}

	public Class<?> getImplementationClass() {
		return getImplementationClass();
	}
	
	

}
