package donjinkrawler.enemies.small;

import donjinkrawler.enemies.EnemyFactory;
import donjinkrawler.enemies.EnemyType;
import donjinkrawler.enemies.Enemy;

public class SmallEnemyFactory implements EnemyFactory {

    public Enemy make(EnemyType type, int id) {

        if(type == null) {
            return null;
        }

        if(type.equals(EnemyType.CHICKEN)) {
            return new SmallChicken(id);
        } else if(type.equals(EnemyType.SKELETON)) {
            return new SmallSkeleton(id);
        } else if(type.equals(EnemyType.ZOMBIE)) {
            return new SmallZombie(id);
        }

        return null;
    }
}
