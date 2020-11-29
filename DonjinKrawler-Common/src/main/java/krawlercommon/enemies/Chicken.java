package krawlercommon.enemies;

import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveAwayFromPlayer;
import krawlercommon.strategies.MoveRandomly;
import krawlercommon.strategies.MoveTowardPlayer;

import java.util.Map;

public abstract class Chicken extends Enemy {

    public Chicken() {
        this.initEnemy();
    }

    public void setInterval(){
        this.updateIntervalSeconds = 3;
    }

    public void setStrategies() {
        this.strategies = Map.of(
                Phases.AWAY, new MoveAwayFromPlayer(),
                Phases.RANDOM, new MoveRandomly(),
                Phases.TOWARDS, new MoveTowardPlayer()
        );
    }

    @Override
    public void debug() {
        System.out.println("Chicken has been created");
    }
}
