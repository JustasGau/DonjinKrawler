package donjinkrawler.strategies;

import donjinkrawler.enemies.Enemy;

public class MoveRandomly implements EnemyStrategy {

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
