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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import games.stendhal.server.core.config.AchievementGroupsXMLLoader;
import games.stendhal.server.core.rp.achievement.Achievement;
import games.stendhal.server.core.rp.achievement.Category;
import games.stendhal.server.core.rule.defaultruleset.DefaultAchievement;
import games.stendhal.server.entity.npc.ChatCondition;
/**
 * Factory class for achievements creation with a fixed category
 *
 * @author madmetzger
 */
public abstract class AbstractAchievementFactory {
	
	

	/**
	 * @return the category the factory should use
	 */
	protected abstract Category getCategory();

	/**
	 * Creates a collection of achievements
	 *
	 * @return the achievments
	 */
	public abstract Collection<DefaultAchievement> createAchievements();

	/**
	 * Creates a single achievement
	 * @param identifier
	 * @param title
	 * @param description
	 * @param score
     * @param active
	 * @param condition
	 * @return the new Achievement
	 */
	protected Achievement createAchievement(String identifier, String title, String description, int score, boolean active, ChatCondition condition) {
		return new Achievement(identifier, title, getCategory(),  description, score, active, condition);
	}

	/**
	 * Create a list of all known achievement factories
	 * @return the list of factories
	 */
	public static List<AchievementGroupsXMLLoader> createFactories() {
		
		// create loaders for each factory type of achieve
		AchievementGroupsXMLLoader AdosItemQuestAchievementLoader = new AchievementGroupsXMLLoader("achievements/ados_item_quest.xml");
		AchievementGroupsXMLLoader ExperienceAchievementLoader = new AchievementGroupsXMLLoader("achievements/experience.xml");
		AchievementGroupsXMLLoader FightingAchievementLoader = new AchievementGroupsXMLLoader("achievements/fighting.xml");
		AchievementGroupsXMLLoader FriendAchievementLoader = new AchievementGroupsXMLLoader("achievements/friend.xml");
		AchievementGroupsXMLLoader InteriorZoneAchievementLoader = new AchievementGroupsXMLLoader("achievements/interior_zone.xml");
		AchievementGroupsXMLLoader ItemAchievementLoader = new AchievementGroupsXMLLoader("achievements/item.xml");
		AchievementGroupsXMLLoader ObtainAchievementsLoader = new AchievementGroupsXMLLoader("achievements/obtain.xml");
		AchievementGroupsXMLLoader OutsideZoneAchievementLoader = new AchievementGroupsXMLLoader("achievements/outside_zone.xml");
		AchievementGroupsXMLLoader ProductionAchievementLoader = new AchievementGroupsXMLLoader("achievements/production.xml");
		AchievementGroupsXMLLoader QuestAchievementLoader = new AchievementGroupsXMLLoader("achievements/quest.xml");
		AchievementGroupsXMLLoader SemosMonsterQuestAchievementLoader = new AchievementGroupsXMLLoader("achievements/semos_monster_quest.xml");
		AchievementGroupsXMLLoader UndergroundZoneAchievementLoader = new AchievementGroupsXMLLoader("achievements/underground_zone.xml");
		AchievementGroupsXMLLoader KirdnehItemAchievementLoader = new AchievementGroupsXMLLoader("achievements/kirdneh_item.xml");
		AchievementGroupsXMLLoader MithrilbourghEnemyArmyAchievementLoader = new AchievementGroupsXMLLoader("achievements/mithrilbourgh_enemy_army.xml");
		
		
		List<AchievementGroupsXMLLoader> list = new LinkedList<AchievementGroupsXMLLoader>();
		//add loaders into the list
		list.add(AdosItemQuestAchievementLoader);
		list.add(ExperienceAchievementLoader);
		list.add(FightingAchievementLoader);
		list.add(FriendAchievementLoader);
		list.add(InteriorZoneAchievementLoader);
		list.add(ItemAchievementLoader);
		list.add(ObtainAchievementsLoader);
		list.add(OutsideZoneAchievementLoader);
		list.add(ProductionAchievementLoader);
		list.add(QuestAchievementLoader);
		list.add(SemosMonsterQuestAchievementLoader);
		list.add(UndergroundZoneAchievementLoader);
		list.add(KirdnehItemAchievementLoader);
		list.add(MithrilbourghEnemyArmyAchievementLoader);
		return list;
	}
}
