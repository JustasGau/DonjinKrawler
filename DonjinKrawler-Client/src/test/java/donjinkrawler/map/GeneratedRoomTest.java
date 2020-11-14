package donjinkrawler.map;

import krawlercommon.map.DoorDirection;
import krawlercommon.map.RoomData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratedRoomTest {
    @Test
    public void testConstructor() {
        Room actualRoom = new Room(new RoomData());
        assertEquals(0, actualRoom.getItems().size());
        assertEquals(0, actualRoom.getDecorations().size());
        assertEquals(0, actualRoom.getDoors().size());
    }

    @Test
    public void testUpdate() {
        assertNull((new Room(new RoomData())).update(null));
    }

    @Test
    public void testGetRoomFromDirection() {
        assertNull((new Room(new RoomData())).getRoomFromDirection(DoorDirection.TOP));
        assertNull((new Room(new RoomData())).getRoomFromDirection(DoorDirection.BOTTOM));
        assertNull((new Room(new RoomData())).getRoomFromDirection(DoorDirection.LEFT));
        assertNull((new Room(new RoomData())).getRoomFromDirection(DoorDirection.RIGHT));
    }

    @Test
    public void testGetWalls() {
        assertEquals(0, (new Room(new RoomData())).getWalls().size());
    }

    @Test
    public void testGetObstacles() {
        assertEquals(0, (new Room(new RoomData())).getObstacles().size());
    }

    @Test
    public void testGetDecorations() {
        assertEquals(0, (new Room(new RoomData())).getDecorations().size());
    }

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        Room actualDeepCopyResult = (new Room(new RoomData())).deepCopy();
        assertEquals(0, actualDeepCopyResult.getItems().size());
        assertEquals(0, actualDeepCopyResult.getDecorations().size());
        assertEquals(0, actualDeepCopyResult.getObstacles().size());
        RoomData roomData = actualDeepCopyResult.getRoomData();
        assertEquals(0, actualDeepCopyResult.getDoors().size());
        assertEquals(0, actualDeepCopyResult.getWalls().size());
        assertEquals(0, roomData.getItems().size());
        assertEquals(0, roomData.getId());
        assertEquals(0, roomData.getEnemies().size());
        assertEquals(0, roomData.getTileTexture());
        assertFalse(roomData.isCleared());
        assertNull(roomData.getRoomType());
    }

    @Test
    public void testSetRoomData() {
        Room room = new Room(new RoomData());
        RoomData roomData = new RoomData();
        room.setRoomData(roomData);
        assertSame(roomData, room.getRoomData());
    }

    @Test
    public void testGetAddressValues() {
        assertEquals(7, (new Room(new RoomData())).getAddressValues().size());
    }
}

