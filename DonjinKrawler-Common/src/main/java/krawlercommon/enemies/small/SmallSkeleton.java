package krawlercommon.enemies.small;

import krawlercommon.enemies.Skeleton;
import krawlercommon.strategies.EnemyStrategy;

public class SmallSkeleton extends Skeleton {

    public SmallSkeleton() {
        this.setName("Small-Skeleton");
        this.setDamage(20.0);
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
