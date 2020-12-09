package donjinkrawler;

import donjinkrawler.decorator.clothes.Clothing;
import donjinkrawler.visitor.ClothingVisitor;

import java.awt.*;
import java.util.List;

public interface AbstractShellInterface {

    List<Clothing> addClothing();

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    String getName();

    Image getImage();

    int getID();

    String getInfo();

    void setInfo(String info);

    void drawClothes(ClothingVisitor visitor);

    double getHealth();

    void setHealth(double val);

    void damage(double damage);

    Image getAttackImage();

    boolean isAttacking();

    void setIsAttacking(boolean attack);

    ShellType getShellType();
}
