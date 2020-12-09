package donjinkrawler.builder;

import krawlercommon.map.Decoration;
import krawlercommon.map.RoomData;
import krawlercommon.map.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class RoomBuilder {
    private static final int MAP_WIDTH = 500;
    private static final int MAP_HEIGHT = 500;
    RoomData roomData;

    public RoomBuilder startNew(int id) {
        roomData = new RoomData();
        roomData.setId(id);
        return this;
    }

    public RoomBuilder buildWalls() {
        return buildWalls("");
    }

    public RoomBuilder buildWalls(String texture) {
        buildWallsAroundMap(texture);
        return this;
    }

    private void buildWallsAroundMap(String texture) {
        // TODO: refactor to configurable window size
        Wall wall = new Wall(0, 0, 20, MAP_HEIGHT - 20, texture); // LEFT WALL
        roomData.getWalls().add(wall);
        wall = new Wall(20, MAP_HEIGHT - 40, MAP_WIDTH - 20, 20, texture); // BOTTOM WALL
        roomData.getWalls().add(wall);
        wall = new Wall(20, 0, MAP_WIDTH - 20, 20, texture); // TOP WALL
        roomData.getWalls().add(wall);
        wall = new Wall(MAP_WIDTH - 20, 20, 20, MAP_HEIGHT - 60, texture); // RIGHT WALL
        roomData.getWalls().add(wall);
    }

    protected void generateRandomDecorations(int fromImage, int toImage) {
        Random rand = new Random();
        int corner;
        Bounds bound;
        List<Bounds> pairList = getPairList();
        for (int i = 0; i < 5; i++) {
            corner = rand.nextInt(4);
            bound = pairList.get(corner);
            int decorationNumber = rand.nextInt((toImage - fromImage) + 1) + fromImage;
            Decoration decoration = constructDecoration(rand, bound, decorationNumber);
            roomData.getDecorations().add(decoration);
        }
    }

    private List<Bounds> getPairList() {
        List<Bounds> pairList = new ArrayList<>();
        pairList.add(new Bounds(40, 150, 40, 150));
        pairList.add(new Bounds(40, 150, 330, 460));
        pairList.add(new Bounds(330, 460, 40, 150));
        pairList.add(new Bounds(330, 460, 330, 460));
        return pairList;
    }

    private Decoration constructDecoration(Random rand, Bounds bound, int decorationNumber) {
        Decoration decoration = new Decoration();
        decoration.setX(rand.nextInt(bound.toX - bound.fromX) + bound.fromX);
        decoration.setY(rand.nextInt(bound.toY - bound.fromY) + bound.fromY);
        decoration.setWidth(20);
        decoration.setHeight(20);
        decoration.setImageNumber(decorationNumber);
        return decoration;
    }

    abstract RoomBuilder buildItems();

    abstract RoomBuilder buildTiles();

    abstract RoomBuilder buildObstacles();

    abstract RoomBuilder buildDecorations();

    abstract RoomBuilder buildEnemies();

    public RoomData build() {
        return roomData;
    }

    private class Bounds {
        protected int fromX;
        protected int toX;
        protected int fromY;
        protected int toY;

        protected Bounds(int fromX, int toX, int fromY, int toY) {
            this.fromX = fromX;
            this.toX = toX;
            this.fromY = fromY;
            this.toY = toY;
        }
    }
}
