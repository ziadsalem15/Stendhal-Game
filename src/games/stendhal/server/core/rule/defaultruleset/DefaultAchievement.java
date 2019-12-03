package games.stendhal.server.core.rule.defaultruleset;

import java.util.Map;

import org.apache.log4j.Logger;

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
	private String description;

	/** Map Tile Id in the way tileset.png:pos. */
	private int tileid;
		
	private String condition;
	
	private Map<String, String> attributes = null;
	
	private Class< ? > implementation = null;

	private int value;
	
	public DefaultAchievement(final String clazz, final String subclazz, final String name, final int tileid) {
		this.clazz = clazz;
		this.subclass = subclazz;
		this.name = name;
		this.tileid = tileid;
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
	
//	public void setImplementation(final Class< ? > implementation) {
//		this.implementation = implementation;
//		creator = buildCreator(implementation);
//	}
//	
//	public Class< ? > getImplementation() {
//		return implementation;
//	}
}
