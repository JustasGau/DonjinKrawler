package krawlercommon.enemies;

import krawlercommon.strategies.Attack;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedBossTest {
    @Test
    public void testConstructor() {
        Boss actualBoss = new Boss();
        assertEquals(0, actualBoss.tick);
        assertEquals(3, actualBoss.strategies.size());
        assertEquals(2, actualBoss.updateIntervalSeconds);
        assertEquals(15.0, actualBoss.getDamage());
        assertEquals(225, actualBoss.getY());
        assertEquals(225, actualBoss.getX());
        assertEquals(100.0, actualBoss.getHealth());
        assertEquals("Boss", actualBoss.getName());
    }

    @Test
    public void testUpdate() {
        Boss boss = new Boss();
        boss.update(new Attack());
        assertEquals("Attack", boss.getInfo());
    }
}

