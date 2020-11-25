package krawlercommon.enemies.big;

import krawlercommon.enemies.Enemy;
import krawlercommon.strategies.EnemyStrategy;

public class BigSkeleton extends Enemy {

    public BigSkeleton() {
        this.setName("Big-Skeleton");
        this.setDamage(40.0);
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
}
