package donjinkrawler.strategies;

import donjinkrawler.enemies.Enemy;

public class MoveAwayFromPlayer implements EnemyStrategy {

    Enemy enemy;

    @Override
    public String getStrategy() {
        return "MoveAway";
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
