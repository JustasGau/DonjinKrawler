package donjinkrawler.enemy_generation;

public abstract class Enemy {

    private String name;
    private double damage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
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
