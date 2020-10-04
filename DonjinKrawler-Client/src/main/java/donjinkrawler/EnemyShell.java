package donjinkrawler;

import javax.swing.*;
import java.util.Random;

public class EnemyShell extends AbstractShell {

    public EnemyShell(String name) {
        this.name = name;
        Random random = new Random();
        this.x = random.nextInt(250);
        this.y = random.nextInt(250);
        loadImage();
    }

    protected void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource(this.resolveImageName()).getFile());
        image = ii.getImage();
    }

    private String resolveImageName()
    {
        if(this.getName().equals("Small Zombie")) {
            return "zombie.png";
        }

        if(this.getName().equals("Big Zombie")) {
            return "zombie-big.png";
        }

        if(this.getName().equals("Small Skeleton")) {
            return "skeleton.png";
        }

        if(this.getName().equals("Big Skeleton")) {
            return "skeleton-big.png";
        }

        if(this.getName().equals("Small Chicken")) {
            return "chicken.png";
        }

        if(this.getName().equals("Big Chicken")) {
            return "chicken-big.png";
        }

        return "zombie.png";
    }
}
