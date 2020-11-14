package krawlercommon.enemies.big;

import krawlercommon.strategies.Attack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratedBigChickenTest {
    @Test
    public void testConstructor() {
        BigChicken actualBigChicken = new BigChicken();
        assertEquals(15.0, actualBigChicken.getDamage());
        assertEquals(100.0, actualBigChicken.getHealth());
        assertEquals("Big-Chicken", actualBigChicken.getName());
    }

    @Test
    public void testUpdate() {
        BigChicken bigChicken = new BigChicken();
        bigChicken.update(new Attack());
        assertsEquals("Attack", bigChicken.getInfo());
    }
}

