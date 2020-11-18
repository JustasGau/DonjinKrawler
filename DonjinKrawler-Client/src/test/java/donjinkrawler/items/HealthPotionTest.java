package donjinkrawler.items;

import krawlercommon.items.HealthPotionData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthPotionTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        HealthPotionData data = new HealthPotionData(1, 2, 15);
        HealthPotion original = new HealthPotion(data);
        HealthPotion clone = original.deepCopy();
        assertEquals(original.getData(), clone.getData());
    }

    @Test
    public void testGetDescription() {
        HealthPotionData data = new HealthPotionData(1, 2, 15);
        HealthPotion original = new HealthPotion(data);
        assertEquals("Restore some HP.", original.getDescription());
    }
}
