package donjinkrawler;

import javax.swing.*;
import java.util.Random;

public class EnemyShell extends AbstractShell {

    private int id;
    private String info = "empty";

    public EnemyShell(String name, int id, int x, int y) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
        loadImage();
    }

    protected void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource(this.resolveImageName()).getFile());
        image = ii.getImage();
    }

    public int getID() { return id; }

    public String getInfo() { return info; }

    public void setInfo(String info){
        this.info = info;

    }

    private String resolveImageName()
    {
        if(this.getName().equals("Small-Zombie")) {
            return "zombie.png";
        }

        if(this.getName().equals("Big-Zombie")) {
            return "zombie-big.png";
        }

        if(this.getName().equals("Small-Skeleton")) {
            return "skeleton.png";
        }

        if(this.getName().equals("Big-Skeleton")) {
            return "skeleton-big.png";
        }

        if(this.getName().equals("Small-Chicken")) {
            return "chicken.png";
        }

        if(this.getName().equals("Big-Chicken")) {
            return "chicken-big.png";
        }

        return "zombie.png";
    }
}