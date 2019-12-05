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
//import games.stendhal.server.core.rp.achievement.Achievement;
import games.stendhal.server.core.rp.achievement.Category;
import games.stendhal.server.core.rule.defaultruleset.DefaultAchievement;
//import games.stendhal.server.entity.npc.condition.QuestStateGreaterThanCondition;
/**
 * Factory for quest achievements
 *
 * @author madmetzger
 */
public class SemosMonsterQuestAchievementFactory extends AbstractAchievementFactory {

	@Override
	public Collection<DefaultAchievement> createAchievements() {
		List<DefaultAchievement> questAchievements = new LinkedList<DefaultAchievement>();
		AchievementsXMLLoader xpLoader = new AchievementsXMLLoader();
		//daily monster quest achievements
		/*
		questAchievements.add(createAchievement("quest.special.daily.0010", "Semos' Protector", "Finish daily monster quest 10 times",
												Achievement.EASY_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily", 2, 9)));
		questAchievements.add(createAchievement("quest.special.daily.0050", "Semos' Guardian", "Finish daily monster quest 50 times",
												Achievement.EASY_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily", 2, 49)));
		questAchievements.add(createAchievement("quest.special.daily.0100", "Semos' Hero", "Finish daily monster quest 100 times",
												Achievement.MEDIUM_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily", 2, 99)));
		questAchievements.add(createAchievement("quest.special.daily.0250", "Semos' Champion", "Finish daily monster quest 250 times",
												Achievement.MEDIUM_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily", 2, 249)));
		questAchievements.add(createAchievement("quest.special.daily.0500", "Semos' Vanquisher", "Finish daily monster quest 500 times",
												Achievement.HARD_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily", 2, 499)));*/

												URI achievementURI = null;
												try {
													achievementURI = new URI("achievements/semos_monster_quest.xml");
												} catch (URISyntaxException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												
												//try to load the achievements list from the resource file
												
												try {
													questAchievements = xpLoader.load(achievementURI);
												} catch (SAXException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												
												//return achievements list

		return questAchievements;
	}

	@Override
	protected Category getCategory() {
		return Category.QUEST_SEMOS_MONSTER;
	}

}
