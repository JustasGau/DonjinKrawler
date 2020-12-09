package krawlercommon.enemies.big;

import krawlercommon.enemies.Chicken;
import krawlercommon.map.Decoration;
import krawlercommon.map.Wall;
import krawlercommon.strategies.EnemyStrategy;

import java.util.ArrayList;

public final class BigChicken extends Chicken {

    ArrayList<Decoration> decorations = new ArrayList<>();
    ArrayList<Wall> walls = new ArrayList<>();

    public BigChicken() {
        this.setName("Big-Chicken");
        this.setDamage(15.0);
    }

    public BigChicken(ArrayList<Decoration> decorations, ArrayList<Wall> walls) {
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
