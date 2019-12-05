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
 * Factory for experience achievements
 *
 * @author madmetzger
 */
public class ExperienceAchievementFactory extends AbstractAchievementFactory {

	@Override
	protected Category getCategory() {
		return Category.EXPERIENCE;
	}

	@Override
	public Collection<DefaultAchievement> createAchievements() {
		List<DefaultAchievement> xpAchievements = new LinkedList<DefaultAchievement>();
		//create loader instance
		AchievementsXMLLoader xpLoader = new AchievementsXMLLoader();
		
		/*
		xpAchievements.add(createAchievement("xp.level.010", "Greenhorn", "Reach level 10", Achievement.EASY_BASE_SCORE, true,
												new LevelGreaterThanCondition(9)));
		xpAchievements.add(createAchievement("xp.level.050", "Novice", "Reach level 50", Achievement.EASY_BASE_SCORE, true,
												new LevelGreaterThanCondition(49)));
		xpAchievements.add(createAchievement("xp.level.100", "Apprentice", "Reach level 100", Achievement.EASY_BASE_SCORE, true,
												new LevelGreaterThanCondition(99)));
		xpAchievements.add(createAchievement("xp.level.200", "Adventurer", "Reach level 200", Achievement.MEDIUM_BASE_SCORE, true,
												new LevelGreaterThanCondition(199)));
		xpAchievements.add(createAchievement("xp.level.300", "Experienced Adventurer", "Reach level 300", Achievement.MEDIUM_BASE_SCORE, true,
												new LevelGreaterThanCondition(299)));
		xpAchievements.add(createAchievement("xp.level.400", "Master Adventurer", "Reach level 400", Achievement.MEDIUM_BASE_SCORE, true,
												new LevelGreaterThanCondition(399)));
		xpAchievements.add(createAchievement("xp.level.500", "Stendhal Master", "Reach level 500", Achievement.HARD_BASE_SCORE, true,
												new LevelGreaterThanCondition(499)));
		xpAchievements.add(createAchievement("xp.level.597", "Stendhal High Master", "Reach level 597", Achievement.HARD_BASE_SCORE, true,
												new LevelGreaterThanCondition(596)));
												
												*/
		URI achievementURI = null;
		try {
			achievementURI = new URI("achievements/experience.xml");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//try to load the achievements list from the resource file
		
		try {
			xpAchievements = xpLoader.load(achievementURI);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return achievements list
		return xpAchievements;
	}

}
