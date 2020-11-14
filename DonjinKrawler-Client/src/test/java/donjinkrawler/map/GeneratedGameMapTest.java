package donjinkrawler.map;

import krawlercommon.map.RoomData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GeneratedGameMapTest {
    @Test
    public void testConstructor() {
        Room room = new Room(new RoomData());
        assertSame(room, (new GameMap(room)).getCurrentRoom());
    }

    @Test
    public void testUpdate() {
        assertNull((new GameMap(new Room(new RoomData()))).update(null));
    }

    @Test
    public void testUpdate2() {
        GameMap gameMap = new GameMap(null);
        gameMap.setCurrentRoom(new Room(new RoomData()));
        assertNull(gameMap.update(null));
    }

    @Test
    public void testSetCurrentRoom() {
        GameMap gameMap = new GameMap(new Room(new RoomData()));
        Room room = new Room(new RoomData());
        gameMap.setCurrentRoom(room);
        assertSame(room, gameMap.getCurrentRoom());
    }
}

