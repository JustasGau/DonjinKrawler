package krawlercommon.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class EnemyGenerator {

    private final EnemyFactory enemyFactory;

    public EnemyGenerator(EnemyFactory enemyFactory) {
        this.enemyFactory = enemyFactory;
    }

    public Enemy generateRandomEnemy() {
        List<EnemyType> types = EnemyType.getSimpleEnemyTypes();
        int rnd = new Random().nextInt(types.size());
        return this.enemyFactory.make(types.get(rnd));
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
