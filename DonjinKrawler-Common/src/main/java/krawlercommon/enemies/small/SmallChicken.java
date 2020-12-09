package krawlercommon.enemies.small;

import krawlercommon.enemies.Chicken;
import krawlercommon.map.Decoration;
import krawlercommon.map.Wall;
import krawlercommon.strategies.EnemyStrategy;

import java.util.ArrayList;

public class SmallChicken extends Chicken {

    ArrayList<Decoration> decorations = new ArrayList<>();
    ArrayList<Wall> walls = new ArrayList<>();

    public SmallChicken() {
        this.setName("Small-Chicken");
        this.setDamage(5.0);
    }

    public SmallChicken(ArrayList<Decoration> decorations, ArrayList<Wall> walls) {
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
    }

    @Override
    public Object deepCopy() throws CloneNotSupportedException {
        return null;
    }
}
