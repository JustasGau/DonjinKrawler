package donjinkrawler.builder;

import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.EnemyGenerator;
import krawlercommon.enemies.big.BigEnemyFactory;
import krawlercommon.enemies.small.SmallEnemyFactory;
import krawlercommon.items.ItemGenerator;
import krawlercommon.items.ItemLocationData;
import krawlercommon.map.Obstacle;
import krawlercommon.map.RoomType;
import krawlercommon.map.Wall;
import krawlercommon.map.obstacles.Lava;
import krawlercommon.map.obstacles.Slime;
import krawlercommon.map.obstacles.Spikes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NormalRoomBuilder extends RoomBuilder {

    private final int MAX_WALL_COORD = 270;
    private final int MIN_WALL_COORD = 130;
    private Map<Integer, Pair> wallPrefabs;
    private Random rand = new Random();

    public NormalRoomBuilder() {
        super();
        wallPrefabs = generateWallPrefabs();
    }

    @Override
    public RoomBuilder startNew(int id) {
        super.startNew(id);
        roomData.setRoomType(RoomType.NORMAL);
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
        for (int i = 0; i < 5; i++) {
            int x = rand.nextInt(MAX_WALL_COORD) + MIN_WALL_COORD;
            int y = rand.nextInt(MAX_WALL_COORD) + MIN_WALL_COORD;
            while ((x > 175 && x < 235) || (y > 175 && y < 235)) {
                x = rand.nextInt(MAX_WALL_COORD) + MIN_WALL_COORD;
                y = rand.nextInt(MAX_WALL_COORD) + MIN_WALL_COORD;
            }
            int wallPrefabNumber = rand.nextInt(wallPrefabs.size());
            Wall wall = new Wall(x, y,
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
        roomData.setTileTexture(rand.nextInt(3) + 1);
        return this;
    }

    @Override
    public RoomBuilder buildObstacles() {
        for (int i = 0; i < 5; i++) {
            String typeNumber = String.valueOf(rand.nextInt(3) + 1);
            Obstacle obstacle;
            if (typeNumber.equals("1")) {
                obstacle = new Lava();
            } else if (typeNumber.equals("2")) {
                obstacle = new Spikes();
            } else {
                obstacle = new Slime();
            }

            obstacle.setX(rand.nextInt(350) + 30);
            obstacle.setY(rand.nextInt(350) + 30);
            obstacle.setWidth(35);
            obstacle.setHeight(35);
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
        EnemyGenerator smallEnemyGenerator = new EnemyGenerator(new SmallEnemyFactory(this.roomData.getDecorations(), this.roomData.getWalls()));
        ArrayList<Enemy> smallEnemies = smallEnemyGenerator.generateRandomEnemies(5);
        EnemyGenerator bigEnemyGenerator = new EnemyGenerator(new BigEnemyFactory(this.roomData.getDecorations(), this.roomData.getWalls()));
        ArrayList<Enemy> bigEnemies = bigEnemyGenerator.generateRandomEnemies(rand.nextInt(3));
        roomData.getEnemies().addAll(smallEnemies);
        roomData.getEnemies().addAll(bigEnemies);
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

    private static class Pair {
        private final int width;
        private final int height;

        public Pair(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
