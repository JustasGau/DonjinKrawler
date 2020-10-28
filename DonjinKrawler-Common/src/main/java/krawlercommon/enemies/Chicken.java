package krawlercommon.enemies;

import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveAwayFromPlayer;
import krawlercommon.strategies.MoveRandomly;
import krawlercommon.strategies.MoveTowardPlayer;

import java.util.Map;

public abstract class Chicken extends Enemy {
    transient Map<Phases, EnemyStrategy> strategies = Map.of(
            Phases.AWAY, new MoveAwayFromPlayer(),
            Phases.RANDOM, new MoveRandomly(),
            Phases.TOWARDS, new MoveTowardPlayer()
    );

    public Chicken() {
        this.setInterval(3);
        this.setStrategies(strategies);
    }
}
