package krawlercommon.enemies;

import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveAwayFromPlayer;
import krawlercommon.strategies.MoveRandomly;
import krawlercommon.strategies.MoveTowardPlayer;

import java.util.Random;
import java.util.UUID;

public abstract class Enemy {

    private String name;
    private double damage;
    private int id = (int) UUID.randomUUID().getMostSignificantBits();
    Random random = new Random();
    private int x = random.nextInt(450);
    private int y = random.nextInt(450);

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

    public void incrementTick(int fps) {
        if (tick == updateIntervalSeconds * fps) {
            tick = 0;
            nextStrategy();

        } else {
            tick++;
            if (currentStrategy != null) {
                currentStrategy.execute();
            }
        }
    }

    private void nextStrategy() {
        int strategyID = random.nextInt(strategies.length);
        currentStrategy = strategies[strategyID];
        currentStrategy.init(this);
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void showOnScreen() {
        System.out.println(this.getName() + " is on screen.");
    }

    public void followHero() {
        System.out.println(this.getName() + " is following the hero.");
    }

    public void attackHero() {
        System.out.println(this.getName() + " attacks and does " + this.getDamage());
    }
}