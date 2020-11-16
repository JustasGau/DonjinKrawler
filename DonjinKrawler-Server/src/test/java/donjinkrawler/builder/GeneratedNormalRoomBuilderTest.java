package donjinkrawler.builder;

import krawlercommon.map.RoomData;
import krawlercommon.map.RoomType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("generated")
public class GeneratedNormalRoomBuilderTest {
    @Test
    public void testConstructor() {
        assertNull((new NormalRoomBuilder()).roomData);
    }

    @Test
    public void testStartNew() {
        NormalRoomBuilder normalRoomBuilder = new NormalRoomBuilder();
        assertSame(normalRoomBuilder, normalRoomBuilder.startNew(1));
        RoomData roomData = ((NormalRoomBuilder) normalRoomBuilder.startNew(1)).roomData;
        assertEquals(RoomType.NORMAL, roomData.getRoomType());
        assertEquals(0, roomData.getWalls().size());
        assertEquals(0, roomData.getItems().size());
        assertEquals(1, roomData.getId());
        assertEquals(0, roomData.getDecorations().size());
        assertEquals(0, roomData.getObstacles().size());
        assertEquals(0, roomData.getEnemies().size());
    }

    @Test
    public void testBuildWalls() {
        NormalRoomBuilder normalRoomBuilder = new NormalRoomBuilder();
        normalRoomBuilder.startNew(1);
        assertSame(normalRoomBuilder, normalRoomBuilder.buildWalls());
    }

    @Test
    public void testBuildItems() {
        NormalRoomBuilder normalRoomBuilder = new NormalRoomBuilder();
        normalRoomBuilder.startNew(1);
        assertSame(normalRoomBuilder, normalRoomBuilder.buildItems());
    }

    @Test
    public void testBuildTiles() {
        NormalRoomBuilder normalRoomBuilder = new NormalRoomBuilder();
        normalRoomBuilder.startNew(1);
        assertSame(normalRoomBuilder, normalRoomBuilder.buildTiles());
    }

    @Test
    public void testBuildObstacles() {
        NormalRoomBuilder normalRoomBuilder = new NormalRoomBuilder();
        normalRoomBuilder.startNew(1);
        assertSame(normalRoomBuilder, normalRoomBuilder.buildObstacles());
    }

    @Test
    public void testBuildDecorations() {
        NormalRoomBuilder normalRoomBuilder = new NormalRoomBuilder();
        normalRoomBuilder.startNew(1);
        assertSame(normalRoomBuilder, normalRoomBuilder.buildDecorations());
    }

    @Test
    public void testBuildEnemies() {
        NormalRoomBuilder normalRoomBuilder = new NormalRoomBuilder();
        normalRoomBuilder.startNew(1);
        assertSame(normalRoomBuilder, normalRoomBuilder.buildEnemies());
    }
}

