package donjinkrawler.builder;

import krawlercommon.map.RoomData;
import krawlercommon.map.RoomType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GeneratedBossRoomBuilderTest {
    @Test
    public void testStartNew() {
        BossRoomBuilder bossRoomBuilder = new BossRoomBuilder();
        assertSame(bossRoomBuilder, bossRoomBuilder.startNew(1));
        RoomData roomData = ((BossRoomBuilder) bossRoomBuilder.startNew(1)).roomData;
        assertEquals(RoomType.BOSS, roomData.getRoomType());
        assertEquals(0, roomData.getWalls().size());
        assertEquals(0, roomData.getItems().size());
        assertEquals(1, roomData.getId());
        assertEquals(0, roomData.getDecorations().size());
        assertEquals(0, roomData.getObstacles().size());
        assertEquals(0, roomData.getEnemies().size());
    }

    @Test
    public void testBuildWalls() {
        BossRoomBuilder bossRoomBuilder = new BossRoomBuilder();
        bossRoomBuilder.startNew(1);
        assertSame(bossRoomBuilder, bossRoomBuilder.buildWalls());
    }

    @Test
    public void testBuildItems() {
        BossRoomBuilder bossRoomBuilder = new BossRoomBuilder();
        assertSame(bossRoomBuilder, bossRoomBuilder.buildItems());
    }

    @Test
    public void testBuildTiles() {
        BossRoomBuilder bossRoomBuilder = new BossRoomBuilder();
        bossRoomBuilder.startNew(1);
        assertSame(bossRoomBuilder, bossRoomBuilder.buildTiles());
    }

    @Test
    public void testBuildObstacles() {
        BossRoomBuilder bossRoomBuilder = new BossRoomBuilder();
        bossRoomBuilder.startNew(1);
        assertSame(bossRoomBuilder, bossRoomBuilder.buildObstacles());
    }

    @Test
    public void testBuildDecorations() {
        BossRoomBuilder bossRoomBuilder = new BossRoomBuilder();
        bossRoomBuilder.startNew(1);
        assertSame(bossRoomBuilder, bossRoomBuilder.buildDecorations());
    }

    @Test
    public void testBuildEnemies() {
        BossRoomBuilder bossRoomBuilder = new BossRoomBuilder();
        bossRoomBuilder.startNew(1);
        assertSame(bossRoomBuilder, bossRoomBuilder.buildEnemies());
    }
}

