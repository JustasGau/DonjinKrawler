package krawlercommon.enemies.small;

import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.EnemyType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("generated")
public class GeneratedSmallEnemyFactoryTest {
    @Test
    public void testMake() {
        Enemy actualMakeResult = (new SmallEnemyFactory(new ArrayList<>(), new ArrayList<>())).make(EnemyType.CHICKEN);
        assertEquals(5.0, actualMakeResult.getDamage());
        assertEquals(100.0, actualMakeResult.getHealth());
        assertEquals("Small-Chicken", actualMakeResult.getName());
    }

    @Test
    public void testMake2() {
        assertNull((new SmallEnemyFactory(new ArrayList<>(), new ArrayList<>())).make(null));
    }

    @Test
    public void testMake3() {
        Enemy actualMakeResult = (new SmallEnemyFactory(new ArrayList<>(), new ArrayList<>())).make(EnemyType.SKELETON);
        assertEquals(20.0, actualMakeResult.getDamage());
        assertEquals(100.0, actualMakeResult.getHealth());
        assertEquals("Small-Skeleton", actualMakeResult.getName());
    }

    @Test
    public void testMake4() {
        Enemy actualMakeResult = (new SmallEnemyFactory(new ArrayList<>(), new ArrayList<>())).make(EnemyType.ZOMBIE);
        assertEquals(15.0, actualMakeResult.getDamage());
        assertEquals(100.0, actualMakeResult.getHealth());
        assertEquals("Small-Zombie", actualMakeResult.getName());
    }

    @Test
    public void testMake5() {
        assertNull((new SmallEnemyFactory(new ArrayList<>(), new ArrayList<>())).make(EnemyType.BOSS));
    }
}

