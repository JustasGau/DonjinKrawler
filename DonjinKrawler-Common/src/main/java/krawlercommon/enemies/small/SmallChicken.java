package krawlercommon.enemies.small;

import krawlercommon.enemies.Chicken;
import krawlercommon.strategies.EnemyStrategy;

public class SmallChicken extends Chicken {

    public SmallChicken() {
        this.setName("Small-Chicken");
        this.setDamage(5.0);
    }

    @Override
    public void update(EnemyStrategy strategy) {
        strategy.init(this);
    }

    @Override
    public Object deepCopy() throws CloneNotSupportedException {
        return null;
    }
}
