package donjinkrawler.items;

import donjinkrawler.items.tiers.CommonTier;
import krawlercommon.items.ArmorData;
import krawlercommon.items.WeaponData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeaponTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        WeaponData data = new WeaponData(1, 2, 15);
        Weapon original = new Weapon(data, new CommonTier(), 15, 20);
        Weapon clone = original.deepCopy();
        assertEquals(original.getData(), clone.getData());
        assertEquals(original.getDamage(), clone.getDamage());
        assertEquals(original.getSpeed(), clone.getSpeed());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        WeaponData data = new WeaponData(1, 2, 15);
        Weapon original = new Weapon(data, new CommonTier(), 15, 20);
        Weapon clone = original.deepCopy();
        assertEquals(original.getData(), clone.getData());
        assertEquals(original.getDamage(), clone.getDamage());
        assertEquals(original.getSpeed(), clone.getSpeed());
    }

    @Test
    public void testDescription() {
        WeaponData data = new WeaponData(1, 2, 15);
        Weapon original = new Weapon(data, new CommonTier(), 15, 20);
        assertEquals( "Tier: " + original.tier.getName() + "\n"
                + "Damage: " + original.getDamage() + "\n"
                + "Speed: " + original.getSpeed(), original.getDescription());
    }
}
