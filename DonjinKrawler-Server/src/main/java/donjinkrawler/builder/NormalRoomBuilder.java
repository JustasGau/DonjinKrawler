package donjinkrawler.builder;

import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.EnemyGenerator;
import krawlercommon.enemies.small.SmallEnemyFactory;
import krawlercommon.items.ItemLocationData;
import krawlercommon.items.ItemGenerator;
import krawlercommon.map.Obstacle;
import krawlercommon.map.ObstacleType;
import krawlercommon.map.RoomType;
import krawlercommon.map.Wall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NormalRoomBuilder extends RoomBuilder {

    private Map<Integer, Pair> wallPrefabs;
    private final int MAX_WALL_COORD = 300;
    private final int MIN_WALL_COORD = 100;

    public NormalRoomBuilder() {
        super();
        wallPrefabs = generateWallPrefabs();
    }

    @Override
    public RoomBuilder startNew(int id) {
        super.startNew(id);
        roomData.setRoomType(RoomType.BOSS);
        return this;
    }

    @Override
    public RoomBuilder buildWalls() {
        super.buildWalls();
        generateRandomWalls();
        return this;
    }

    private void generateRandomWalls() {
        // TODO: rework because sometimes it may block doors / players
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int wallPrefabNumber = random.nextInt(wallPrefabs.size());
            Wall wall = new Wall(random.nextInt(MAX_WALL_COORD) + MIN_WALL_COORD,
                    random.nextInt(MAX_WALL_COORD) + MIN_WALL_COORD,
                    wallPrefabs.get(wallPrefabNumber).width,
                    wallPrefabs.get(wallPrefabNumber).height);
            roomData.getWalls().add(wall);
        }
    }

    @Override
    public RoomBuilder buildItems() {
        ItemLocationData item = ItemGenerator.generateRandomItem(roomData.getItems().size());
        roomData.getItems().put(roomData.getItems().size(), item);
        return this;
    }

    @Override
    public RoomBuilder buildTiles() {
        Random rand = new Random();
        roomData.setTileTexture(rand.nextInt(3) + 1);
        return this;
    }

    @Override
    public RoomBuilder buildObstacles() {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            Obstacle obstacle = new Obstacle();
            obstacle.setX(rand.nextInt(350) + 30);
            obstacle.setY(rand.nextInt(350) + 30);
            obstacle.setWidth(35);
            obstacle.setHeight(35);
            obstacle.setObstacleType(ObstacleType.getTypeByNumber(String.valueOf(rand.nextInt(3) + 1)));
            roomData.getObstacles().add(obstacle);
            // TODO: check if wall is here already
        }
        return this;
    }

    @Override
    public RoomBuilder buildDecorations() {
        super.generateRandomDecorations(1, 2);
        return this;
    }

    @Override
    RoomBuilder buildEnemies() {
        EnemyGenerator smallEnemyGenerator = new EnemyGenerator(new SmallEnemyFactory());
        ArrayList<Enemy> smallEnemies = smallEnemyGenerator.generateRandomEnemies(5);
        roomData.getEnemies().addAll(smallEnemies);
        return this;
    }


    private Map<Integer, Pair> generateWallPrefabs() {
        wallPrefabs = new HashMap<>();
        wallPrefabs.put(wallPrefabs.size(), new Pair(20, 20));
        wallPrefabs.put(wallPrefabs.size(), new Pair(20, 40));
        wallPrefabs.put(wallPrefabs.size(), new Pair(20, 60));
        wallPrefabs.put(wallPrefabs.size(), new Pair(40, 20));
        wallPrefabs.put(wallPrefabs.size(), new Pair(60, 20));
        return wallPrefabs;
    }

    private class Pair {
        private int width;
        private int height;

        public Pair(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
