package map;

import donjinkrawler.map.Room;
import krawlercommon.map.RoomData;
import krawlercommon.map.RoomType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    @Test
    public void testShallowCopy() throws CloneNotSupportedException {
        RoomData topRoom = new RoomData();
        topRoom.setRoomType(RoomType.NORMAL);
        RoomData roomData = new RoomData();
        roomData.setId(1);
        roomData.setAdjacentRooms(topRoom);
        roomData.setRoomType(RoomType.BOSS);
        roomData.setTileTexture(5);
        Room room = new Room(roomData);
        Room clone = room.clone();
        for (int i = 0; i < room.getDoors().size(); i++) {
            assertEquals(room.getDoors().get(i), clone.getDoors().get(i));
        }
        assertEquals(room.getRoomData().getTop(), clone.getRoomData().getTop());
        assertEquals(RoomType.BOSS, clone.getRoomData().getRoomType());
        assertEquals(1, clone.getRoomData().getId());
    }

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        RoomData topRoom = new RoomData();
        topRoom.setRoomType(RoomType.NORMAL);
        RoomData roomData = new RoomData();
        roomData.setId(1);
        roomData.setAdjacentRooms(topRoom);
        roomData.setRoomType(RoomType.BOSS);
        roomData.setTileTexture(5);
        Room room = new Room(roomData);
        Room clone = room.deepCopy();
        for (int i = 0; i < room.getDoors().size(); i++) {
            assertNotEquals(room.getDoors().get(i), clone.getDoors().get(i));
            assertEquals(room.getDoors().get(i).getDirection(), clone.getDoors().get(i).getDirection());
            assertEquals(room.getDoors().get(i).getX(), clone.getDoors().get(i).getX());
            assertEquals(room.getDoors().get(i).getY(), clone.getDoors().get(i).getY());
        }
        assertEquals(room.getRoomData().getTop(), clone.getRoomData().getTop());
        assertEquals(RoomType.BOSS, clone.getRoomData().getRoomType());
        assertEquals(1, clone.getRoomData().getId());
    }
}
