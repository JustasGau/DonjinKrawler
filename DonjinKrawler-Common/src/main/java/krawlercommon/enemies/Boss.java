package krawlercommon.enemies;

import krawlercommon.map.Decoration;
import krawlercommon.map.Wall;
import krawlercommon.strategies.Attack;
import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveTowardPlayer;
import krawlercommon.strategies.RangeAttack;

import java.util.ArrayList;
import java.util.Map;

public final class Boss extends Enemy {

    private ArrayList<Decoration> decorations = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();

    public Boss() {
        this.setX(225);
        this.setY(225);
        this.setName("Boss");
        this.setDamage(15.0);
        this.initEnemy();
        this.setPhase(Phases.TOWARDS);
    }

    public Boss(ArrayList<Decoration> decorations, ArrayList<Wall> walls) {
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

    public void setInterval() {
        this.updateIntervalSeconds = 2;
    }

    public void setStrategies() {
        this.strategies = Map.of(
                Phases.TOWARDS, new MoveTowardPlayer(),
                Phases.ATTACK, new Attack(),
                Phases.RANGED, new RangeAttack()
        );
    }
}
