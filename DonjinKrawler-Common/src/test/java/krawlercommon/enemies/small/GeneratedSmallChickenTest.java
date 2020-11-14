package krawlercommon.enemies.small;

import krawlercommon.strategies.Attack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratedSmallChickenTest {
    @Test
    public void testConstructor() {
        SmallChicken actualSmallChicken = new SmallChicken();
        assertEquals(5.0, actualSmallChicken.getDamage());
        assertEquals(100.0, actualSmallChicken.getHealth());
        assertEquals("Small-Chicken", actualSmallChicken.getName());
    }

    @Test
    public void testUpdate() {
        SmallChicken smallChicken = new SmallChicken();
        smallChicken.update(new Attack());
        assertEquals("Attack", smallChicken.getInfo());
    }
}

