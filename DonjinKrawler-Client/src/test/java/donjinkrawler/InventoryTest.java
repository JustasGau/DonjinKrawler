package donjinkrawler;

import donjinkrawler.items.Armor;
import donjinkrawler.items.Weapon;
import donjinkrawler.items.tiers.CommonTier;
import krawlercommon.items.ArmorData;
import krawlercommon.items.WeaponData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InventoryTest {

    @Test
    public void testOpenAndClose() {
        Inventory inventory = new Inventory();
        inventory.open();
        inventory.close();
        Assertions.assertTrue(true);
    }

    @Test
    public void testAddWeapon() {
        Inventory inventory = new Inventory();
        inventory.addWeapon(new Weapon(new WeaponData(1,2,3), new CommonTier(), 15, 20));
        Assertions.assertTrue(true);
    }

    @Test
    public void testAddArmor() {
        Inventory inventory = new Inventory();
        inventory.addArmor(new Armor(new ArmorData(1,2,3), new CommonTier(), 15, 20, 25));
        Assertions.assertTrue(true);
    }
}
