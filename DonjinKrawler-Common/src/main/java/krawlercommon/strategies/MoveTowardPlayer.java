package krawlercommon.strategies;

import krawlercommon.ConnectionManager;
import krawlercommon.PlayerData;
import krawlercommon.enemies.Enemy;

public class MoveTowardPlayer implements EnemyStrategy {

    public MoveTowardPlayer() {
    }

    Enemy enemy;
    PlayerData target;
    int tick = 0;
    int speed = 2;

    @Override
    public String getStrategy() {
        return "MoveTowards";
    }

    @Override
    public void init(Enemy enemy) {
        this.enemy = enemy;
        target = calculateClosest();
        enemy.setInfo(getStrategy());
    }

    @Override
    public void execute() {
        if (tick % 10 == 0) {
            if (target != null) {
                if (target.getX() > enemy.getX())
                    enemy.setDx(speed);
                else if (target.getX() < enemy.getX())
                    enemy.setDx((-1) * speed);
                else
                    enemy.setDx(0);

                if (target.getY() > enemy.getY())
                    enemy.setDy(speed);
                else if (target.getY() < enemy.getY())
                    enemy.setDy((-1) * speed);
                else
                    enemy.setDy(0);
            }
        } else if (tick % 2 == 0) {
            enemy.move();
        } else if (tick % 1000 == 0) {
            tick = 0;
        }
        tick++;
    }

    private PlayerData calculateClosest() {
        PlayerData closest = null;
        double distance = Double.MAX_VALUE;
        for (PlayerData player : ConnectionManager.getConnectionMap().values()) {
            int x = enemy.getX() - player.getX();
            int y = enemy.getY() - player.getY();
            double tmpDist = Math.sqrt(x*x + y*y);
            if (tmpDist < distance) {
                distance = tmpDist;
                closest = player;
            }
        }
        return closest;
    }
}
