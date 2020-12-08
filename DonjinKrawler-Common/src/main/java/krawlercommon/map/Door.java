package krawlercommon.map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public final class Door {
    private int x;
    private int y;
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
        try {
            InputStream stream = getClass().getResourceAsStream("/door.png");
            ImageIcon ii = new ImageIcon(ImageIO.read(stream));
            doorImage = ii.getImage();
        } catch (Exception ignored) {

        }
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