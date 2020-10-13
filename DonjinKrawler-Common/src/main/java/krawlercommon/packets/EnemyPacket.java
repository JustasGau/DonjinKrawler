package krawlercommon.packets;

import krawlercommon.enemies.Enemy;

import java.util.ArrayList;
import java.util.List;

public class EnemyPacket {

    private List<Enemy> enemies;

    public EnemyPacket() {
        enemies = new ArrayList<>();
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
}
