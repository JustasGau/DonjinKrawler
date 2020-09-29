package donjinkrawler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class GameMap {

    private final int x = 0;
    private final int y = 0;
    private Image image;

    private int id;
    private Door[] doors = new Door[4];

    public GameMap(int id, int[] doorLocations) {
        if (doorLocations[0] == 1)
            doors[0] = new Door(Game.DoorDirection.LEFT,0,250);
        if (doorLocations[1] == 1)
            doors[1] = new Door(Game.DoorDirection.TOP, 250,0);
        if (doorLocations[2] == 1)
            doors[2] = new Door(Game.DoorDirection.RIGHT, 480,250);
        if (doorLocations[3] == 1)
            doors[3] = new Door(Game.DoorDirection.BOTTOM, 250,420);
        System.out.println(doorLocations[0] + " " +doorLocations[1]+ " " +doorLocations[2]+ " " +doorLocations[3]);
        this.id = id;
//        loadImage();
    }

    public Image getImage() {
        return image;
    }

    private class Door {
        private int x;
        private int y;
        private int width;
        private int height;
        Game.DoorDirection direction;

        public Door(Game.DoorDirection direction, int x, int y){
            this.direction = direction;
            this.x = x;
            this.y = y;
            loadImage();
        }
        private void loadImage() {
            ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("door.png").getFile());
            image = ii.getImage();
            width = image.getWidth(null);
            height = image.getHeight(null);
        }

        public Boolean checkCollision(int PlayerX, int PlayerY) {
            Boolean collision = false;
            if (PlayerX+10 >= x && PlayerX+10 < x + width &&
                PlayerY+10 >= y && PlayerY+10 < y + height) {
                collision = true;
            }
            return collision;
        }
        public Game.DoorDirection getDirection(){
            return direction;
        }

        public Image getImage() {
            return image;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }

    public Game.DoorDirection update(Player player) {
        for (int i = 0; i < doors.length; i++) {
           if (doors[i] != null && doors[i].checkCollision(player.getX(), player.getY()))
               return doors[i].getDirection();
        }
        return null;
    }
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.drawString(String.valueOf(id) , x, y);
        for (int i = 0; i < doors.length; i++) {
            if (doors[i] != null)
                g2d.drawImage(doors[i].getImage(), doors[i].getX(), doors[i].getY(), null);
        }
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("x.jpg").getFile());
        image = ii.getImage();
    }
}
