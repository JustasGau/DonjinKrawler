package krawlercommon.enemies.big;

import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.EnemyFactory;
import krawlercommon.enemies.EnemyType;

public class BigEnemyFactory implements EnemyFactory {

    public Enemy make(EnemyType type) {

        if (type == null) {
            return null;
        }

        if (type.equals(EnemyType.CHICKEN)) {
            return new BigChicken();
        } else if (type.equals(EnemyType.SKELETON)) {
            return new BigSkeleton();
        } else if (type.equals(EnemyType.ZOMBIE)) {
            return new BigZombie();
        }

        return null;
    }
}
