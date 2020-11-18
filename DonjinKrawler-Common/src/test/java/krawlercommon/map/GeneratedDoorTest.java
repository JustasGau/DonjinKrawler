package krawlercommon.map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Tag("generated")
public class GeneratedDoorTest {
    @Test
    public void testCheckCollision() {
        assertFalse((new Door()).checkCollision(1, 1, 1, 1, 1, 1));
    }
}

