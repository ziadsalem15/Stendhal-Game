package games.stendhal.server.core.config;

import static org.hamcrest.CoreMatchers.is;
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
		AchievementGroupsXMLLoader loader = new AchievementGroupsXMLLoader(new URI("testachievement.xml"));
		List<DefaultAchievement> list = loader.load();
		assertThat(Boolean.valueOf(list.isEmpty()), is(Boolean.TRUE));
		//assertThat(ach.getImplementationClass(), notNullValue());
	}
	
}
