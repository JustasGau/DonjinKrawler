package donjinkrawler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public interface AbstractShellInterface {

    ArrayList<ImageIcon> addClothing();

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
}
