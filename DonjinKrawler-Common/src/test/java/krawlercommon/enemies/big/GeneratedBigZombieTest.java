package krawlercommon.enemies.big;

import krawlercommon.strategies.Attack;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedBigZombieTest {
    @Test
    public void testConstructor() {
        BigZombie actualBigZombie = new BigZombie();
        assertEquals(30.0, actualBigZombie.getDamage());
        assertEquals(100.0, actualBigZombie.getHealth());
        assertEquals("Big-Zombie", actualBigZombie.getName());
    }

    @Test
    public void testUpdate() {
        BigZombie bigZombie = new BigZombie();
        bigZombie.update(new Attack());
        assertEquals("Attack", bigZombie.getInfo());
    }
}

