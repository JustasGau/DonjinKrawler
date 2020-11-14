package krawlercommon.packets;

import krawlercommon.enemies.Enemy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratedEnemyPacketTest {
    @Test
    public void testConstructor() {
        assertTrue((new EnemyPacket()).isUpdate());
    }

    @Test
    public void testConstructor2() {
        EnemyPacket actualEnemyPacket = new EnemyPacket(123);
        assertEquals(123, actualEnemyPacket.roomId);
        assertTrue(actualEnemyPacket.isUpdate());
    }

    @Test
    public void testSetEnemies() {
        EnemyPacket enemyPacket = new EnemyPacket();
        ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
        enemyPacket.setEnemies(enemyList);
        assertSame(enemyList, enemyPacket.getEnemies());
    }

    @Test
    public void testSetCreate() {
        EnemyPacket enemyPacket = new EnemyPacket();
        enemyPacket.setCreate();
        assertFalse(enemyPacket.isUpdate());
    }
}

