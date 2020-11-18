package donjinkrawler.builder;

import krawlercommon.map.RoomData;
import krawlercommon.map.RoomType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@Tag("generated")
public class GeneratedRoomDirectorTest {
    @Test
    public void testConstructRoom() {
        BossRoomBuilder bossRoomBuilder = new BossRoomBuilder();
        RoomDirector roomDirector = new RoomDirector(bossRoomBuilder);
        RoomData actualConstructRoomResult = roomDirector.constructRoom(1);
        assertSame(bossRoomBuilder.roomData, actualConstructRoomResult);
        assertEquals(100, actualConstructRoomResult.getTileTexture());
        assertEquals(RoomType.BOSS, actualConstructRoomResult.getRoomType());
        assertEquals(6, actualConstructRoomResult.getWalls().size());
        assertEquals(0, actualConstructRoomResult.getItems().size());
        assertEquals(1, actualConstructRoomResult.getId());
        assertEquals(1, actualConstructRoomResult.getDecorations().size());
        assertEquals(2, actualConstructRoomResult.getObstacles().size());
        assertEquals(1, actualConstructRoomResult.getEnemies().size());
        assertSame(actualConstructRoomResult, ((BossRoomBuilder) roomDirector.roomBuilder).roomData);
    }
}

