package krawlercommon.map;


import krawlercommon.items.ItemData;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: refactor to RoomData class for things that are networked and Room class for things
public class RoomData {
    // Item list
    // Walls object?
    // Enemies?
    private int id;
    private boolean cleared;
    private RoomData left;
    private RoomData right;
    private RoomData top;
    private RoomData bottom;
    private final ArrayList<Wall> walls;
    private final HashMap<Integer, ItemData> items;
    private final ArrayList<Obstacle> obstacles;
    private final ArrayList<Decoration> decorations;
    private int tileTexture;
    private RoomType roomType;

    public RoomData() {
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

    public HashMap<Integer, ItemData> getItems() {
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

}
