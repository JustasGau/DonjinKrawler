package krawlercommon.strategies;

import krawlercommon.enemies.Enemy;

public class MoveRandomly implements EnemyStrategy {

    public MoveRandomly() {
    }

    Enemy enemy;

    @Override
    public String getStrategy() {
        return "MoveRandomly";
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
