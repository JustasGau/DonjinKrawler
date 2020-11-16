package krawlercommon.strategies;

import krawlercommon.enemies.Boss;
import krawlercommon.enemies.big.BigChicken;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("generated")
public class GeneratedMoveAwayFromPlayerTest {
    @Test
    public void testInit() {
        MoveAwayFromPlayer moveAwayFromPlayer = new MoveAwayFromPlayer();
        Boss boss = new Boss();
        moveAwayFromPlayer.init(boss);
        assertEquals("MoveAway", boss.getInfo());
        assertNull(moveAwayFromPlayer.target);
        assertSame(boss, moveAwayFromPlayer.enemy);
    }

    @Test
    public void testInit2() {
        MoveAwayFromPlayer moveAwayFromPlayer = new MoveAwayFromPlayer();
        BigChicken bigChicken = new BigChicken();
        moveAwayFromPlayer.init(bigChicken);
        assertEquals("MoveAway", bigChicken.getInfo());
        assertNull(moveAwayFromPlayer.target);
        assertSame(bigChicken, moveAwayFromPlayer.enemy);
    }

    @Test
    public void testExecute() {
        MoveAwayFromPlayer moveAwayFromPlayer = new MoveAwayFromPlayer();
        moveAwayFromPlayer.execute();
        assertEquals(1, moveAwayFromPlayer.tick);
    }
}

