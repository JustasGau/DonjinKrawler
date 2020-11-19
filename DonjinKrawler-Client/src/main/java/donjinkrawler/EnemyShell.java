package donjinkrawler;

import donjinkrawler.flyweight.EnemyType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EnemyShell extends AbstractShell {

    private int id;
    private String info = "No strategy";
    private Map<String, ImageIcon> clothes = new HashMap<>();
    private EnemyType type;

    public EnemyShell(String name, int id, int x, int y, EnemyType type) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public void loadImage() {

    }

    @Override
    public Image getImage() {
        return type.getImage();
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

}
