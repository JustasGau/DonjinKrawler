package krawlercommon.strategies;

import krawlercommon.enemies.Boss;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GeneratedMoveRandomlyTest {
    @Test
    public void testConstructor() {
        assertEquals(0, (new MoveRandomly()).tick);
    }

    @Test
    public void testInit() {
        MoveRandomly moveRandomly = new MoveRandomly();
        Boss boss = new Boss();
        moveRandomly.init(boss);
        assertEquals("MoveRandomly", boss.getInfo());
        assertSame(boss, moveRandomly.enemy);
    }

    @Test
    public void testExecute() {
        MoveRandomly moveRandomly = new MoveRandomly();
        moveRandomly.init(new Boss());
        moveRandomly.execute();
        assertEquals(1, moveRandomly.tick);
    }
}

