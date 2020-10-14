package donjinkrawler.builder;

import krawlercommon.items.ItemData;
import krawlercommon.items.ItemGenerator;
import krawlercommon.map.RoomType;

public class ItemRoomBuilder extends RoomBuilder {

    public ItemRoomBuilder() {
        super();
    }

    @Override
    public RoomBuilder startNew(int id) {
        super.startNew(id);
        roomData.setRoomType(RoomType.ITEM);
        return this;
    }

    @Override
    public RoomBuilder buildWalls() {
        return super.buildWalls();
    }

    @Override
    public RoomBuilder buildItems() {
        int[] centerpoints = {40, 173, 306, 440};
        for (int i = 0; i < centerpoints.length; i++) {
            ItemData itemData = ItemGenerator.generateRandomItem(roomData.getItems().size(), 210, centerpoints[i]);
            roomData.getItems().put(roomData.getItems().size(), itemData);
            itemData  = ItemGenerator.generateRandomItem(roomData.getItems().size(), 310, centerpoints[i]);
            roomData.getItems().put(roomData.getItems().size(), itemData);
        }
        return this;
    }

    @Override
    public RoomBuilder buildTiles() {
        roomData.setTileTexture(200);
        return this;
    }

    @Override
    public RoomBuilder buildObstacles() {
        return this;
    }

    @Override
    public RoomBuilder buildDecorations() {
        super.generateRandomDecorations(200, 202);
        return this;
    }
}
