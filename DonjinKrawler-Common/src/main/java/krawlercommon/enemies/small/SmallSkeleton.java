package krawlercommon.enemies.small;

import krawlercommon.enemies.Skeleton;
import krawlercommon.map.Decoration;
import krawlercommon.map.Wall;
import krawlercommon.strategies.EnemyStrategy;

import java.util.ArrayList;

public class SmallSkeleton extends Skeleton {

    ArrayList<Decoration> decorations = new ArrayList<>();
    ArrayList<Wall> walls = new ArrayList<>();

    public SmallSkeleton() {
        this.setName("Small-Skeleton");
        this.setDamage(20.0);
    }

    public SmallSkeleton(ArrayList<Decoration> decorations, ArrayList<Wall> walls) {
        this();
        this.decorations = decorations;
        this.walls = walls;
    }

    @Override
    public void move() {
        super.move(decorations, walls);
    }

    @Override
    public void update(EnemyStrategy strategy) {
        strategy.init(this);
        strategy.execute();
    }

    @Override
    public Object deepCopy() throws CloneNotSupportedException {
        return null;
    }
}
