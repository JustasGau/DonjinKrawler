package donjinkrawler.enemy_generation;

public class EnemyFactory {

    public Enemy make(String type) {

        if(type == null) {
            return null;
        }

        if(type.equalsIgnoreCase("CHICKEN")) {
            return new Chicken();
        } else if(type.equalsIgnoreCase("SKELETON")) {
            return new Skeleton();
        } else if(type.equalsIgnoreCase("ZOMBIE")) {
            return new Zombie();
        }

        return null;
    }
}
