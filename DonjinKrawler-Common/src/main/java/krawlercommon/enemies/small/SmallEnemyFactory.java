package krawlercommon.enemies.small;

import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.EnemyFactory;
import krawlercommon.enemies.EnemyType;
import krawlercommon.map.Decoration;
import krawlercommon.map.Wall;

import java.util.ArrayList;

public class SmallEnemyFactory implements EnemyFactory {

    private ArrayList<Decoration> decorations;
    private ArrayList<Wall> walls;

    public SmallEnemyFactory(ArrayList<Decoration> decorations, ArrayList<Wall> walls) {
        this.decorations = decorations;
        this.walls = walls;
    }

    public Enemy make(EnemyType type) {

        if (type == null) {
            return null;
        }

        if (type.equals(EnemyType.CHICKEN)) {
            return new SmallChicken(decorations, walls);
        } else if (type.equals(EnemyType.SKELETON)) {
            return new SmallSkeleton(decorations, walls);
        } else if (type.equals(EnemyType.ZOMBIE)) {
            return new SmallZombie(decorations, walls);
        }

        return null;
    }
}
