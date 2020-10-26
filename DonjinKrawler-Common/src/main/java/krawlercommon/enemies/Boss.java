package krawlercommon.enemies;

import krawlercommon.strategies.*;

public class Boss extends Enemy {
    transient EnemyStrategy[] strategies = {new MoveTowardPlayer(), new RangeAttack(), new Attack()};

    public Boss() {
        this.setX(225);
        this.setY(225);
        this.setName("Boss");
        this.setDamage(15.0);
        this.setStrategies(strategies);
    }

    @Override
    public void update(EnemyStrategy strategy) {
        strategy.init(this);
    }
}
