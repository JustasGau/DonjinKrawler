package krawlercommon.enemies.big;

import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.EnemyType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("generated")
public class GeneratedBigEnemyFactoryTest {
    @Test
    public void testMake() {
        Enemy actualMakeResult = (new BigEnemyFactory()).make(EnemyType.CHICKEN);
        assertEquals(15.0, actualMakeResult.getDamage());
        assertEquals(100.0, actualMakeResult.getHealth());
        assertEquals("Big-Chicken", actualMakeResult.getName());
    }

    @Test
    public void testMake2() {
        assertNull((new BigEnemyFactory()).make(null));
    }

    @Test
    public void testMake3() {
        Enemy actualMakeResult = (new BigEnemyFactory()).make(EnemyType.SKELETON);
        assertEquals(40.0, actualMakeResult.getDamage());
        assertEquals(100.0, actualMakeResult.getHealth());
        assertEquals("Big-Skeleton", actualMakeResult.getName());
    }

    @Test
    public void testMake4() {
        Enemy actualMakeResult = (new BigEnemyFactory()).make(EnemyType.ZOMBIE);
        assertEquals(30.0, actualMakeResult.getDamage());
        assertEquals(100.0, actualMakeResult.getHealth());
        assertEquals("Big-Zombie", actualMakeResult.getName());
    }

    @Test
    public void testMake5() {
        assertNull((new BigEnemyFactory()).make(EnemyType.BOSS));
    }
}

