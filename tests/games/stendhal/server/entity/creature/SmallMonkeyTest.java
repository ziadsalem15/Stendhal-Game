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

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.creature.impl.attack.HandToHand;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPObject;
import utilities.PlayerTestHelper;
import utilities.RPClass.SmallMonkeyTestHelper;

public class SmallMonkeyTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SmallMonkeyTestHelper.generateRPClasses();
		MockStendlRPWorld.get();
	}

	List<String> foods = Arrays.asList("ham", "pizza", "meat");

	/**
	 * Tests for monkey.
	 */
	@Test
	public void testSmallMonkey() {
		final SmallMonkey malfoy = new SmallMonkey();
		assertThat(malfoy.getFoodNames(), is(foods));
	}

	/**
	 * Tests for smallMonkeyPlayer.
	 */
	@Test
	public void testSmallMonkeyPlayer() {

		final StendhalRPZone zone = new StendhalRPZone("zone");
		final Player bob = PlayerTestHelper.createPlayer("bob");
		zone.add(bob);
		final SmallMonkey malfoy = new SmallMonkey(bob);

		assertThat(malfoy.getFoodNames(), is(foods));
	}

	/**
	 * Tests for monkeyRPObjectPlayer.
	 */
	@Test
	public void testMonkeyRPObjectPlayer() {
		RPObject template = new RPObject();
		template.put("hp", 30);
		final SmallMonkey malfoy = new SmallMonkey(template, PlayerTestHelper.createPlayer("bob"));
		assertThat(malfoy.getFoodNames(), is(foods));
	}
	
	/**
	 * Tests logic method.
	 */
	@Test
	public void testGetCloseNPC() {
		MockStendhalRPRuleProcessor.get();
		//final StendhalRPZone zone = new StendhalRPZone("zone");
		final Creature steve = createMock(Creature.class);
		final SmallMonkey malfoy = new SmallMonkey();
		assertThat(malfoy.getNearestTarget(malfoy.getPerceptionRange()),is(steve));
		//zone.add((Creature)steve);
		//zone.add(malfoy);
	    //expect(malfoy.getNearestTarget(malfoy.getPerceptionRange())).andReturn(steve);
		malfoy.logic();
		assertTrue(malfoy.nextTo(steve));
	}
	
	
    

}
