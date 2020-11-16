package krawlercommon.strategies;

import krawlercommon.enemies.Boss;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@Tag("generated")
public class GeneratedRangeAttackTest {
    @Test
    public void testInit() {
        RangeAttack rangeAttack = new RangeAttack();
        Boss boss = new Boss();
        rangeAttack.init(boss);
        assertEquals("Range Attack", boss.getInfo());
        assertSame(boss, rangeAttack.enemy);
    }
}

