package donjinkrawler.enemies;

import donjinkrawler.strategies.EnemyStrategy;
import donjinkrawler.strategies.MoveAwayFromPlayer;
import donjinkrawler.strategies.MoveRandomly;
import donjinkrawler.strategies.MoveTowardPlayer;

abstract public class Chicken extends Enemy {
    EnemyStrategy strategies[] = { new MoveAwayFromPlayer(), new MoveRandomly(), new MoveTowardPlayer()};

    public Chicken(){
        this.setInterval(3);
        this.setStrategies(strategies);
    }
}
