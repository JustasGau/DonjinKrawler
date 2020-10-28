package krawlercommon.strategies;

import krawlercommon.enemies.Enemy;

import java.util.Random;

public class MoveRandomly implements EnemyStrategy {

    Random random = new Random();
    Enemy enemy;
    int tick = 0;
    public MoveRandomly() {
    }


    @Override
    public String getStrategy() {
        return "MoveRandomly";
    }

    @Override
    public void init(Enemy enemy) {
        this.enemy = enemy;
        enemy.setInfo(getStrategy());
    }

    @Override
    public void execute() {
        if (tick % 100 == 0) {
            enemy.setDx(random.nextInt(3 + 2) - 2);
            enemy.setDy(random.nextInt(3 + 2) - 2);
        } else if (tick % 2 == 0) {
            enemy.move();
        } else if (tick % 1000 == 0) {
            tick = 0;
        }
        tick++;
    }
}
