package donjinkrawler.builder;

import krawlercommon.map.RoomData;
import krawlercommon.map.RoomType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GeneratedItemRoomBuilderTest {
    @Test
    public void testStartNew() {
        ItemRoomBuilder itemRoomBuilder = new ItemRoomBuilder();
        assertSame(itemRoomBuilder, itemRoomBuilder.startNew(1));
        RoomData roomData = ((ItemRoomBuilder) itemRoomBuilder.startNew(1)).roomData;
        assertEquals(RoomType.ITEM, roomData.getRoomType());
        assertEquals(0, roomData.getWalls().size());
        assertEquals(0, roomData.getItems().size());
        assertEquals(1, roomData.getId());
        assertEquals(0, roomData.getDecorations().size());
        assertEquals(0, roomData.getObstacles().size());
        assertEquals(0, roomData.getEnemies().size());
    }

    @Test
    public void testBuildWalls() {
        ItemRoomBuilder itemRoomBuilder = new ItemRoomBuilder();
        itemRoomBuilder.startNew(1);
        assertSame(itemRoomBuilder, itemRoomBuilder.buildWalls());
    }

    @Test
    public void testBuildItems() {
        ItemRoomBuilder itemRoomBuilder = new ItemRoomBuilder();
        itemRoomBuilder.startNew(1);
        assertSame(itemRoomBuilder, itemRoomBuilder.buildItems());
    }

    @Test
    public void testBuildTiles() {
        ItemRoomBuilder itemRoomBuilder = new ItemRoomBuilder();
        itemRoomBuilder.startNew(1);
        assertSame(itemRoomBuilder, itemRoomBuilder.buildTiles());
    }

    @Test
    public void testBuildObstacles() {
        ItemRoomBuilder itemRoomBuilder = new ItemRoomBuilder();
        assertSame(itemRoomBuilder, itemRoomBuilder.buildObstacles());
    }

    @Test
    public void testBuildDecorations() {
        ItemRoomBuilder itemRoomBuilder = new ItemRoomBuilder();
        itemRoomBuilder.startNew(1);
        assertSame(itemRoomBuilder, itemRoomBuilder.buildDecorations());
    }

    @Test
    public void testBuildEnemies() {
        ItemRoomBuilder itemRoomBuilder = new ItemRoomBuilder();
        assertSame(itemRoomBuilder, itemRoomBuilder.buildEnemies());
    }
}

