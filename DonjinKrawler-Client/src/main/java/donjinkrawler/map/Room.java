package donjinkrawler.map;

import donjinkrawler.FileUtils;
import donjinkrawler.Player;
import donjinkrawler.SwingUtils;
import donjinkrawler.items.BaseItem;
import donjinkrawler.items.ItemMaker;
import krawlercommon.KrawlerCloneable;
import krawlercommon.items.ItemLocationData;
import krawlercommon.iterator.Iterator;
import krawlercommon.iterator.door.DoorCollection;
import krawlercommon.map.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room implements KrawlerCloneable {

    private RoomData roomData;
    private DoorCollection doorCollection = new DoorCollection();
    private Map<String, BufferedImage> wallImages = new HashMap<>();
    private Map<String, BufferedImage> tileImages = new HashMap<>();
    private Map<String, BufferedImage> obstacleImages = new HashMap<>();
    private Map<String, BufferedImage> decorationImages = new HashMap<>();

    private HashMap<Integer, BaseItem> items = new HashMap<>();

    public Room(RoomData roomData) {
        this.roomData = roomData;
        initDoors();
        loadImages();
    }

    public void initDoors() {
        if (!doorCollection.doors.isEmpty()) {
            doorCollection = new DoorCollection();
        }
        if (roomData.getTop() != null) {
            doorCollection.doors.add(new Door(DoorDirection.TOP, 250, 0));
        }
        if (roomData.getBottom() != null) {
            doorCollection.doors.add(new Door(DoorDirection.BOTTOM, 250, 460));
        }
        if (roomData.getRight() != null) {
            doorCollection.doors.add(new Door(DoorDirection.RIGHT, 480, 250));
        }
        if (roomData.getLeft() != null) {
            doorCollection.doors.add(new Door(DoorDirection.LEFT, 0, 250));
        }
    }

    private void loadImages() {

        try {
            readImages("walls", wallImages);
            readImages("floors", tileImages);
            readImages("obstacles", obstacleImages);
            readImages("decorations", decorationImages);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void readImages(String imageFolder, Map<String, BufferedImage> hashMap)
            throws IOException, URISyntaxException {
        BufferedImage image;
        for (URL url : FileUtils.getResourceFolderFiles(imageFolder)) {
            image = ImageIO.read(url);
            String[] splits = url.toString().split("/");
            hashMap.put(splits[splits.length - 1].split("\\.")[0], image);
        }
    }

    public DoorDirection update(Player player) {
        for (Iterator i = doorCollection.getIterator(); i.hasNext(); ) {
            Door door = (Door) i.getNext();
            if (door.checkCollision(player.getX(), player.getY(), player.getBotX(),
                    player.getBotY(), player.getWidth(), player.getWidth())) {
                return door.getDirection();
            }
        }
        return null;
    }

    public RoomData getRoomFromDirection(DoorDirection direction) {
        switch (direction) {
            case TOP:
                return roomData.getTop();
            case LEFT:
                return roomData.getLeft();
            case RIGHT:
                return roomData.getRight();
            case BOTTOM:
                return roomData.getBottom();
            default:
                throw new IllegalStateException("Unsupported argument provided");
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.drawString(String.valueOf(roomData.getId()), 250, 250);
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
            BufferedImage obstacleImage = obstacleImages.get(obstacle.getImageNumber());
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
        for (Iterator i = doorCollection.getIterator(); i.hasNext(); ) {
            Door door = (Door) i.getNext();
            g2d.drawImage(door.getImage(), door.getX(), door.getY(), null);
        }
    }

    private void drawItems(Graphics2D g2d) {
        for (ItemLocationData item : getRoomData().getItems().values()) {
            BaseItem gameItem = ItemMaker.makeItem(item);
            items.put(item.getId(), gameItem);
            if (gameItem != null) {
                g2d.drawImage(gameItem.getImage(), gameItem.getData().getX(), gameItem.getData().getY(), null);
            }
        }
    }

    public void removeItem(Integer itemId) {
        this.items.remove(itemId);
        this.roomData.removeItem(itemId);
    }

    public ArrayList<Wall> getWalls() {
        return roomData.getWalls();
    }

    public DoorCollection getDoors() {
        return doorCollection;
    }

    public ArrayList<Obstacle> getObstacles() {
        return roomData.getObstacles();
    }

    public ArrayList<Decoration> getDecorations() {
        return roomData.getDecorations();
    }

    public HashMap<Integer, BaseItem> getItems() {
        return this.items;
    }

    @Override
    public Room deepCopy() throws CloneNotSupportedException {
        Room clone = (Room) super.clone();
        clone.setRoomData(roomData.deepCopy());
        clone.doorCollection = new DoorCollection();
        clone.initDoors();
        clone.wallImages = new HashMap<>();
        cloneImageMap(wallImages, clone.wallImages);
        clone.tileImages = new HashMap<>();
        cloneImageMap(tileImages, clone.tileImages);
        clone.decorationImages = new HashMap<>();
        cloneImageMap(decorationImages, clone.decorationImages);
        clone.obstacleImages = new HashMap<>();
        cloneImageMap(obstacleImages, clone.obstacleImages);
        clone.items = new HashMap<>();
        cloneItems(items, clone.items);
        return clone;
    }


    private void cloneImageMap(Map<String, BufferedImage> origMap, Map<String, BufferedImage> dest) {
        for (Map.Entry<String, BufferedImage> entry : origMap.entrySet()) {
            if (entry.getValue() != null) {
                dest.put(entry.getKey(), SwingUtils.deepCopy(entry.getValue()));
            }
        }
    }

    private void cloneItems(HashMap<Integer, BaseItem> origMap, HashMap<Integer, BaseItem> dest)
            throws CloneNotSupportedException {
        for (Map.Entry<Integer, BaseItem> entry : origMap.entrySet()) {
            dest.put(entry.getKey(), entry.getValue().deepCopy());
        }
    }

    @Override
    public Room clone() throws CloneNotSupportedException {
        return (Room) super.clone();
    }

    public RoomData getRoomData() {
        return roomData;
    }

    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
    }

    public List<String> getAddressValues() {
        List<String> addressValues = new ArrayList<>();
        addressValues.add("Room hashcode: " + System.identityHashCode(this));
        addressValues.add("RoomData hashcode: " + System.identityHashCode(roomData));
        addressValues.add("Doors hashcode: " + System.identityHashCode(doorCollection));
        addressValues.add("WallImages hashcode: " + System.identityHashCode(wallImages));
        addressValues.add("TileImages hashcode: " + System.identityHashCode(tileImages));
        addressValues.add("DecorationImages hashcode: " + System.identityHashCode(decorationImages));
        addressValues.add("ObstacleImages hashcode: " + System.identityHashCode(obstacleImages));
        return addressValues;
    }
}
