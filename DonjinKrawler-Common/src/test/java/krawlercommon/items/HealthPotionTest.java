package krawlercommon.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthPotionTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        HealthPotionData data = new HealthPotionData(1, 10, 15);
        HealthPotionData clone = data.deepCopy();
        assertEquals(data.getId(), clone.getId());
        assertEquals(data.getX(), clone.getX());
        assertEquals(data.getY(), clone.getY());
    }
}
