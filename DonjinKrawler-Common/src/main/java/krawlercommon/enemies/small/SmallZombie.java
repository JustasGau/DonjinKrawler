package krawlercommon.enemies.small;

import krawlercommon.enemies.Zombie;
import krawlercommon.strategies.*;

import java.util.Map;

final public class SmallZombie extends Zombie {

    public SmallZombie() {
        this.setName("Small-Zombie");
        this.setDamage(15.0);
    }

    @Override
    public void update(EnemyStrategy strategy) {
        strategy.init(this);
        strategy.execute();
    }

    @Override
    public Object deepCopy() throws CloneNotSupportedException {
        return null;
    }

    public void setInterval() {
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

    @Override
    public void debug() {
        // System.out.println("Small zombie:" + this.getID() + " has been created");
    }
}
