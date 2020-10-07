package donjinkrawler.enemies.small;

import donjinkrawler.enemies.Zombie;

public class SmallZombie extends Zombie {

    public SmallZombie(int id) {
        this.setName("Small-Zombie");
        this.setDamage(15.0);
        this.setID(id);
    }

}
