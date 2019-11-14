package games.stendhal.server.entity.item;

import java.util.Map;

public class SleepingBag extends Item{
	/**
	 * Creates a new sleeping bag.
	 *
	 * @param name
	 * @param clazz
	 * @param subclass
	 * @param attributes
	 */
	public SleepingBag(final String name, final String clazz, final String subclass,
			final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
	}
	
	/**
	 * Copy constructor.
	 *
	 * @param item
	 *            item to copy
	 */
	public SleepingBag(final SleepingBag item) {
		super(item);
	}

}
