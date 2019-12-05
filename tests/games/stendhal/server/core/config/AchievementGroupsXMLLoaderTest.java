package games.stendhal.server.core.config;

import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;


import games.stendhal.server.core.rule.defaultruleset.DefaultAchievement;
import games.stendhal.server.maps.MockStendlRPWorld;


public class AchievementGroupsXMLLoaderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		MockStendlRPWorld.get();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() {
		MockStendlRPWorld.reset();
	}
	
	/**
	 * Test method for {@link games.stendhal.server.core.config.AchievementsXMLLoader#readZone(org.w3c.dom.Element)}.
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void testLoad() throws URISyntaxException, SAXException, IOException {
		URI myUri = new URI("testachievements.xml");
		AchievementGroupsXMLLoader loader = new AchievementGroupsXMLLoader(myUri);
		List<DefaultAchievement> list = loader.load();
		assertThat(Boolean.valueOf(list.isEmpty()), is(Boolean.FALSE));
		DefaultAchievement achievement = list.get(0);
		assertThat(achievement.getDescription(), is("Finish daily item quest 10 times"));
		//assertThat(achievement.getImplementationClass(), notNullValue());
		//assertThat(achievement.getImplementationClass().getName(), is("games.stendhal.server.AdosItemQuestAchievementsFactory"));
		
		//assertThat(achievement.getDescription(),is("Finish daily item quest 10 times"));
	}
	
	@Test
	public void testLoadString() throws URISyntaxException, SAXException, IOException {
		URI myUri = new URI("testachievements.xml");

		AchievementGroupsXMLLoader loader = new AchievementGroupsXMLLoader(myUri);
		List<DefaultAchievement> list = loader.load();
		assertThat(Boolean.valueOf(list.isEmpty()), is(Boolean.FALSE));
		
		//assertThat(ach.getImplementationClass(), notNullValue());
		//assertThat(ach.getImplementationClass().getName(), is("games.stendhal.server.core.rp.achievement"));

		//SingletonRepository.getEntityManager().addAchievement(ach);

		
		
	}
}
