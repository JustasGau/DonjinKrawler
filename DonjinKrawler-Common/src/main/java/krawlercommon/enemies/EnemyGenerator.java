package krawlercommon.enemies;

import java.util.ArrayList;
import java.util.Random;

public final class EnemyGenerator {

    private final EnemyFactory enemyFactory;

    public EnemyGenerator(EnemyFactory enemyFactory) {
        this.enemyFactory = enemyFactory;
    }

    public Enemy generateRandomEnemy() {
        EnemyType[] types = EnemyType.values();
        int rnd = new Random().nextInt(types.length);
        return this.enemyFactory.make(types[rnd]);
    }

    public ArrayList<Enemy> generateRandomEnemies(int number) {

        number = (number == 0) ? 5 : number;
        ArrayList<Enemy> enemies = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            enemies.add(this.generateRandomEnemy());
        }

        return enemies;
    }
}