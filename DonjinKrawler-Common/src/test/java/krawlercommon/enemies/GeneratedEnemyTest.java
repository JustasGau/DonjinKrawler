package krawlercommon.enemies;

import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveTowardPlayer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedEnemyTest {
    @Test
    public void testSetName() {
        Boss boss = new Boss();
        boss.setName("Name");
        assertEquals("Name", boss.getName());
    }

    @Test
    public void testSetInterval() {
        Boss boss = new Boss();
        boss.setInterval(42);
        assertEquals(42, boss.updateIntervalSeconds);
    }

    @Test
    public void testSetStrategies() {
        Boss boss = new Boss();
        boss.setStrategies(new HashMap<Enemy.Phases, EnemyStrategy>());
        assertEquals(3, boss.strategies.size());
    }

    @Test
    public void testSetDamage() {
        Boss boss = new Boss();
        boss.setDamage(10.0);
        assertEquals(10.0, boss.getDamage());
    }

    @Test
    public void testSetX() {
        Boss boss = new Boss();
        boss.setX(42);
        assertEquals(42, boss.getX());
    }

    @Test
    public void testSetY() {
        Boss boss = new Boss();
        boss.setY(42);
        assertEquals(42, boss.getY());
    }

    @Test
    public void testSetInfo() {
        Boss boss = new Boss();
        boss.setInfo("Info");
        assertEquals("Info", boss.getInfo());
    }

    @Test
    public void testDamage() {
        Boss boss = new Boss();
        boss.damage(10.0);
        assertEquals(90.0, boss.getHealth());
    }

    @Test
    public void testSetCurrentStrategy() {
        Boss boss = new Boss();
        MoveTowardPlayer strategy = new MoveTowardPlayer();
        boss.setCurrentStrategy(strategy);
        assertEquals(strategy.getStrategy(), boss.currentStrategy.getStrategy());
    }

    @Test
    public void testMove() {
        Boss boss = new Boss();
        boss.setX(5);
        boss.setDx(2);
        boss.move();
        assertEquals(7, boss.getX());
        boss.setY(5);
        boss.setDy(2);
        boss.move();
        assertEquals(7, boss.getY());
    }
}

