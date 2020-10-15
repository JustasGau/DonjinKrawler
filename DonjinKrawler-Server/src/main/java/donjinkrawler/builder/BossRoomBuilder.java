package donjinkrawler.builder;

import krawlercommon.map.RoomType;

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
}
