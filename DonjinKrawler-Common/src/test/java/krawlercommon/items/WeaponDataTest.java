package krawlercommon.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeaponDataTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        WeaponData data = new WeaponData(1, 10, 15);
        WeaponData clone = data.deepCopy();
        assertEquals(data.getId(), clone.getId());
        assertEquals(data.getX(), clone.getX());
        assertEquals(data.getY(), clone.getY());
    }
}
