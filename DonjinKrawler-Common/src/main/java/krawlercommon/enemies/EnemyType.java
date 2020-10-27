package krawlercommon.enemies;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnemyType {
    CHICKEN,
    SKELETON,
    ZOMBIE,
    BOSS;

    public static List<EnemyType> getSimpleEnemyTypes() {
        return Arrays.stream(EnemyType.values()).filter(t -> !t.equals(BOSS)).collect(Collectors.toList());
    }
}
