package donjinkrawler.builder;

import krawlercommon.map.RoomData;

public class RoomDirector {

    RoomBuilder roomBuilder;

    public RoomDirector(RoomBuilder roomBuilder) {
        this.roomBuilder = roomBuilder;
    }

    public RoomData constructRoom(int id) {
        roomBuilder.startNew(id).buildWalls();
        roomBuilder.buildTiles();
        roomBuilder.buildItems();
        roomBuilder.buildObstacles();
        roomBuilder.buildDecorations();
        roomBuilder.buildEnemies();
        return roomBuilder.build();
    }
}
