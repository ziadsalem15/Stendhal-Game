package games.stendhal.server.entity.item;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;

import games.stendhal.server.maps.MockStendlRPWorld;

import utilities.RPClass.ItemTestHelper;

public class SleepingBagTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();

	}


	/**
	 * Tests for describe.
	 */
	@Test
	public void testDescribe() {
		final SleepingBag sleepingBag = (SleepingBag) SingletonRepository.getEntityManager().getItem("sleeping bag");
		assertThat(sleepingBag.describe(), is("You see a §'sleeping bag'."));
	}

}
