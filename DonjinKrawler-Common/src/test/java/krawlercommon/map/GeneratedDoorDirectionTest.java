package krawlercommon.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratedDoorDirectionTest {
    @Test
    public void testValueOf() {
        assertEquals(DoorDirection.BOTTOM, DoorDirection.valueOf("BOTTOM"));
    }

    @Test
    public void testValues() {
        assertEquals(4, DoorDirection.values().length);
    }
}

