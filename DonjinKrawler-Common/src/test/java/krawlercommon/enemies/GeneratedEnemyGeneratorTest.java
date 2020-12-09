package krawlercommon.enemies;

import krawlercommon.enemies.big.BigEnemyFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedEnemyGeneratorTest {
    @Test
    public void testGenerateRandomEnemies() {
        assertEquals(10, (new EnemyGenerator(new BigEnemyFactory(new ArrayList<>(), new ArrayList<>())))
                .generateRandomEnemies(10).size());
        assertEquals(5, (new EnemyGenerator(new BigEnemyFactory(new ArrayList<>(), new ArrayList<>())))
                .generateRandomEnemies(0).size());
    }
}

