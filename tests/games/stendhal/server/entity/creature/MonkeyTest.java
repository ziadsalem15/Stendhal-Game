/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.creature;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPObject;
import utilities.PlayerTestHelper;
import utilities.RPClass.BabyDragonTestHelper;

public class MonkeyTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MonkeyTestHelper.generateRPClasses();
		MockStendlRPWorld.get();
	}

	List<String> foods = Arrays.asList("ham", "pizza", "meat");

	/**
	 * Tests for monkey.
	 */
	@Test
	public void testMonkey() {
		final Monkey malfoy = new Monkey();
		assertThat(malfoy.getFoodNames(), is(foods));
	}

	/**
	 * Tests for monkeyPlayer.
	 */
	@Test
	public void testMonkeyPlayer() {

		final StendhalRPZone zone = new StendhalRPZone("zone");
		final Player bob = PlayerTestHelper.createPlayer("bob");
		zone.add(bob);
		final Monkey malfoy = new Monkey(bob);

		assertThat(malfoy.getFoodNames(), is(foods));
	}

	/**
	 * Tests for monkeyRPObjectPlayer.
	 */
	@Test
	public void testMonkeyRPObjectPlayer() {
		RPObject template = new RPObject();
		template.put("hp", 30);
		final Monkey malfoy = new Monkey(template, PlayerTestHelper.createPlayer("bob"));
		assertThat(malfoy.getFoodNames(), is(foods));
	}

}
