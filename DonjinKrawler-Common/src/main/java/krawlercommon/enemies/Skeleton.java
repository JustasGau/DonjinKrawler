package krawlercommon.enemies;

import krawlercommon.strategies.*;

import java.util.Map;

public abstract class Skeleton extends Enemy {
    transient Map<Phases, EnemyStrategy> strategies = Map.of(
            Phases.AWAY, new MoveAwayFromPlayer(),
            Phases.RANDOM, new MoveRandomly(),
            Phases.TOWARDS, new MoveTowardPlayer(),
            Phases.ATTACK, new Attack(),
            Phases.RANGED, new RangeAttack()
    );

    public Skeleton() {
        this.setInterval(1);
        this.setStrategies(strategies);
    }
}
