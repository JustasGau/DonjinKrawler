package krawlercommon.enemies.big;

import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.EnemyFactory;
import krawlercommon.enemies.EnemyType;
import krawlercommon.map.Decoration;
import krawlercommon.map.Wall;

import java.util.ArrayList;

public class BigEnemyFactory implements EnemyFactory {

    private ArrayList<Decoration> decorations;
    private ArrayList<Wall> walls;

    public BigEnemyFactory(ArrayList<Decoration> decorations, ArrayList<Wall> walls) {
        this.decorations = decorations;
        this.walls = walls;
    }

    public Enemy make(EnemyType type) {

        if (type == null) {
            return null;
        }

        if (type.equals(EnemyType.CHICKEN)) {
            return new BigChicken(decorations, walls);
        } else if (type.equals(EnemyType.SKELETON)) {
            return new BigSkeleton(decorations, walls);
        } else if (type.equals(EnemyType.ZOMBIE)) {
            return new BigZombie(decorations, walls);
        }

        return null;
    }
}
