package donjinkrawler.enemies;

import donjinkrawler.strategies.*;

abstract public class Skeleton extends Enemy {
    EnemyStrategy strategies[] = {
            new MoveAwayFromPlayer(),
            new MoveRandomly(),
            new MoveTowardPlayer(),
            new Attack(),
            new RangeAttack()
    };
    public Skeleton(){
        this.setInterval(1);
        this.setStrategies(strategies);
    }
}
