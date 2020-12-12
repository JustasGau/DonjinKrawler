package donjinkrawler;

import donjinkrawler.decorator.clothes.Clothing;
import donjinkrawler.flyweight.EnemyFlyweight;
import donjinkrawler.visitor.ClothingVisitor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyShell extends AbstractShell {

    private int id;
    private String info = "No strategy";
    private List<Clothing> clothes = new ArrayList<>();
    private EnemyFlyweight type;

    public EnemyShell(String name, int id, int x, int y, EnemyFlyweight type) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public void loadImage() {
        // Not needed for EnemyShell
    }

    @Override
    public String getName() {
        return this.type.getName();
    }

    @Override
    public Image getImage() {
        return type.getImage();
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
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
