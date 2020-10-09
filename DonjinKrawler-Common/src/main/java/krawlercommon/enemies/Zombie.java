package krawlercommon.enemies;

import krawlercommon.strategies.*;

public abstract class Zombie extends Enemy {
    transient EnemyStrategy[] strategies = {
        new MoveAwayFromPlayer(),
        new MoveRandomly(),
        new MoveTowardPlayer(),
        new Attack()
    };

    public Zombie() {
        this.setInterval(2);
        this.setStrategies(strategies);
    }
}
