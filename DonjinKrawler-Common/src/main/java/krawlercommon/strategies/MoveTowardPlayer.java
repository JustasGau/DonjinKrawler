package krawlercommon.strategies;

import krawlercommon.enemies.Enemy;

public class MoveTowardPlayer implements EnemyStrategy {

    public MoveTowardPlayer() {
    }

    Enemy enemy;

    @Override
    public String getStrategy() {
        return "MoveTowards";
    }

    @Override
    public void init(Enemy enemy) {
        this.enemy = enemy;
        enemy.setInfo(getStrategy());
    }

    @Override
    public void execute() {
        enemy.setInfo(getStrategy());
    }
}
