package krawlercommon.enemies;

import com.esotericsoftware.kryonet.Server;
import krawlercommon.observer.Observer;
import krawlercommon.packets.ChangeEnemyStrategyPacket;
import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveAwayFromPlayer;
import krawlercommon.strategies.MoveRandomly;
import krawlercommon.strategies.MoveTowardPlayer;

import java.util.Random;
import java.util.UUID;

public abstract class Enemy implements Observer {

    private String name;
    private double damage;
    private int id = (int) UUID.randomUUID().getMostSignificantBits();
    Random random = new Random();
    private int x = random.nextInt(450);
    private int y = random.nextInt(450);
    private int dx;
    private int dy;

    transient EnemyStrategy[] strategies = {new MoveAwayFromPlayer(), new MoveRandomly(), new MoveTowardPlayer()};
    transient EnemyStrategy currentStrategy;
    int updateIntervalSeconds = 2;
    int tick = 0;
    String info;

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInterval(int interval) {
        updateIntervalSeconds = interval;
    }

    public void setStrategies(EnemyStrategy[] strat) {
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

    public int getY() {
        return y;
    }

    public void setX(int val) {
        x = val;
    }

    public void setY(int val) {
        y = val;
    }


    public void incrementTick(int fps, Server server) {
        if (tick == updateIntervalSeconds * fps) {
            tick = 0;
            nextStrategy(server);

        } else {
            tick++;
            if (currentStrategy != null) {
                currentStrategy.execute();
            }
        }
    }

    private void nextStrategy(Server server) {
        int strategyID = random.nextInt(strategies.length);
        currentStrategy = strategies[strategyID];
        currentStrategy.init(this);

        ChangeEnemyStrategyPacket packet = new ChangeEnemyStrategyPacket();
        packet.id = id;
        packet.strategy = currentStrategy;
        server.sendToAllTCP(packet);
    }

    public void setCurrentStrategy(EnemyStrategy strategy) {
        this.currentStrategy = strategy;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void move() {
        if ((dx < 0 && x - dx > 2) || (dx > 0 && x + 20 + dx < 500))
            x += dx;
        if ((dy < 0 && y - dy > 2) || (dy > 0 && y + 20 + 50 + dy < 500))
            y += dy;
    }

    public void setDx(int val) { dx = val;}

    public void setDy(int val) { dy = val; }


}
