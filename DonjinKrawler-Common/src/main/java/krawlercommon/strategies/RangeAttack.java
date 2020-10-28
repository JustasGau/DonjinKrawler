package krawlercommon.strategies;

import krawlercommon.enemies.Enemy;

public class RangeAttack implements EnemyStrategy {

    public RangeAttack() {
    }

    Enemy enemy;

    @Override
    public String getStrategy() {
        return "Range Attack";
    }

    @Override
    public void init(Enemy enemy) {
        this.enemy = enemy;
        enemy.setInfo(getStrategy());
    }

    @Override
    public void execute() {
    }
}
