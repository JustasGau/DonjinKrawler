package krawlercommon.enemies.big;

import krawlercommon.enemies.Enemy;
import krawlercommon.strategies.EnemyStrategy;

public class BigZombie extends Enemy {

    public BigZombie() {
        this.setName("Big-Zombie");
        this.setDamage(30.0);
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
