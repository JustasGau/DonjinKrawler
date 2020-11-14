package krawlercommon.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GeneratedObstacleTest {
    @Test
    public void testSetObstacleType() {
        Obstacle obstacle = new Obstacle();
        obstacle.setObstacleType(ObstacleType.LAVA);
        assertEquals(ObstacleType.LAVA, obstacle.getObstacleType());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        assertNull((new Obstacle()).clone().getObstacleType());
    }
}

