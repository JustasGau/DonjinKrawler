package krawlercommon.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratedObstacleTypeTest {
    @Test
    public void testGetTypeByNumber() {
        assertEquals(ObstacleType.SLIME, ObstacleType.getTypeByNumber("Number"));
        assertEquals(ObstacleType.LAVA, ObstacleType.getTypeByNumber("1"));
        assertEquals(ObstacleType.SPIKES, ObstacleType.getTypeByNumber("2"));
    }
}

