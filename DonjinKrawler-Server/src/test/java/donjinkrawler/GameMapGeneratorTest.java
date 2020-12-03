package donjinkrawler;

import krawlercommon.map.RoomData;
import krawlercommon.map.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapGeneratorTest {

    private static final int GAME_MAP_SIZE = 9;

    @Test
    public void executeMapGeneration() {
        GameMapGenerator generator = new GameMapGenerator(GAME_MAP_SIZE);
        List<String> stringList = generator.generate();
        Map<Integer, RoomData> gameMap = generator.generateRoomsFromString(stringList);
    }

    @ParameterizedTest
    @ValueSource(ints = {9, 11, 13, 15, 17})
    public void testRoomLoopingNew(int gameSize) {
        GameMapGenerator generator = new GameMapGenerator(gameSize);
        for (int i = 0; i < 1000; i++) {
            List<String> stringList = generator.generate();
            assertFalse(areRoomsLoopingNew(stringList), "rooms are looping, iteration " + i);
            assertTrue(areAllRoomsConnected(stringList), "all rooms should be connected, iteration " + i);
        }
    }


    private boolean areRoomsLoopingNew(List<String> stringList) {
        for (int i = 0; i < stringList.size(); i++) {
            if (stringList.get(i).charAt(i) == '1') {
                return true;
            }
        }
        return false;
    }

    private boolean areAllRoomsConnected(List<String> stringList) {
        return stringList.stream().allMatch(s -> s.chars().filter(c -> c == '1').count() > 0);
    }

    @Test
    public void testAdjacencyMatrixConversionToRooms() {
        GameMapGenerator generator = new GameMapGenerator(5);
        List<String> stringList = List.of(
                "01001",
                "10101",
                "01010",
                "00101",
                "11010"
        );
        Map<Integer, RoomData> rooms = generator.generateRoomsFromString(stringList);
        assertNotNull(rooms);
        assertNotNull(rooms.get(0));
        assertNotNull(rooms.get(0).getLeft());
        assertNotNull(rooms.get(0).getLeft().getRight());
        assertNotNull(rooms.get(0).getRight());
        assertNotNull(rooms.get(0).getRight().getLeft());
        assertNotEquals(rooms.get(0).getLeft(), rooms.get(0).getRight());
        assertNotEquals(rooms.get(0).getRight(), rooms.get(0).getLeft());
    }

    @Test
    public void testOnlyOneBossRoomIsGenerated() {
        GameMapGenerator generator = new GameMapGenerator(10);
        List<String> stringList = generator.generate();
        Map<Integer, RoomData> rooms = generator.generateRoomsFromString(stringList);
        assertEquals(1, rooms.values().stream().filter(r -> r.getRoomType().equals(RoomType.BOSS)).count());
    }
}
