package donjinkrawler.strategies;

import donjinkrawler.enemies.Enemy;

public class MoveTowardPlayer implements EnemyStrategy {
    Enemy enemy;

    @Override
    public String getStrategy() {
        return "MoveTowards";
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
