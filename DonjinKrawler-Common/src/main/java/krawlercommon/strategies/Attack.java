package krawlercommon.strategies;

import krawlercommon.enemies.Enemy;

public class Attack implements EnemyStrategy {

    public Attack() {

    }

    Enemy enemy;

    @Override
    public String getStrategy() {
        return "Attack";
    }

    @Override
    public void init(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void execute() {
        enemy.setInfo(getStrategy());
    }
}
