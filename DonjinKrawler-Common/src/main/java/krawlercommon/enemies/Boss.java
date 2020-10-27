package krawlercommon.enemies;

import krawlercommon.strategies.*;

import java.util.Map;

public class Boss extends Enemy {
    transient Map<Phases, EnemyStrategy> strategies = Map.of(
            Phases.TOWARDS, new MoveTowardPlayer(),
            Phases.ATTACK, new Attack(),
            Phases.RANGED, new RangeAttack()
    );

    public Boss() {
        this.setX(225);
        this.setY(225);
        this.setName("Boss");
        this.setDamage(15.0);
        this.setStrategies(strategies);
        this.setPhase(Phases.TOWARDS);
    }

    @Override
    public void update(EnemyStrategy strategy) {
        strategy.init(this);
    }
}
