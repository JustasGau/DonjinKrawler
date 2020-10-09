package krawlercommon.enemies;

import krawlercommon.strategies.*;

public abstract class Skeleton extends Enemy {
    transient EnemyStrategy[] strategies = {
        new MoveAwayFromPlayer(),
        new MoveRandomly(),
        new MoveTowardPlayer(),
        new Attack(),
        new RangeAttack()
    };

    public Skeleton() {
        this.setInterval(1);
        this.setStrategies(strategies);
    }
}
