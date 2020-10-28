package map;

import donjinkrawler.map.Room;
import krawlercommon.items.ArmorData;
import krawlercommon.map.RoomData;
import krawlercommon.map.RoomType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    @Test
    public void testShallowCopy() throws CloneNotSupportedException {
        Room room = generateRoom();
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
        Room room = generateRoom();
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

    @Test
    public void testShallowCopyHashCode() throws CloneNotSupportedException {
        Room room = generateRoom();
        Room clone = room.clone();
        System.out.println("OLD ROOM");
        room.getAddressValues().forEach(System.out::println);
        System.out.println("NEW ROOM");
        clone.getAddressValues().forEach(System.out::println);
        assertNotEquals(System.identityHashCode(room), System.identityHashCode(clone));
        assertEquals(System.identityHashCode(room.getRoomData()), System.identityHashCode(clone.getRoomData()));
        assertEquals(System.identityHashCode(room.getDoors()), System.identityHashCode(clone.getDoors()));
    }

    @Test
    public void testDeepCopyHashCode() throws CloneNotSupportedException {
        Room room = generateRoom();
        Room clone = room.deepCopy();
        System.out.println("OLD ROOM");
        room.getAddressValues().forEach(System.out::println);
        System.out.println("NEW ROOM");
        clone.getAddressValues().forEach(System.out::println);
        assertNotEquals(System.identityHashCode(room), System.identityHashCode(clone));
        assertNotEquals(System.identityHashCode(room.getRoomData()), System.identityHashCode(clone.getRoomData()));
        assertNotEquals(System.identityHashCode(room.getDoors()), System.identityHashCode(clone.getDoors()));
        assertNotEquals(
                System.identityHashCode(room.getDecorations()), System.identityHashCode(clone.getDecorations()));
        assertNotEquals(System.identityHashCode(room.getWalls()), System.identityHashCode(clone.getWalls()));
        assertNotEquals(System.identityHashCode(room.getItems()), System.identityHashCode(clone.getItems()));
        assertNotEquals(System.identityHashCode(room.getItems()), System.identityHashCode(clone.getItems()));
        assertNotEquals(System.identityHashCode(room.getRoomData().getItems().get(1)),
                System.identityHashCode(clone.getRoomData().getItems().get(1)));
    }

    private Room generateRoom() {
        RoomData topRoom = new RoomData();
        topRoom.setRoomType(RoomType.NORMAL);
        RoomData roomData = new RoomData();
        roomData.setId(1);
        roomData.setAdjacentRooms(topRoom);
        roomData.setRoomType(RoomType.BOSS);
        roomData.setTileTexture(5);
        roomData.getItems().put(1, new ArmorData());
        return new Room(roomData);
    }

}
