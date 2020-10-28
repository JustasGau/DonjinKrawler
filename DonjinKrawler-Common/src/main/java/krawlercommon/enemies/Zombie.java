package krawlercommon.enemies;

import krawlercommon.strategies.*;

import java.util.Map;

public abstract class Zombie extends Enemy {
    transient Map<Phases, EnemyStrategy> strategies = Map.of(
            Phases.AWAY, new MoveAwayFromPlayer(),
            Phases.RANDOM, new MoveRandomly(),
            Phases.TOWARDS, new MoveTowardPlayer(),
            Phases.ATTACK, new Attack()
    );

    public Zombie() {
        this.setInterval(2);
        this.setStrategies(strategies);
    }
}
