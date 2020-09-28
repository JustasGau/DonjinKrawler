package donjinkrawler.enemies;

public class EnemyFactory {

    public Enemy make(EnemyType type) {

        if(type == null) {
            return null;
        }

        if(type.equals(EnemyType.CHICKEN)) {
            return new Chicken();
        } else if(type.equals(EnemyType.SKELETON)) {
            return new Skeleton();
        } else if(type.equals(EnemyType.ZOMBIE)) {
            return new Zombie();
        }

        return null;
    }
}
