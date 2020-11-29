package krawlercommon.enemies;

import com.esotericsoftware.kryonet.Server;
import krawlercommon.KrawlerCloneable;
import krawlercommon.observer.Observer;
import krawlercommon.packets.ChangeEnemyStrategyPacket;
import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveTowardPlayer;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public abstract class Enemy extends EnemyTemplate implements Observer, KrawlerCloneable {

    Random random = new Random();
    public transient Map<Phases, EnemyStrategy> strategies;
    transient EnemyStrategy currentStrategy;
    public int updateIntervalSeconds;
    int tick = 0;
    String info;
    private String name;
    private double damage;
    private int id = (int) UUID.randomUUID().getMostSignificantBits();
    private int x = random.nextInt(450);
    private int y = random.nextInt(450);
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

    public void move() {
        if ((dx < 0 && x - dx > 2) || (dx > 0 && x + 20 + dx < 500)) {
            x += dx;
        }
        if ((dy < 0 && y - dy > 2) || (dy > 0 && y + 20 + 50 + dy < 500)) {
            y += dy;
        }
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

    public enum Phases {
        RANDOM,
        TOWARDS,
        AWAY,
        ATTACK,
        RANGED
    }

    @Override
    public Enemy clone() throws CloneNotSupportedException {
        return (Enemy) super.clone();
    }
}
