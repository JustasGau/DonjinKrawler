package donjinkrawler;

import donjinkrawler.items.BaseItem;
import donjinkrawler.items.ItemMaker;
import krawlercommon.items.ItemLocationData;
import krawlercommon.map.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {

    RoomData roomData;
    private final int x = 250;
    private final int y = 250;

    private final ArrayList<Door> doors = new ArrayList<>();
    private final Map<String, BufferedImage> wallImages = new HashMap<>();
    private final Map<String, BufferedImage> tileImages = new HashMap<>();
    private final Map<String, BufferedImage> obstacleImages = new HashMap<>();
    private final Map<String, BufferedImage> decorationImages = new HashMap<>();

    public Room(RoomData roomData) {
        this.roomData = roomData;
        initDoors();
        loadImages();
    }

    private void initDoors() {
        if (roomData.getTop() != null) {
            doors.add(new Door(DoorDirection.TOP, 250, 0));
        }
        if (roomData.getBottom() != null) {
            doors.add(new Door(DoorDirection.BOTTOM, 250, 460));
        }
        if (roomData.getRight() != null) {
            doors.add(new Door(DoorDirection.RIGHT, 480, 250));
        }
        if (roomData.getLeft() != null) {
            doors.add(new Door(DoorDirection.LEFT, 0, 250));
        }
    }

    private void loadImages() {

        try {
            readImages("walls", wallImages);
            readImages("floors", tileImages);
            readImages("obstacles", obstacleImages);
            readImages("decorations", decorationImages);
        } catch (IOException ignored) {

        }
    }

    private void readImages(String imageFolder, Map<String, BufferedImage> hashMap) throws IOException {
        BufferedImage image;
        for (File f : FileUtils.getResourceFolderFiles(imageFolder)) {
            image = ImageIO.read(f);
            hashMap.put(f.getName().split("\\.")[0], image);
        }
    }

    public DoorDirection update(Player player) {
        for (Door door : doors) {
            if (door.checkCollision(player.getX(), player.getY(), player.getBotX(),
                    player.getBotY(), player.getWidth(), player.getWidth())) {
                return door.getDirection();
            }
        }
        return null;
    }

    public RoomData getRoomFromDirection(DoorDirection direction) {
        return switch (direction) {
            case TOP -> roomData.getTop();
            case LEFT -> roomData.getLeft();
            case RIGHT -> roomData.getRight();
            case BOTTOM -> roomData.getBottom();
        };
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.drawString(String.valueOf(roomData.getId()), x, y);
        drawTiles(g2d);
        drawObstacles(g2d);
        drawDecorations(g2d);
        drawWalls(g2d);
        drawDoors(g2d);
        drawItems(g2d);
    }

    private void drawTiles(Graphics2D g2d) {
        BufferedImage tileImage = tileImages.get(String.valueOf(roomData.getTileTexture()));
        Rectangle rectangle = new Rectangle(0, 0, tileImage.getWidth(), tileImage.getHeight());
        TexturePaint tp = new TexturePaint(tileImage, rectangle);
        g2d.setPaint(tp);
        for (int y = 0; y < 500; y += tileImage.getHeight()) {
            for (int x = 0; x < 500; x += tileImage.getWidth()) {
                g2d.fill(new Rectangle(x, y, tileImage.getWidth(), tileImage.getHeight()));
            }
        }
    }

    private void drawObstacles(Graphics2D g2d) {
        for (Obstacle obstacle : roomData.getObstacles()) {
            BufferedImage obstacleImage = obstacleImages.get(obstacle.getObstacleType().number);
            fillImage(g2d, obstacleImage, obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
        }
    }

    private void drawDecorations(Graphics2D g2d) {
        for (Decoration decoration : roomData.getDecorations()) {
            BufferedImage decorationImage = decorationImages.get(String.valueOf(decoration.getImageNumber()));
            g2d.drawImage(decorationImage, decoration.getX(), decoration.getY(), null);
        }
    }

    private void drawWalls(Graphics2D g2d) {
        for (Wall wall : roomData.getWalls()) {
            BufferedImage image = wallImages.get(String.valueOf(wall.getTexture()));
            fillImage(g2d, image, wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
        }
    }

    private void fillImage(Graphics2D g2d, BufferedImage image, int x, int y, int width, int height) {
        Rectangle rectangle = new Rectangle(0, 0, image.getWidth(), image.getHeight());
        TexturePaint tp = new TexturePaint(image, rectangle);
        g2d.setPaint(tp);
        g2d.fill(new Rectangle(x, y, width, height));
    }

    private void drawDoors(Graphics2D g2d) {
        for (Door door : doors) {
            g2d.drawImage(door.getImage(), door.getX(), door.getY(), null);
        }
    }

    private void drawItems(Graphics2D g2d) {
        for (ItemLocationData item : roomData.getItems().values()) {
            BaseItem gameItem = ItemMaker.makeItem(item);
            if (gameItem != null) {
                g2d.drawImage(gameItem.getImage(), gameItem.getData().getX(), gameItem.getData().getY(), null);
            }
        }
    }

    public ArrayList<Wall> getWalls() {
        return roomData.getWalls();
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public ArrayList<Obstacle> getObstacles() {
        return roomData.getObstacles();
    }

    public ArrayList<Decoration> getDecorations() {
        return roomData.getDecorations();
    }
}
