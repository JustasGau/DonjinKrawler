package donjinkrawler.flyweight;

import javax.swing.*;
import java.awt.*;

public class EnemyFlyweight {
    private String name;
    private Image image;

    public EnemyFlyweight(String name) {
        this.name = name;
        loadImage();
    }

    public void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource(this.resolveImageName()).getFile());
        image = ii.getImage();
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    private String resolveImageName() {
        if (this.name.equals("Small-Zombie")) {
            return "zombie.png";
        }

        if (this.name.equals("Big-Zombie")) {
            return "zombie-big.png";
        }

        if (this.name.equals("Small-Skeleton")) {
            return "skeleton.png";
        }

        if (this.name.equals("Big-Skeleton")) {
            return "skeleton-big.png";
        }

        if (this.name.equals("Small-Chicken")) {
            return "chicken.png";
        }

        if (this.name.equals("Big-Chicken")) {
            return "chicken-big.png";
        }

        if (this.name.equals("Boss")) {
            return "boss.png";
        }

        return "zombie.png";
    }

}
