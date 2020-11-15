package donjinkrawler.items;

import krawlercommon.items.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneratedItemMakerTest {
    @Test
    public void testMakeItem() {
        assertNull(ItemMaker.makeItem(null));

        ArmorData armorData = new ArmorData();
        assertTrue(ItemMaker.makeItem(armorData) instanceof Armor);

        SpeedPotionData speedPotionData = new SpeedPotionData();
        assertTrue(ItemMaker.makeItem(speedPotionData) instanceof SpeedPotion);

        HealthPotionData healthPotionData = new HealthPotionData();
        assertTrue(ItemMaker.makeItem(healthPotionData) instanceof HealthPotion);

        DamagePotionData damagePotionData = new DamagePotionData();
        assertTrue(ItemMaker.makeItem(damagePotionData) instanceof DamagePotion);

        WeaponData weaponData = new WeaponData();
        assertTrue(ItemMaker.makeItem(weaponData) instanceof Weapon);
    }
}

