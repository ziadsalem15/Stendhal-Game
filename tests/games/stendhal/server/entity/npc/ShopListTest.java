package games.stendhal.server.entity.npc;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class ShopListTest {

	@Test
	public void shouldBuyRedLionFishFromFishmongers() {
		ShopList shop = ShopList.get();
		Map<String, Integer> shopContents = shop.get("buyfishes");
		String contents = shopContents.toString();
		String expected ="{perch=22, mackerel=20, roach=10, char=30, clownfish=30, surgeonfish=15, trout=45, cod=10, red lionfish=120}";
		assertEquals(contents,expected);
	}

}
