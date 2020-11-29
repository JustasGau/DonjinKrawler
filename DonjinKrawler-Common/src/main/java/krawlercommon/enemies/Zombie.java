package krawlercommon.enemies;

import krawlercommon.strategies.*;

import java.util.Map;

public abstract class Zombie extends Enemy {

    public Zombie() {
        this.initEnemy();
    }
    public void setInterval(){
        this.updateIntervalSeconds = 2;
    }

    public void setStrategies() {
        this.strategies = Map.of(
                Phases.AWAY, new MoveAwayFromPlayer(),
                Phases.RANDOM, new MoveRandomly(),
                Phases.TOWARDS, new MoveTowardPlayer(),
                Phases.ATTACK, new Attack()
        );
    }

}
