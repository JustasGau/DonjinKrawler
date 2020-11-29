package krawlercommon.enemies.big;

import krawlercommon.enemies.Chicken;
import krawlercommon.strategies.EnemyStrategy;

import java.util.Map;

public class BigChicken extends Chicken {

    public BigChicken() {
        this.setName("Big-Chicken");
        this.setDamage(15.0);
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
