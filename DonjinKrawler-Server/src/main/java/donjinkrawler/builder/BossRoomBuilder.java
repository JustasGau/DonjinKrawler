package donjinkrawler.builder;

import krawlercommon.enemies.Boss;
import krawlercommon.map.Decoration;
import krawlercommon.map.Obstacle;
import krawlercommon.map.RoomType;
import krawlercommon.map.Wall;
import krawlercommon.map.obstacles.Lava;

public class BossRoomBuilder extends RoomBuilder {

    public BossRoomBuilder() {
        super();
    }

    @Override
    public RoomBuilder startNew(int id) {
        super.startNew(id);
        roomData.setRoomType(RoomType.BOSS);
        return this;
    }

    @Override
    public RoomBuilder buildWalls() {
        super.buildWalls("100");
        Wall wall = new Wall(20, 160, 160, 20, "300");
        roomData.getWalls().add(wall);
        wall = new Wall(179, 20, 4, 160, "300-side");
        roomData.getWalls().add(wall);
        return this;
    }

    @Override
    public RoomBuilder buildItems() {
        return this;
    }

    @Override
    public RoomBuilder buildTiles() {
        roomData.setTileTexture(100);
        return this;
    }

    @Override
    public RoomBuilder buildObstacles() {
        Obstacle obstacle = new Lava();
        obstacle.setX(50);
        obstacle.setY(380);
        obstacle.setWidth(150);
        obstacle.setHeight(50);
        roomData.getObstacles().add(obstacle);

        obstacle = new Lava();
        obstacle.setX(300);
        obstacle.setY(100);
        obstacle.setWidth(150);
        obstacle.setHeight(50);
        roomData.getObstacles().add(obstacle);

        return this;
    }

    @Override
    public RoomBuilder buildDecorations() {
        Decoration decoration = new Decoration();
        decoration.setX(40);
        decoration.setY(40);
        decoration.setImageNumber(300);
        decoration.setHeight(128);
        decoration.setWidth(128);
        roomData.getDecorations().add(decoration);
        return this;
    }

    @Override
    RoomBuilder buildEnemies() {
        Boss boss = new Boss(roomData.getDecorations(), roomData.getWalls());
        roomData.getEnemies().add(boss);
        return this;
    }
}
