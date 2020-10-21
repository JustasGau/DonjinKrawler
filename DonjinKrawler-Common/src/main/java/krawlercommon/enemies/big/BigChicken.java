package krawlercommon.enemies.big;

import krawlercommon.enemies.Chicken;
import krawlercommon.strategies.EnemyStrategy;

public class BigChicken extends Chicken {

    public BigChicken() {
        this.setName("Big-Chicken");
        this.setDamage(15.0);
    }

    @Override
    public void update(EnemyStrategy strategy) {
        strategy.init(this);
    }
}
