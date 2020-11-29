package krawlercommon.enemies;

import krawlercommon.strategies.*;

import java.util.Map;

public class Boss extends Enemy {

    public Boss() {
        this.setX(225);
        this.setY(225);
        this.setName("Boss");
        this.setDamage(15.0);
        this.initEnemy();
        this.setPhase(Phases.TOWARDS);
    }

    @Override
    public void update(EnemyStrategy strategy) {
        strategy.init(this);
    }

    @Override
    public Object deepCopy() throws CloneNotSupportedException {
        return null;
    }

    public void setInterval(){
        this.updateIntervalSeconds = 2;
    }

    public void setStrategies() {
        this.strategies = Map.of(
                Phases.TOWARDS, new MoveTowardPlayer(),
                Phases.ATTACK, new Attack(),
                Phases.RANGED, new RangeAttack()
        );
    }
}
