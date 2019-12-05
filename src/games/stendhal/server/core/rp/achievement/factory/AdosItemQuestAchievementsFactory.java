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
 * factory for item related achievements.
 *
 * @author madmetzger
 */
public class AdosItemQuestAchievementsFactory extends AbstractAchievementFactory {

	@Override
	protected Category getCategory() {
		return Category.QUEST_ADOS_ITEMS;
	}

	@Override
	public Collection<DefaultAchievement> createAchievements() {
		//create loader instance
		AchievementsXMLLoader AdosItemQuestLoad = new AchievementsXMLLoader();
		
		List<DefaultAchievement> achievements = new LinkedList<DefaultAchievement>();
		
		/*
		//daily item quest achievements
		achievements.add(createAchievement("quest.special.daily_item.0010", "Ados' Supporter", "Finish daily item quest 10 times",
												Achievement.EASY_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily_item", 2, 9)));
		achievements.add(createAchievement("quest.special.daily_item.0050", "Ados' Provider", "Finish daily item quest 50 times",
												Achievement.EASY_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily_item", 2, 49)));
		achievements.add(createAchievement("quest.special.daily_item.0100", "Ados' Supplier", "Finish daily item quest 100 times",
												Achievement.MEDIUM_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily_item", 2, 99)));
		achievements.add(createAchievement("quest.special.daily_item.0250", "Ados' Stockpiler", "Finish daily item quest 250 times",
												Achievement.MEDIUM_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily_item", 2, 249)));
		achievements.add(createAchievement("quest.special.daily_item.0500", "Ados' Hoarder", "Finish daily item quest 500 times",
												Achievement.HARD_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily_item", 2, 499)));
		*/
		
		//try to initialize variable achievementURI with the xml string
		
		URI achievementURI = null;
		try {
			achievementURI = new URI("achievements/ados_item_quest.xml");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//try to load the achievements list from the resource file
		
		try {
			achievements = AdosItemQuestLoad.load(achievementURI);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return achievements list
		return achievements;
	}

}
