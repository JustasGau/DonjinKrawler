package donjinkrawler;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EnemyShell extends AbstractShell {

    private int id;
    private String info = "No strategy";
    private Map<String, ImageIcon> clothes = new HashMap<>();

    public EnemyShell(String name, int id, int x, int y) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
        loadImage();
    }

    public void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource(this.resolveImageName()).getFile());
        image = ii.getImage();
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

    private String resolveImageName() {
        if (this.getName().equals("Small-Zombie")) {
            return "zombie.png";
        }

        if (this.getName().equals("Big-Zombie")) {
            return "zombie-big.png";
        }

        if (this.getName().equals("Small-Skeleton")) {
            return "skeleton.png";
        }

        if (this.getName().equals("Big-Skeleton")) {
            return "skeleton-big.png";
        }

        if (this.getName().equals("Small-Chicken")) {
            return "chicken.png";
        }

        if (this.getName().equals("Big-Chicken")) {
            return "chicken-big.png";
        }

        if (this.getName().equals("Boss")) {
            return "boss.png";
        }

        return "zombie.png";
    }

    public void drawClothes(Graphics2D g2d, Game g) {
        int offsetX;
        int offsetY;
        for (Map.Entry<String, ImageIcon> entry : clothes.entrySet()) {
            Image clothImage = entry.getValue().getImage();
            switch (entry.getKey()) {
                case "Maracas", "Poncho" -> {
                    offsetX = 0;
                    offsetY = 5;
                }
                default -> {
                    offsetX = 2;
                    offsetY = -3;
                }
            }
            g2d.drawImage(clothImage, getX() + offsetX, getY() + offsetY, g);
        }
    }

    @Override
    public void damage(double damage) {

    }

    @Override
    public Map<String, ImageIcon> addClothing() {
        return clothes;
    }

    @Override
    public Image getAttackImage() {
        return null;
    }

}
