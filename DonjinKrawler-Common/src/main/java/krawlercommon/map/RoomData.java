package krawlercommon.map;

import krawlercommon.KrawlerCloneable;
import krawlercommon.enemies.Enemy;
import krawlercommon.items.ItemLocationData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomData implements KrawlerCloneable, Serializable {
    private int id;
    private boolean cleared;
    private RoomData left;
    private RoomData right;
    private RoomData top;
    private RoomData bottom;
    private ArrayList<Wall> walls;
    private HashMap<Integer, ItemLocationData> items;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Decoration> decorations;
    private ArrayList<Enemy> enemies;
    private int tileTexture;
    private RoomType roomType;

    public RoomData() {
        enemies = new ArrayList<>();
        walls = new ArrayList<>();
        obstacles = new ArrayList<>();
        items = new HashMap<>();
        decorations = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoomData getLeft() {
        return left;
    }

    public void setLeft(RoomData left) {
        this.left = left;
    }

    public RoomData getRight() {
        return right;
    }

    public void setRight(RoomData right) {
        this.right = right;
    }

    public RoomData getTop() {
        return top;
    }

    public void setTop(RoomData top) {
        this.top = top;
    }

    public RoomData getBottom() {
        return bottom;
    }

    public void setBottom(RoomData bottom) {
        this.bottom = bottom;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public HashMap<Integer, ItemLocationData> getItems() {
        return items;
    }

    public int getTileTexture() {
        return tileTexture;
    }

    public void setTileTexture(int tileTexture) {
        this.tileTexture = tileTexture;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public ArrayList<Decoration> getDecorations() {
        return decorations;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setAdjacentRooms(RoomData other) {
        if (left == null && other.right == null) {
            setLeft(other);
            other.setRight(this);
        } else if (right == null && other.left == null) {
            setRight(other);
            other.setLeft(this);
        } else if (top == null && other.bottom == null) {
            setTop(other);
            other.setBottom(this);
        } else if (bottom == null && other.top == null) {
            setBottom(other);
            other.setTop(this);
        }
    }

    @Override
    public RoomData deepCopy() throws CloneNotSupportedException {
        RoomData clonedData = new RoomData();
        clonedData.setId(id);
        clonedData.setCleared(cleared);
        clonedData.setTileTexture(tileTexture);
        if (top != null) {
            clonedData.setTop(top.clone());
        }
        if (bottom != null) {
            clonedData.setBottom(bottom.clone());
        }
        if (left != null) {
            clonedData.setLeft(left.clone());
        }
        if (right != null) {
            clonedData.setRight(right.clone());
        }
        clonedData.walls = new ArrayList<>();
        for (Wall wall : walls) {
            Wall clone = wall.clone();
            clonedData.walls.add(clone);
        }
        clonedData.items = new HashMap<>();
        for (Map.Entry<Integer, ItemLocationData> entry : items.entrySet()) {
            clonedData.getItems().put(entry.getKey(), entry.getValue().clone());
        }
        clonedData.obstacles = new ArrayList<>();
        for (Obstacle obstacle : obstacles) {
            Obstacle clone = obstacle.clone();
            clonedData.obstacles.add(clone);
        }
        clonedData.decorations = new ArrayList<>();
        for (Decoration decoration : decorations) {
            Decoration clone = decoration.clone();
            clonedData.decorations.add(clone);
        }
        clonedData.enemies = new ArrayList<>();
        for (Enemy enemy : enemies) {
            Enemy clone = enemy.clone();
            clonedData.enemies.add(clone);
        }
        clonedData.roomType = roomType;
        return clonedData;
    }

    @Override
    public RoomData clone() throws CloneNotSupportedException {
        return (RoomData) super.clone();
    }

    public void removeItem(Integer itemId) {
        this.items.remove(itemId);
    }
}
