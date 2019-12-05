package games.stendhal.server.core.rp.achievement;


import static org.junit.Assert.*;


import org.junit.Test;


import games.stendhal.server.entity.npc.condition.QuestStateGreaterThanCondition;


public class AchievementTest {
    
	Category c =  Category.EXPERIENCE;
	Achievement a = new Achievement("some identifier", "some title", c, "some description", 
            Achievement.EASY_BASE_SCORE, true, new QuestStateGreaterThanCondition("daily_item", 2, 9));
	
	


	@Test
	public void testBaseScores() {
		assertEquals(Achievement.EASY_BASE_SCORE, 1);
		assertEquals(Achievement.MEDIUM_BASE_SCORE, 2);
		assertEquals(Achievement.HARD_BASE_SCORE, 5);
		
	}
	
	@Test
	public void testGetters() {
		assertEquals(a.getIdentifier(),"some identifier");
		assertEquals(a.getTitle(),"some title");
		assertEquals(a.getCategory(),Category.EXPERIENCE);
		assertEquals(a.getDescription(),"some description"); 
		assertEquals(a.getBaseScore(), Achievement.EASY_BASE_SCORE);
	}
	
	@Test
	public void testBooleans() {
		assertEquals(a.isActive(), true);
		
		
	}
	
	@Test
	public void testToString() {
		assertEquals(a.toString(),"Achievement<id: "+"some identifier"+", title: "+"some title"+">");
		
	}

}
