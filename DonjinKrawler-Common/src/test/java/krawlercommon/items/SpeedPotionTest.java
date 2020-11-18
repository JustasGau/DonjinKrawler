package krawlercommon.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpeedPotionTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        SpeedPotionData data = new SpeedPotionData(1, 10, 15);
        SpeedPotionData clone = data.deepCopy();
        assertEquals(data.getId(), clone.getId());
        assertEquals(data.getX(), clone.getX());
        assertEquals(data.getY(), clone.getY());
    }
}
