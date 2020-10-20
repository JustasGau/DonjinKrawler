package donjinkrawler.builder;

import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.EnemyGenerator;
import krawlercommon.enemies.big.BigEnemyFactory;
import krawlercommon.map.RoomType;

import java.util.ArrayList;

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
        super.buildWalls(100);
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
        return this;
    }

    @Override
    public RoomBuilder buildDecorations() {
        return this;
    }

    @Override
    RoomBuilder buildEnemies() {
        EnemyGenerator bigEnemyGenerator = new EnemyGenerator(new BigEnemyFactory());
        ArrayList<Enemy> bigEnemies = bigEnemyGenerator.generateRandomEnemies(1);
        roomData.getEnemies().addAll(bigEnemies);
        return this;
    }
}
