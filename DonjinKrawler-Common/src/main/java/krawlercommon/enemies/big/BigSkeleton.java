package krawlercommon.enemies.big;

import krawlercommon.enemies.Skeleton;
import krawlercommon.map.Decoration;
import krawlercommon.map.Wall;
import krawlercommon.strategies.EnemyStrategy;

import java.util.ArrayList;

public final class BigSkeleton extends Skeleton {

    ArrayList<Decoration> decorations = new ArrayList<>();
    ArrayList<Wall> walls = new ArrayList<>();

    public BigSkeleton() {
        this.setName("Big-Skeleton");
        this.setDamage(40.0);
    }

    public BigSkeleton(ArrayList<Decoration> decorations, ArrayList<Wall> walls) {
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
