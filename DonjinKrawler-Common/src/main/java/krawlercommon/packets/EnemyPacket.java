package krawlercommon.packets;

import krawlercommon.enemies.Enemy;

import java.util.ArrayList;
import java.util.List;

public class EnemyPacket {
    public int roomId;
    public Boolean update = true;

    private List<Enemy> enemies;

    public EnemyPacket() {
        enemies = new ArrayList<>();
    }

    public EnemyPacket(int roomId) {
        this.roomId = roomId;
        this.enemies = new ArrayList<>();
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

    public void setCreate() {
        update = false;
    }

    public Boolean isUpdate() {
        return update;
    }
}
