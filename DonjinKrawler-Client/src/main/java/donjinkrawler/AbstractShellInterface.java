package donjinkrawler;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public interface AbstractShellInterface {

    Map<String, ImageIcon> addClothing();

    int getX();

    int getY();

    String getName();

    void setX(int x);

    void setY(int y);

    Image getImage();

    int getID();

    String getInfo();

    void setInfo(String info);

    void drawClothes(Graphics2D g2d, Game game);

    double getHealth();

    void damage(double damage);

    void setHealth(double val);

    Image getAttackImage();

    boolean isAttacking();

    void setIsAttacking(boolean attack);
}
