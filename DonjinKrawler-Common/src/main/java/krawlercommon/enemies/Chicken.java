package krawlercommon.enemies;

import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveAwayFromPlayer;
import krawlercommon.strategies.MoveRandomly;
import krawlercommon.strategies.MoveTowardPlayer;

public abstract class Chicken extends Enemy {
    transient EnemyStrategy[] strategies = {new MoveAwayFromPlayer(), new MoveRandomly(), new MoveTowardPlayer()};

    public Chicken() {
        this.setInterval(3);
        this.setStrategies(strategies);
    }
}
