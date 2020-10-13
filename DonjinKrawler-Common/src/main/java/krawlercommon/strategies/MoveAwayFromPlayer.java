package krawlercommon.strategies;

import krawlercommon.enemies.Enemy;

public class MoveAwayFromPlayer implements EnemyStrategy {

    public MoveAwayFromPlayer() {
    }

    Enemy enemy;

    @Override
    public String getStrategy() {
        return "MoveAway";
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
