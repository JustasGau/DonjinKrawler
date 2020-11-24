package krawlercommon.enemies.small;

import krawlercommon.enemies.Zombie;
import krawlercommon.strategies.EnemyStrategy;

public class SmallZombie extends Zombie {

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
}
