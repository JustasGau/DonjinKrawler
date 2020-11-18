package krawlercommon.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArmorDataTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        ArmorData data = new ArmorData(1, 10, 15);
        ArmorData clone = data.deepCopy();
        assertEquals(data.getId(), clone.getId());
        assertEquals(data.getX(), clone.getX());
        assertEquals(data.getY(), clone.getY());
    }
}
