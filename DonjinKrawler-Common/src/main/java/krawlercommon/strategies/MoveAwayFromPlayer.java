package krawlercommon.strategies;

import krawlercommon.ConnectionManager;
import krawlercommon.PlayerData;
import krawlercommon.enemies.Enemy;

public class MoveAwayFromPlayer implements EnemyStrategy {

    Enemy enemy;
    PlayerData target;
    int tick = 0;
    int speed = 2;

    public MoveAwayFromPlayer() {
    }

    @Override
    public String getStrategy() {
        return "MoveAway";
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
                if (target.getX() < enemy.getX()) {
                    enemy.setDx(speed);
                } else if (target.getX() > enemy.getX()) {
                    enemy.setDx((-1) * speed);
                }
                if (target.getY() < enemy.getY()) {
                    enemy.setDy(speed);
                } else if (target.getY() > enemy.getY()) {
                    enemy.setDy((-1) * speed);
                }
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
        if (ConnectionManager.getConnectionMap() != null) {
            for (PlayerData player : ConnectionManager.getConnectionMap().values()) {
                int x = enemy.getX() - player.getX();
                int y = enemy.getY() - player.getY();
                double tmpDist = Math.sqrt(x * x + y * y);
                if (tmpDist < distance) {
                    distance = tmpDist;
                    closest = player;
                }
            }
        }
        return closest;
    }
}
