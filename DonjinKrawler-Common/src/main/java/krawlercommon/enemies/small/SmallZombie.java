package krawlercommon.enemies.small;

import krawlercommon.enemies.Zombie;
import krawlercommon.map.Decoration;
import krawlercommon.map.Wall;
import krawlercommon.strategies.*;

import java.util.ArrayList;
import java.util.Map;

public final class SmallZombie extends Zombie {

    ArrayList<Decoration> decorations = new ArrayList<>();
    ArrayList<Wall> walls = new ArrayList<>();

    public SmallZombie() {
        this.setName("Small-Zombie");
        this.setDamage(15.0);
    }

    public SmallZombie(ArrayList<Decoration> decorations, ArrayList<Wall> walls) {
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

    public void setInterval() {
        this.updateIntervalSeconds = 2;
    }

    public void setStrategies() {
        this.strategies = Map.of(
                Phases.AWAY, new MoveAwayFromPlayer(),
                Phases.RANDOM, new MoveRandomly(),
                Phases.TOWARDS, new MoveTowardPlayer(),
                Phases.ATTACK, new Attack()
        );
    }

    @Override
    public void debug() {
//         System.out.println("Small zombie:" + this.getID() + " has been created");
    }
}
