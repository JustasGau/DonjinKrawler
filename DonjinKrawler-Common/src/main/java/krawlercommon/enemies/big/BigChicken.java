package krawlercommon.enemies.big;

import krawlercommon.enemies.Enemy;
import krawlercommon.strategies.EnemyStrategy;

public class BigChicken extends Enemy {

    public BigChicken() {
        this.setName("Big-Chicken");
        this.setDamage(15.0);
    }

    @Override
    public void update(EnemyStrategy strategy) {
        setCurrentStrategy(strategy);
    }
}
