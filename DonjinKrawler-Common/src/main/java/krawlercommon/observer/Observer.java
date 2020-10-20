package krawlercommon.observer;

import krawlercommon.strategies.EnemyStrategy;

public interface Observer {
    void update(EnemyStrategy strategy);
}
