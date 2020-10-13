package krawlercommon.enemies.small;

import krawlercommon.enemies.EnemyFactory;
import krawlercommon.enemies.EnemyType;
import krawlercommon.enemies.Enemy;

public class SmallEnemyFactory implements EnemyFactory {

    public Enemy make(EnemyType type) {

        if (type == null) {
            return null;
        }

        if (type.equals(EnemyType.CHICKEN)) {
            return new SmallChicken();
        } else if (type.equals(EnemyType.SKELETON)) {
            return new SmallSkeleton();
        } else if (type.equals(EnemyType.ZOMBIE)) {
            return new SmallZombie();
        }

        return null;
    }
}
