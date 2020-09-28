package donjinkrawler.enemy_generation;

import java.util.Random;

public final class EnemyGenerator {

    /**
     * List of all available types of enemies
     */
    private static final String[] TYPES = {
            "CHICKEN", "SKELETON", "ZOMBIE",
    };

    /**
     * Enemy factory
     */
    private final EnemyFactory enemyFactory;

    /**
     * EnemyGenerator class Constructor
     */
    public EnemyGenerator() {
        this.enemyFactory = new EnemyFactory();
    }

    /**
     * Generate new random enemy
     *
     * @return new randomly generated enemy
     */
    public Enemy generateRandomEnemy() {
        int rnd = new Random().nextInt(TYPES.length);
        return this.enemyFactory.make(TYPES[rnd]);
    }
}
