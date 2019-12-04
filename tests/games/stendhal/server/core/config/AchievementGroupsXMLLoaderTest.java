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

	@Before
	public void setUp() {
		MockStendlRPWorld.get();
	}

	@After
	public void tearDown() {
		MockStendlRPWorld.reset();
	}
	
	@Test
	public void testLoad() throws URISyntaxException, SAXException, IOException {
		AchievementGroupsXMLLoader loader = new AchievementGroupsXMLLoader(new URI("testspells.xml"));
		List<DefaultAchievement> list = loader.load();
		assertThat(Boolean.valueOf(list.isEmpty()), is(Boolean.TRUE));
		//assertThat(ach.getImplementationClass(), notNullValue());
	}
	
}
