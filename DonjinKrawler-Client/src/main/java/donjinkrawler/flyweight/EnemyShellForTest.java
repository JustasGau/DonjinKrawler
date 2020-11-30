package donjinkrawler.flyweight;

import donjinkrawler.AbstractShell;
import donjinkrawler.Game;
import donjinkrawler.ShellType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EnemyShellForTest extends AbstractShell {

    private int id;
    private String info = "No strategy";
    private Map<String, ImageIcon> clothes = new HashMap<>();
    private EnemyFlyweight type;

    public EnemyShellForTest(String name, int id, int x, int y) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource(this.resolveImageName()).getFile());
        image = ii.getImage();
    }

    public Image getImage() {
        return image;
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

    public int getID() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void drawClothes(Graphics2D g2d, Game g) {
        int offsetX;
        int offsetY;
        for (Map.Entry<String, ImageIcon> entry : clothes.entrySet()) {
            Image clothImage = entry.getValue().getImage();
            switch (entry.getKey()) {
                case "Maracas":
                case "Poncho":
                    offsetX = 0;
                    offsetY = 5;
                    break;
                default:
                    offsetX = 2;
                    offsetY = -3;
            }
            g2d.drawImage(clothImage, getX() + offsetX, getY() + offsetY, g);
        }
    }

    @Override
    public void damage(double damage) {
        setHealth(getHealth() - damage);
    }

    @Override
    public Map<String, ImageIcon> addClothing() {
        return clothes;
    }

    @Override
    public Image getAttackImage() {
        return null;
    }

    @Override
    public ShellType getShellType() {
        return ShellType.ENEMY;
    }

}
