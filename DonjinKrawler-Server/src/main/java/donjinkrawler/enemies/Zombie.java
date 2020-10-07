package donjinkrawler.enemies;

import donjinkrawler.strategies.*;

abstract public class Zombie extends Enemy {
    EnemyStrategy strategies[] = {
            new MoveAwayFromPlayer(),
            new MoveRandomly(),
            new MoveTowardPlayer(),
            new Attack()
    };

    public Zombie(){
        this.setInterval(2);
        this.setStrategies(strategies);
    }
}
