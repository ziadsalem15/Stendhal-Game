package games.stendhal.server.core.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//import games.stendhal.server.core.rp.achievement.Achievement;
import games.stendhal.server.core.rule.defaultruleset.DefaultAchievement;
//import games.stendhal.server.core.rule.defaultruleset.DefaultAchievement;
public class AchievementGroupsXMLLoader extends DefaultHandler {

		private static final Logger LOGGER = Logger.getLogger(AchievementGroupsXMLLoader.class);

		/** The main zone configuration file. */
		protected URI uri;

		/**
		 * Create an xml based loader of achievement groups.
		 *
		 * @param uri
		 *            The location of the configuration file.
		 */
		public AchievementGroupsXMLLoader(final URI uri) {
			this.uri = uri;
		}


		/**
		 * Create an xml based loader of achievement groups.
		 *
		 * @param uri
		 *            The location of the configuration file.
		 */
		public AchievementGroupsXMLLoader(final String uri) {
			try {
				this.uri = new URI(uri);
			} catch (URISyntaxException e) {
				LOGGER.error(e, e);
			}
		}

		/**
		 * Loads achievements
		 *
		 * @return list of all achievements.
		 */
		public List<DefaultAchievement> load() throws SAXException, IOException {
			final GroupsXMLLoader groupsLoader = new GroupsXMLLoader(uri);

			final List<URI> groups = groupsLoader.load();
			final AchievementsXMLLoader loader = new AchievementsXMLLoader();

			final List<DefaultAchievement> list = new LinkedList<DefaultAchievement>();
			for (final URI groupUri : groups) {
				LOGGER.debug("Loading spell group [" + groupUri + "]");
				list.addAll(loader.load(groupUri));
			}

//			final GroupsXMLLoader groupsLoader = new GroupsXMLLoader(uri);
//			final List<DefaultAchievement> list = new LinkedList<DefaultAchievement>();
//			try {
//				List<URI> groups = groupsLoader.load();
//
//				// Load each group
//				for (final URI tempUri : groups) {
//					final AchievementsXMLLoader loader = new AchievementsXMLLoader();
//
//					try {
//						list.addAll(loader.load(tempUri));
//					} catch (final SAXException ex) {
//						LOGGER.error("Error loading creature group: " + tempUri, ex);
//					}
//				}
//			} catch (SAXException e) {
//				LOGGER.error(e, e);
//			} catch (IOException e) {
//				LOGGER.error(e, e);
//			}
			return list;
		}
		
		
		

}
