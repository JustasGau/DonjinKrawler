package krawlercommon.strategies;

import krawlercommon.enemies.Boss;
import krawlercommon.enemies.Enemy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GeneratedAttackTest {
    @Test
    public void testInit() {
        Attack attack = new Attack();
        Boss boss = new Boss();
        attack.init(boss);
        assertEquals("Attack", boss.getInfo());
        assertSame(boss, attack.enemy);
    }

    @Test
    public void testInit2() {
        Boss boss = new Boss();
        boss.setPhase(Enemy.Phases.RANDOM);
        Attack attack = new Attack();
        attack.init(boss);
        assertEquals("Attack", boss.getInfo());
        assertSame(boss, attack.enemy);
    }
}

