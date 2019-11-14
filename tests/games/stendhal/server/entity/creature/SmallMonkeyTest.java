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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.player.Player;
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
	
		final StendhalRPZone zone = new StendhalRPZone("zone",20,20);
		final RPEntity steve = new Creature();
		final RPEntity keith = PlayerTestHelper.createPlayer("keith");
		final SmallMonkey mymonkey = new SmallMonkey();
		zone.add(steve);
		zone.add(keith);
		zone.add(mymonkey);
		mymonkey.setPosition(0, 0);
		keith.setPosition(10, 10);
		steve.setPosition(3, 3);
		//check nearest target is the closest entity
		assertThat(mymonkey.getNearestTarget(),is(steve));
		//run logic until the monkey has stopped moving
		do{mymonkey.logic();}while(mymonkey.moving());
		//check that the monkey has stopped at the nearest target
		assertTrue(mymonkey.nextTo(steve));
	}
	
	/**
	 * Tests logic method when monkey has an owner.
	 */
	@Test
	public void testGetCloseNPCPlayer() {
		final StendhalRPZone zone = new StendhalRPZone("zone",20,20);
		final RPEntity steve = PlayerTestHelper.createPlayer("steve");
		final RPEntity keith = new Creature();
		final Player bob = PlayerTestHelper.createPlayer("bob");
		zone.add(bob);
		final SmallMonkey thismonkey = new SmallMonkey(bob);
		zone.add(steve);
		zone.add(keith);
		thismonkey.setPosition(0, 0);
		keith.setPosition(0, 10);
		steve.setPosition(0, 7);
		bob.setPosition(0, 4);
		//check nearest target is the closest entity (not the monkey or owner)
		assertThat(thismonkey.getNearestTarget(),is(steve));
		//run logic until the monkey has stopped moving
		do{thismonkey.logic();}while(thismonkey.moving());
		//check that the monkey has stopped at the nearest target
		assertTrue(thismonkey.nextTo(steve));	
	}

	
	@Test
	public void testRob() {
		final StendhalRPZone zone = new StendhalRPZone("zone",20,20);
		final RPEntity steve = PlayerTestHelper.createPlayer("steve");
		final Player bob = PlayerTestHelper.createPlayer("bob");
		zone.add(bob);
		final SmallMonkey thismonkey = new SmallMonkey(bob);
		zone.add(steve);
		thismonkey.setPosition(0, 0);
		bob.setPosition(0, 4);
		steve.setPosition(0, 7);
		final StackableItem item = (StackableItem)SingletonRepository.getEntityManager().getItem("money");
		steve.equipToInventoryOnly(item);
		assertTrue(steve.isEquipped(item.getName(),1));
		//run logic until the monkey has stopped moving
		do{thismonkey.logic();}while(thismonkey.moving());
		//check that monkey has stolen from steve
		assertFalse(steve.isEquipped(item.getName(),1));
		//run logic until monkey is back to owner
		do{thismonkey.logic();}while(thismonkey.moving());
		//check that monkey has given item to owner
		assertTrue(bob.isEquipped(item.getName(), 1));
	}
}
