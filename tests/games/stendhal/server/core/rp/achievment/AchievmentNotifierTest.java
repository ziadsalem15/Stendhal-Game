package games.stendhal.server.core.rp.achievment;

import org.junit.Test;

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
	
}
