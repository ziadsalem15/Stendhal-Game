package games.stendhal.server.core.rp.achievement;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
//import com.google.common.collect.ImmutableList;
import games.stendhal.server.core.rp.achievement.AchievementNotifier;
import games.stendhal.server.entity.player.Player;
import utilities.PlayerTestHelper;


public class AchievmentNotifierTest {
	
	@Test
	public void initializeTest()
	{
		AchievementNotifier instance1 = AchievementNotifier.get();
		instance1.initialize();		
		final Player player = PlayerTestHelper.createPlayer("player");
		instance1.onItemLoot(player);
		String achievementIdentifier = null;
		instance1.awardAchievementIfNotYetReached(player, achievementIdentifier);

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
		String achievementIdentifier = null;
		final Player player2 = PlayerTestHelper.createPlayer("player");
		instance3.awardAchievementIfNotYetReached(player2, achievementIdentifier);
	}
	
	@Test
	public void onLoginTest()
	{
		AchievementNotifier instance4 = AchievementNotifier.get();
		final Player player3 = PlayerTestHelper.createPlayer("player");
		instance4.onLogin(player3);
	}
	
	@Test
	public void getAchievementsTest()
	{
		AchievementNotifier instance4 = AchievementNotifier.get();
		List <Achievement> achievmentsList = instance4.getAchievements() ;
		assertEquals(instance4.getAchievements(),achievmentsList);
		
	}
}
