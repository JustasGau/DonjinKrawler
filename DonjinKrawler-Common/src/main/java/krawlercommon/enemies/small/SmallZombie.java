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
        setCurrentStrategy(strategy);
    }
}
