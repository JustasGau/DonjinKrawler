package krawlercommon.strategies;

import krawlercommon.enemies.Boss;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratedMoveTowardPlayerTest {
    @Test
    public void testInit() {
        MoveTowardPlayer moveTowardPlayer = new MoveTowardPlayer();
        Boss boss = new Boss();
        moveTowardPlayer.init(boss);
        assertEquals("MoveTowards", boss.getInfo());
        assertNull(moveTowardPlayer.target);
        assertSame(boss, moveTowardPlayer.enemy);
    }

    @Test
    public void testExecute() {
        MoveTowardPlayer moveTowardPlayer = new MoveTowardPlayer();
        moveTowardPlayer.execute();
        assertEquals(1, moveTowardPlayer.tick);
    }
}

