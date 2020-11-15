package krawlercommon.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DamagePotionTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        DamagePotionData data = new DamagePotionData(1, 10, 15);
        DamagePotionData clone = data.deepCopy();
        assertEquals(data.getId(), clone.getId());
        assertEquals(data.getX(), clone.getX());
        assertEquals(data.getY(), clone.getY());
    }
}
