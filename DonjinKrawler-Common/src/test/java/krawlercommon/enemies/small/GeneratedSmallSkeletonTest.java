package krawlercommon.enemies.small;

import krawlercommon.strategies.Attack;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedSmallSkeletonTest {
    @Test
    public void testConstructor() {
        SmallSkeleton actualSmallSkeleton = new SmallSkeleton();
        assertEquals(20.0, actualSmallSkeleton.getDamage());
        assertEquals(100.0, actualSmallSkeleton.getHealth());
        assertEquals("Small-Skeleton", actualSmallSkeleton.getName());
    }

    @Test
    public void testUpdate() {
        SmallSkeleton smallSkeleton = new SmallSkeleton();
        smallSkeleton.update(new Attack());
        assertEquals("Attack", smallSkeleton.getInfo());
    }
}

