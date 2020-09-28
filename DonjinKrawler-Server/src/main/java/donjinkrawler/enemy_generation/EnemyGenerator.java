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

    /**
     * Generate new random enemies
     *
     * @return new randomly generated enemies list
     */
    public Enemy[] generateRandomEnemies(int number) {

        number = (number == 0) ? 5 : number;
        Enemy[] enemies = new Enemy[number];

        for (int i = 0; i < number; i++) {
            enemies[i] = this.generateRandomEnemy();
        }

        return enemies;
    }
}
