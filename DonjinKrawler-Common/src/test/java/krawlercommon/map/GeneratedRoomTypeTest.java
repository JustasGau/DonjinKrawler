package krawlercommon.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratedRoomTypeTest {
    @Test
    public void testValueOf() {
        assertEquals(RoomType.BOSS, RoomType.valueOf("BOSS"));
    }

    @Test
    public void testValues() {
        assertEquals(3, RoomType.values().length);
    }
}

