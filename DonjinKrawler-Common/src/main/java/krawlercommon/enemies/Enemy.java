package krawlercommon.enemies;

import com.esotericsoftware.kryonet.Server;
import krawlercommon.observer.Observer;
import krawlercommon.packets.ChangeEnemyStrategyPacket;
import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveAwayFromPlayer;
import krawlercommon.strategies.MoveRandomly;
import krawlercommon.strategies.MoveTowardPlayer;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public abstract class Enemy implements Observer {

    Random random = new Random();
    transient Map<Phases, EnemyStrategy> strategies = Map.of(
            Phases.AWAY, new MoveAwayFromPlayer(),
            Phases.RANDOM, new MoveRandomly(),
            Phases.TOWARDS, new MoveTowardPlayer()
    );
    transient EnemyStrategy currentStrategy;
    int updateIntervalSeconds = 2;
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

    public void setInterval(int interval) {
        updateIntervalSeconds = interval;
    }

    public void setStrategies(Map<Phases, EnemyStrategy> strat) {
        strategies = strat;
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

    public enum Phases {
        RANDOM,
        TOWARDS,
        AWAY,
        ATTACK,
        RANGED
    }

}
