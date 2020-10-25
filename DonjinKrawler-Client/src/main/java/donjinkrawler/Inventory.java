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
        this.frame.setArmor(this.armor);
        this.frame.setWeapon(this.weapon);
        this.frame.setVisible(true);
    }

    public void addWeapon(Weapon weapon) {
        if(this.weapon == null) {
            this.weapon = weapon;
        }
    }

    public void addArmor(Armor armor) {
        if(this.armor == null) {
            this.armor = armor;
        }
    }

    public void removeWeapon() {
        this.weapon = null;
    }

    public void removeArmor() {
        this.armor = null;
    }
}