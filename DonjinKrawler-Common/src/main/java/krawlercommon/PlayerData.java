package krawlercommon;

import krawlercommon.composite.Attribute;

public class PlayerData  {
    private String name;
    private int id;
    private int x;
    private int y;
    private double health;
    private Attribute maxHealth;
    private Attribute speed;
    private Attribute damage;

    public PlayerData() {

    }

    public PlayerData(String name, int id, int x, int y) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
        health = 100;
        maxHealth = new Attribute(100);
        speed = new Attribute(2);
        damage = new Attribute(5);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public Attribute getMaxHealth() {
        return maxHealth;
    }

    public Attribute getSpeed() {
        return speed;
    }

    public Attribute getDamage() {
        return damage;
    }

    public void adjustHealthWithMaxHealth() {
        if (health != maxHealth.getFinalValue()) {
            double maxHealthValue = maxHealth.getFinalValue();
            double difference = maxHealthValue - maxHealth.getBaseValue();
            health += difference;
        }
    }
}
