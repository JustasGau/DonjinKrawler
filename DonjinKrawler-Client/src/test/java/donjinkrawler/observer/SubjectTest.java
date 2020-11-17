package donjinkrawler.observer;

import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.small.SmallChicken;
import krawlercommon.strategies.MoveTowardPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubjectTest {
    @Test
    public void updateTest() {
        Enemy enemy = new SmallChicken();
        var initialStrategy = enemy.getInfo();
        enemy.update(new MoveTowardPlayer());
        Assertions.assertNotEquals(initialStrategy, enemy.getInfo());
    }
}
