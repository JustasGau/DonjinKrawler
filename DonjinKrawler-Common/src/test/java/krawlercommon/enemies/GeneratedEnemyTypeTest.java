package krawlercommon.enemies;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedEnemyTypeTest {
    @Test
    public void testGetSimpleEnemyTypes() {
        List<EnemyType> actualSimpleEnemyTypes = EnemyType.getSimpleEnemyTypes();
        assertEquals(3, actualSimpleEnemyTypes.size());
        assertEquals(EnemyType.CHICKEN, actualSimpleEnemyTypes.get(0));
        assertEquals(EnemyType.SKELETON, actualSimpleEnemyTypes.get(1));
        assertEquals(EnemyType.ZOMBIE, actualSimpleEnemyTypes.get(2));
    }
}

