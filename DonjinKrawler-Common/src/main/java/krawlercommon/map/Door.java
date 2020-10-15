package krawlercommon.map;

import javax.swing.*;
import java.awt.*;

public class Door {
    private int x;
    private int y;
    private int width = 20;
    private int height = 20;
    private DoorDirection direction;
    private transient Image doorImage;

    public Door() {

    }

    public Door(DoorDirection direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        loadImage();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("door.png").getFile());
        doorImage = ii.getImage();
        width = doorImage.getWidth(null);
        height = doorImage.getHeight(null);
    }

    public boolean checkCollision(int topCornerX, int topCornerY, int botCornerX,
                                  int botCornerY, int width, int height) {
        int topCenter = topCornerX + width / 2;
        int sideCenter = topCornerY + height / 2;
        if (direction == DoorDirection.BOTTOM) {
            return topCenter >= x && topCenter <= getBotX() && botCornerY >= y;
        } else if (direction == DoorDirection.TOP) {
            return topCenter >= x && topCenter <= getBotX() && topCornerY <= getBotY();
        } else if (direction == DoorDirection.LEFT) {
            return topCornerX <= getBotX() && sideCenter <= getBotY() && sideCenter >= y;
        } else if (direction == DoorDirection.RIGHT) {
            return botCornerX >= x && sideCenter <= getBotY() && sideCenter >= y;
        }
        return false;
    }

    public DoorDirection getDirection() {
        return direction;
    }

    public Image getImage() {
        return doorImage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBotX() {
        return x + getImage().getWidth(null);
    }

    public int getBotY() {
        return y + getImage().getHeight(null);
    }
}