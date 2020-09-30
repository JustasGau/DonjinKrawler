package donjinkrawler;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class EnemyShell extends AbstractShell {

    private int dx;
    private int dy;
    private int x;
    private int y;
    private String name;
    private Image image;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
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
