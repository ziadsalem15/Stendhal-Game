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
package games.stendhal.server.core.rp.achievement.factory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.SAXException;

import games.stendhal.server.core.config.AchievementsXMLLoader;
import games.stendhal.server.core.rp.achievement.Category;

import games.stendhal.server.core.rule.defaultruleset.DefaultAchievement;
/**
 * Factory for fighting achievements
 *
 * @author madmetzger
 */
public class FightingAchievementFactory extends AbstractAchievementFactory {

	@Override
	public Collection<DefaultAchievement> createAchievements() {
		AchievementsXMLLoader fightLoader = new AchievementsXMLLoader();
		List<DefaultAchievement> fightingAchievements = new LinkedList<DefaultAchievement>();
		
		/*
		fightingAchievements.add(createAchievement("fight.general.rats", "Rat Hunter", "Kill 15 rats", Achievement.EASY_BASE_SCORE, true,
													new PlayerHasKilledNumberOfCreaturesCondition("rat", 15)));
		fightingAchievements.add(createAchievement("fight.general.exterminator", "Exterminator", "Kill 10 rats of each kind", Achievement.MEDIUM_BASE_SCORE, true,
													new PlayerHasKilledNumberOfCreaturesCondition(10, "rat", "caverat", "razorrat", "venomrat", "zombie rat", "giantrat", "ratman", "ratwoman", "archrat")));
		fightingAchievements.add(createAchievement("fight.general.deer", "Deer Hunter", "Kill 25 deer", Achievement.EASY_BASE_SCORE, true,
													new PlayerHasKilledNumberOfCreaturesCondition("deer", 25)));
		fightingAchievements.add(createAchievement("fight.general.boars", "Boar Hunter", "Kill 20 boar", Achievement.EASY_BASE_SCORE, true,
													new PlayerHasKilledNumberOfCreaturesCondition("boar", 20)));
		fightingAchievements.add(createAchievement("fight.general.bears", "Bear Hunter", "Kill 10 black bears, 10 bears and 10 babybears", Achievement.EASY_BASE_SCORE, true,
													new PlayerHasKilledNumberOfCreaturesCondition(10, "bear", "black bear", "babybear")));
		fightingAchievements.add(createAchievement("fight.general.foxes", "Fox Hunter", "Kill 20 foxes", Achievement.EASY_BASE_SCORE, true,
													new PlayerHasKilledNumberOfCreaturesCondition("fox", 20)));
		fightingAchievements.add(createAchievement("fight.general.safari", "Safari", "Kill 30 tigers, 30 lions and 50 elephants", Achievement.EASY_BASE_SCORE, true,
													new AndCondition(
															new PlayerHasKilledNumberOfCreaturesCondition("tiger", 30),
															new PlayerHasKilledNumberOfCreaturesCondition("lion", 30),
															new PlayerHasKilledNumberOfCreaturesCondition("elephant", 50)
															)));
		fightingAchievements.add(createAchievement("fight.general.ents", "Wood Cutter", "Kill 10 ents, 10 entwifes and 10 old ents", Achievement.MEDIUM_BASE_SCORE, true,
													new PlayerHasKilledNumberOfCreaturesCondition(10, "ent", "entwife", "old ent")));
		fightingAchievements.add(createAchievement("fight.special.rare", "Poacher", "Kill any rare creature", Achievement.HARD_BASE_SCORE, true,
				new KilledRareCreatureCondition()));

		fightingAchievements.add(createAchievement("fight.special.all", "Legend", "Kill all creatures solo", Achievement.HARD_BASE_SCORE, true,
				new KilledSoloAllCreaturesCondition()));
		fightingAchievements.add(createAchievement("fight.special.allshared", "Team Player", "Kill all creatures in a team", Achievement.HARD_BASE_SCORE, true,
				new KilledSharedAllCreaturesCondition())); */
		
		//try to initialize variable achievementURI with the xml string
		
				URI achievementURI = null;
				try {
					achievementURI = new URI("achievements/fighting.xml");
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//try to load the achievements list from the resource file
				
				try {
					fightingAchievements = fightLoader.load(achievementURI);
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//return achievements list
		
		return fightingAchievements;
	}

	@Override
	protected Category getCategory() {
		return Category.FIGHTING;
	}

}
