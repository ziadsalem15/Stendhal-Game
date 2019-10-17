package games.stendhal.server.actions.equip;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.Item;


public class EquipMithrilClaspTest {
	@Test
	public void equip_MIthrilClaspKeyring_Test()
	{
		Item mc = SingletonRepository.getEntityManager().getItem("mithril clasp");
		
		assertTrue(mc.canBeEquippedIn("keyring"));
	}

}
