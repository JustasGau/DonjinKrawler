package donjinkrawler.items;

import krawlercommon.items.SpeedPotionData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpeedPotionTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        SpeedPotionData data = new SpeedPotionData(1, 2, 15);
        SpeedPotion original = new SpeedPotion(data);
        SpeedPotion clone = original.deepCopy();
        assertEquals(original.getData(), clone.getData());
    }

    @Test
    public void testGetDescription() {
        SpeedPotionData data = new SpeedPotionData(1, 2, 15);
        SpeedPotion original = new SpeedPotion(data);
        assertEquals("Adds some speed buff.", original.getDescription());
    }
}
