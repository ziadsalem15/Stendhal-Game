package games.stendhal.server.core.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//import games.stendhal.server.core.rp.achievement.Achievement;
import games.stendhal.server.core.rp.achievement.Category;
import games.stendhal.server.core.rule.defaultruleset.DefaultAchievement;
//import games.stendhal.server.core.rule.defaultruleset.DefaultAchievement;
//import games.stendhal.server.entity.npc.ChatCondition;

public final class AchievementsXMLLoader extends DefaultHandler {

	/** the logger instance. */
	private static final Logger LOGGER = Logger.getLogger(AchievementsXMLLoader.class);
	
	private String name;
	
	private String clazz;
	
	private String subclass;
	
//	private String description;
	
	private String text;
//	
//	private String tileid;
//	
	private boolean attributesTag;
//
//	private String identifier;
//	private String title;
//	private String description;
//	private boolean active;
//	private int score;
	
	private String identifier;

	private String title;

	private Category category;

	private String description;

	private int baseScore;

	/** is this achievement visible? */
	private boolean active;

	private String condition;
	
	private String conditionText;
	
	private List<DefaultAchievement> list;
	
	/** Attributes of the achievement */
	private Map<String, String> attributes;
	
//	private boolean condition;

	
	public List<DefaultAchievement> load(final URI uri) throws SAXException {
		list = new LinkedList<DefaultAchievement>();
		// Use the default (non-validating) parser
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Parse the input
			final SAXParser saxParser = factory.newSAXParser();

			final InputStream is = AchievementsXMLLoader.class.getResourceAsStream(uri.getPath());

			if (is == null) {
				throw new FileNotFoundException("cannot find resource '" + uri
						+ "' in classpath");
			}
			try {
				saxParser.parse(is, this);
			} finally {
				is.close();
			}
		} catch (final ParserConfigurationException t) {
			LOGGER.error(t);
		} catch (final IOException e) {
			LOGGER.error(e);
			throw new SAXException(e);
		}

		return list;
	}
	
	@Override
	public void startDocument() {
		// do nothing
	}

	@Override
	public void endDocument() {
		// do nothing
	}
	
	@Override
	public void startElement(final String namespaceURI, final String lName, final String qName,
			final Attributes attrs) {
		text = "";
		if(qName.equals("achievement")) {
			name = attrs.getValue("name");
			//condition = attrs.getValue("condition");
			attributes = new LinkedHashMap<String, String>();
		}
		else if (qName.equals("type")) {
			clazz = attrs.getValue("class");
			subclass = attrs.getValue("subclass");
		}
		else if (qName.equals("attributes")) {
		 attributesTag = true;
		}
		else if (attributesTag && qName.equals("identifier")) {
			identifier = attrs.getValue("value");
		}
		else if (attributesTag && qName.equals("title")) {
			title = attrs.getValue("value");
		} else if (attributesTag && qName.equals("description")) {
			description = attrs.getValue("value");
		} else if (attributesTag && qName.equals("score")) {
			baseScore = Integer.parseInt(attrs.getValue("value"));
		} else if (attributesTag && qName.equals("active")) {
			active = Boolean.parseBoolean("value");
		} else if (attributesTag && qName.equals("condition")) {
			conditionText = attrs.getValue("value");
			//condition = new ChatCondition(conditionText);
		}
	}
	
	@Override
	public void endElement(final String namespaceURI, final String sName, final String qName) {
		if (qName.equals("achievement")) {
			final DefaultAchievement achievement = new DefaultAchievement(identifier, title, category, description, baseScore, active, condition);
//			achievement.setAttributes(attributes);
			list.add(achievement);
			}
		else if (qName.equals("attributes")) {
			attributesTag = false;
		}
	}
	
	@Override
	public void characters(final char[] buf, final int offset, final int len) {
		text = text + (new String(buf, offset, len)).trim();
	}
}
