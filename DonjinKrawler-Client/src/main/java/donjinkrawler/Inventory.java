package donjinkrawler;

import donjinkrawler.frames.InventoryFrame;
import donjinkrawler.items.Armor;
import donjinkrawler.items.Weapon;

public class Inventory {

    private final InventoryFrame frame;

    private Weapon weapon;
    private Armor armor;

    public Inventory() {
        this.frame = new InventoryFrame();
    }

    public void open() {
        this.frame.setVisible(true);
    }

    public void close() {
        this.frame.setVisible(false);
    }

    public void addWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.frame.setWeapon(this.weapon);
    }

    public void addArmor(Armor armor) {
        this.armor = armor;
        this.frame.setArmor(this.armor);
    }

    public void removeWeapon() {
        this.weapon = null;
        this.frame.setWeapon(null);
    }

    public void removeArmor() {
        this.armor = null;
        this.frame.setArmor(null);
    }
}