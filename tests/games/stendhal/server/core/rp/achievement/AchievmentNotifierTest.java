package games.stendhal.server.core.rp.achievement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;

//import java.util.List;

import org.junit.Test;
//import com.google.common.collect.ImmutableList;
import games.stendhal.server.core.rp.achievement.Achievement;
import games.stendhal.server.core.rp.achievement.AchievementNotifier;
import games.stendhal.server.entity.npc.condition.QuestStateGreaterThanCondition;
//import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.player.Player;
import utilities.PlayerTestHelper;


public class AchievmentNotifierTest {
	//AchievementNotifier instance1.initialize();
	@Test
	public void initializeTest()
	{
		AchievementNotifier instance1 = AchievementNotifier.get();
		instance1.initialize();		
		//final Player player = PlayerTestHelper.createPlayer("player");
		//String achievementIdentifier = "fight.general.rats";
		//assertTrue(!(player.hasReachedAchievement(achievementIdentifier)));
		//instance1.awardAchievementIfNotYetReached(player, achievementIdentifier);
		//assertFalse(player.hasReachedAchievement("fight.general.rats"));

	}
	@Test
	public void onItemLootTest()
	{
		AchievementNotifier instance2 = AchievementNotifier.get();
		final Player player = PlayerTestHelper.createPlayer("player");
		instance2.onItemLoot(player);
	}
	
	@Test
	public void awardAchievementTest()
	{	
		AchievementNotifier instance3 = AchievementNotifier.get();
		String achievementIdentifier = "fight.general.exterminator";
		final Player player2 = PlayerTestHelper.createPlayer("player");
		player2.initReachedAchievements();
		assertTrue(!(player2.hasReachedAchievement(achievementIdentifier)));
		instance3.awardAchievementIfNotYetReached(player2, achievementIdentifier);
		assertTrue(!(player2.hasReachedAchievement(achievementIdentifier)));
	}
	
	@Test
	public void onLoginTest()
	{
		AchievementNotifier instance4 = AchievementNotifier.get();
		final Player player3 = PlayerTestHelper.createPlayer("player");
		instance4.onLogin(player3);
	}
	
	@Test
	public void logReachingTest()
	{
		AchievementNotifier instance5 = AchievementNotifier.get();
		Player player4 = PlayerTestHelper.createPlayer("player");
		player4.initReachedAchievements();
		Category c =  Category.EXPERIENCE;
		Achievement a = new Achievement("some identifier", "some title", c, "some description",             Achievement.EASY_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily_item", 2, 9));
		instance5.logReachingOfAnAchievement(player4, a);

	}
	
	@Test
	public void notifyPlayerTest()
	{
		AchievementNotifier instance5 = AchievementNotifier.get();
		Player player4 = PlayerTestHelper.createPlayer("player");
		player4.initReachedAchievements();
		List<Achievement> c =  instance5.getAchievements();
		instance5.notifyPlayerAboutReachedAchievements(player4, c);

	}
	
	@Test
	public void notifyTest()
	{
		AchievementNotifier instance5 = AchievementNotifier.get();
		Player player4 = PlayerTestHelper.createPlayer("player");
		Category c =  Category.EXPERIENCE;
		Achievement a = new Achievement("fight.general.rats", "some title", c, "some description",             Achievement.EASY_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily_item", 2, 9)); 
		instance5.notifyPlayerAboutReachedAchievement(player4, a);

	}
	@Test
	public void getAchievementsTest()
	{
		AchievementNotifier instance4 = AchievementNotifier.get();
		List <Achievement> achievmentsList = instance4.getAchievements() ;
		assertEquals(instance4.getAchievements(),achievmentsList);
		
	}
}

