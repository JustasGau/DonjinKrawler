package donjinkrawler.flyweight;

import donjinkrawler.AbstractShell;
import donjinkrawler.ShellType;
import donjinkrawler.decorator.clothes.Clothing;
import donjinkrawler.visitor.ClothingVisitor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyShellForTest extends AbstractShell {

    private int id;
    private String info = "No strategy";
    private List<Clothing> clothes = new ArrayList<>();

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

    public void drawClothes(ClothingVisitor visitor) {
        for (Clothing entry : clothes) {
            entry.accept(visitor);
        }
    }

    @Override
    public void damage(double damage) {
        setHealth(getHealth() - damage);
    }

    @Override
    public List<Clothing> addClothing() {
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
