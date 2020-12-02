package krawlercommon.strategies;

import krawlercommon.enemies.Enemy;

public class Attack implements EnemyStrategy {

    Enemy enemy;

    public Attack() {

    }

    @Override
    public String getStrategy() {
        return "Attack";
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
