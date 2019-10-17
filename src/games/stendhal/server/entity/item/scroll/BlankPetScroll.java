/* $Id$
 /***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
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

import java.util.Map;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.creature.Pet;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
//import games.stendhal.server.entity.player.PetOwner;;

/**
 * Represents an empty/blank pet scroll.
 */
public class BlankPetScroll extends Scroll {

	// private static final Logger logger = Logger.getLogger(EmptyScroll.class);

	/**
	 * Creates a new blank pet scroll.
	 *
	 * @param name
	 * @param clazz
	 * @param subclass
	 * @param attributes
	 */
	public BlankPetScroll(final String name, final String clazz, final String subclass,
			final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
	}

	/**
	 * Copy constructor.
	 *
	 * @param item
	 *            item to copy
	 */
	public BlankPetScroll(final BlankPetScroll item) {
		super(item);
	}

	/**
	 * Use a blank pet scroll.
	 *
	 * @param player
	 * @return always true
	 */
	@Override
	protected boolean useScroll(final Player player) {
		final StendhalRPZone zone = player.getZone();
		
		 String petName = player.getPet().getName();
		 if(petName == "baby dragon")
			 petName = "baby_dragon";
		 if(petName == "purple dragon")
			 petName = "purple_dragon";
		 
		final String petTitle = player.getPet().getTitle();
		final int petHp = player.getPet().getHP();
		final int petBasehp = player.getPet().getBaseHP();
		final int petXP = player.getPet().getXP();
		final int petWeight = player.getPet().getWeight();
		final int petAtk = player.getPet().getAtk();
		final int petDef = player.getPet().getDef();
		

		if (zone.isTeleportInAllowed(player.getX(), player.getY())) {
			final Item summonPetScroll = SingletonRepository.getEntityManager().getItem(
					"summon pet scroll");
			summonPetScroll.setInfoString(petName+"_"+petTitle+"_"+petHp+"_"+petBasehp+"_"+petXP+"_"+petWeight+"_"+petAtk+"_"+petDef);
			player.equipOrPutOnGround(summonPetScroll);

			final Pet pet = player.getPet();


			if (pet != null) {
				//petOwner.storePet(pet);
				player.removePet(pet);
				pet.getZone().remove(pet);
				player.sendPrivateText("Amazingly your pet melds with the scroll. It just walked right into the page!");
			}

			return true;
		} else {
			player.sendPrivateText("The strong anti magic aura in this area prevents the scroll from working!");
			return false;
		}
	}

}
