package donjinkrawler.strategies;

import donjinkrawler.enemies.Enemy;

public interface EnemyStrategy {
    String getStrategy();
    void init(Enemy enemy);
    void execute();
}
