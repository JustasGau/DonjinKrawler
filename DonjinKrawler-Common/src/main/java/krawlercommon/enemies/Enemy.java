package krawlercommon.enemies;

import com.esotericsoftware.kryonet.Server;
import krawlercommon.KrawlerCloneable;
import krawlercommon.map.CollidableObject;
import krawlercommon.map.Decoration;
import krawlercommon.map.Wall;
import krawlercommon.observer.Observer;
import krawlercommon.packets.ChangeEnemyStrategyPacket;
import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveTowardPlayer;

import java.util.*;

public abstract class Enemy extends EnemyTemplate implements Observer, KrawlerCloneable {

    public transient Map<Phases, EnemyStrategy> strategies;
    protected int updateIntervalSeconds;
    Random random = new Random();
    transient EnemyStrategy currentStrategy;
    int tick = 0;
    String info;
    private String name;
    private double damage;
    private int id = (int) UUID.randomUUID().getMostSignificantBits();
    private int x = random.nextInt(430) + 20;
    private int y = random.nextInt(430) + 20;
    private int dx;
    private int dy;
    private Phases strategyPhase = Phases.RANDOM;
    private double health = 100;
    private boolean updateStatus = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getX() {
        return x;
    }

    public void setX(int val) {
        x = val;
    }

    public int getY() {
        return y;
    }

    public void setY(int val) {
        y = val;
    }

    public void incrementTick(int fps, Server server) {
        checkStrategyCondition(server);
        if (updateStatus) {
            ChangeEnemyStrategyPacket packet = new ChangeEnemyStrategyPacket();
            packet.id = id;
            packet.strategy = currentStrategy;
            server.sendToAllTCP(packet);
            updateStatus = false;
        }

        if (currentStrategy != null) {
            currentStrategy.execute();
        }
    }

    private void checkStrategyCondition(Server server) {
        if (strategyPhase == Phases.RANDOM) {
            currentStrategy = strategies.get(strategyPhase);
            nextStrategy(server);
        }
        if (strategyPhase != Phases.TOWARDS && health < 100) {
            strategyPhase = Phases.TOWARDS;
            currentStrategy = strategies.get(strategyPhase);
            nextStrategy(server);
        }
        if (strategyPhase != Phases.AWAY && health < 30) {
            strategyPhase = Phases.AWAY;
            currentStrategy = strategies.get(strategyPhase);
            nextStrategy(server);
        }
    }

    public void setPhase(Phases phase) {
        strategyPhase = phase;
    }

    private void nextStrategy(Server server) {
        currentStrategy.init(this);

        ChangeEnemyStrategyPacket packet = new ChangeEnemyStrategyPacket();
        packet.id = id;
        packet.strategy = currentStrategy;
        server.sendToAllTCP(packet);
    }

    public Phases getStrategyPhase() {
        return strategyPhase;
    }

    public void setCurrentStrategy(EnemyStrategy strategy) {
        if (strategy instanceof MoveTowardPlayer) {
            strategyPhase = Phases.TOWARDS;
            currentStrategy = strategies.get(strategyPhase);
            currentStrategy.init(this);
            updateStatus = true;
        }
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public abstract void move();

    public void move(ArrayList<Decoration> decorations, ArrayList<Wall> walls) {
        if (isCollidingWithImmovableObject(walls) || isCollidingWithImmovableObject(decorations)) {
            return;
        }
        x += dx;
        y += dy;
    }

    private boolean isCollidingWithImmovableObject(List<? extends CollidableObject> objects) {
        for (CollidableObject object : objects) {
            if (isCollidingWith(object)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollidingWith(CollidableObject obj) {
        // FIXME: UGLY HACK WITH HARDCODED WIDTH
        // 20 = enemy image width
        int topCornerX = x + dx;
        int topCornerY = y + dy;
        int botCornerX = x + 20 + dx;
        int botCornerY = y + 20 + dy;
        return obj.getTopX() < botCornerX && obj.getBotX() > topCornerX
                && obj.getTopY() < botCornerY && obj.getBotY() > topCornerY;
    }

    public void setDx(int val) {
        dx = val;
    }

    public void setDy(int val) {
        dy = val;
    }

    public void damage(double val) {
        health -= val;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    @Override
    public Enemy clone() throws CloneNotSupportedException {
        return (Enemy) super.clone();
    }

    public enum Phases {
        RANDOM,
        TOWARDS,
        AWAY,
        ATTACK,
        RANGED
    }
}
