package krawlercommon.strategies;

import krawlercommon.enemies.Enemy;

public interface EnemyStrategy {
    String getStrategy();

    void init(Enemy enemy);

    void execute();
}
