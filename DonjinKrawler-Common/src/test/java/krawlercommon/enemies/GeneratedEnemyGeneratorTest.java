package krawlercommon.enemies;

import krawlercommon.enemies.big.BigEnemyFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratedEnemyGeneratorTest {
    @Test
    public void testGenerateRandomEnemies() {
        assertEquals(10, (new EnemyGenerator(new BigEnemyFactory())).generateRandomEnemies(10).size());
        assertEquals(5, (new EnemyGenerator(new BigEnemyFactory())).generateRandomEnemies(0).size());
    }
}

