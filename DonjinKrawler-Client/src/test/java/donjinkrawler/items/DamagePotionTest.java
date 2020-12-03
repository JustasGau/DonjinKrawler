package donjinkrawler.items;

import krawlercommon.items.DamagePotionData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DamagePotionTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        DamagePotionData data = new DamagePotionData(1, 2, 15);
        DamagePotion original = new DamagePotion(data);
        DamagePotion clone = original.deepCopy();
        assertEquals(original.getData(), clone.getData());
    }

    @Test
    public void testGetDescription() {
        DamagePotionData data = new DamagePotionData(1, 2, 15);
        DamagePotion original = new DamagePotion(data);
        assertEquals("Adds some damage buff.", original.getDescription());
    }
}
