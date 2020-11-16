package krawlercommon.enemies.big;

import krawlercommon.strategies.Attack;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedBigSkeletonTest {
    @Test
    public void testConstructor() {
        BigSkeleton actualBigSkeleton = new BigSkeleton();
        assertEquals(40.0, actualBigSkeleton.getDamage());
        assertEquals(100.0, actualBigSkeleton.getHealth());
        assertEquals("Big-Skeleton", actualBigSkeleton.getName());
    }

    @Test
    public void testUpdate() {
        BigSkeleton bigSkeleton = new BigSkeleton();
        bigSkeleton.update(new Attack());
        assertEquals("Attack", bigSkeleton.getInfo());
    }
}

