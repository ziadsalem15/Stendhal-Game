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
package games.stendhal.server.entity.item.scroll;
//package games.stendhal.server.entity.creature;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;


import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.creature.BabyDragon;
import games.stendhal.server.entity.item.scroll.BlankPetScroll;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.RPClass.ItemTestHelper;
public class SummonPetScrollTest extends QuestHelper {
  
	@Test
  public void SummonPetScrollTest() {
	ItemTestHelper.generateRPClasses();
	MockStendlRPWorld.get();
		
	final StendhalRPZone zone = new StendhalRPZone("zone")
			{
				@Override
				public boolean isInProtectionArea(final Entity entity)
				{
				  return false;	
				}
			};
	MockStendlRPWorld.get().addRPZone(zone);
	final Player bob = PlayerTestHelper.createPlayer("bob");
	zone.add(bob);
    BabyDragon drako = new BabyDragon(bob);
    
     BlankPetScroll blank_scroll = (BlankPetScroll) SingletonRepository.getEntityManager().getItem("blank pet scroll");
   // BlankPetScroll blank_scroll  = (BlankPetScroll)ItemTestHelper.createItem("blank pet scroll");
    //BlankPetScroll blank_scroll = new BlankPetScroll("blank_scroll","blank pet scroll","scroll",null);
 
 
    
    drako.setName("drako");
    drako.setAtk(450);
    drako.setBaseHP(450);
    bob.setPet(drako);
    blank_scroll.useScroll(bob);
    
    SummonPetScroll summon_scroll = (SummonPetScroll) bob.getFirstEquipped("summon pet scroll"); 
     summon_scroll.useScroll(bob);
    
    assertThat(bob.getPet().getName(), is("drako"));
    assertThat(bob.getPet().getAtk(), is(450));
    assertThat(bob.getPet().getBaseHP(), is(450));
    
	
}
}
