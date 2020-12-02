package donjinkrawler;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public interface AbstractShellInterface {

    Map<String, ImageIcon> addClothing();

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    String getName();

    Image getImage();

    int getID();

    String getInfo();

    void setInfo(String info);

    void drawClothes(Graphics2D g2d, Game game);

    double getHealth();

    void setHealth(double val);

    void damage(double damage);

    Image getAttackImage();

    boolean isAttacking();

    void setIsAttacking(boolean attack);
}
