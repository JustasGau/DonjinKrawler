package krawlercommon.enemies.small;

import krawlercommon.strategies.Attack;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedSmallZombieTest {
    @Test
    public void testConstructor() {
        SmallZombie actualSmallZombie = new SmallZombie();
        assertEquals(15.0, actualSmallZombie.getDamage());
        assertEquals(100.0, actualSmallZombie.getHealth());
        assertEquals("Small-Zombie", actualSmallZombie.getName());
    }

    @Test
    public void testUpdate() {
        SmallZombie smallZombie = new SmallZombie();
        smallZombie.update(new Attack());
        assertEquals("Attack", smallZombie.getInfo());
    }
}

