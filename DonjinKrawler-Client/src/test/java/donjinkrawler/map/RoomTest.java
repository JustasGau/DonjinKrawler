package donjinkrawler.map;

import donjinkrawler.items.DamagePotion;
import krawlercommon.items.ArmorData;
import krawlercommon.items.DamagePotionData;
import krawlercommon.map.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    public void testRoomDraw() {
        RoomData roomData = mock(RoomData.class);
        when(roomData.getTileTexture()).thenReturn(1);
        when(roomData.getId()).thenReturn(69);
        when(roomData.getWalls()).thenReturn(new ArrayList<>(List.of(new Wall(10, 10, 10, 10))));
        when(roomData.getDecorations()).thenReturn(new ArrayList<>(List.of(new Decoration(10, 10, 10, 10))));
        Obstacle obstacle = new Obstacle(20, 10, 10, 10);
        obstacle.setObstacleType(ObstacleType.LAVA);
        when(roomData.getObstacles()).thenReturn(new ArrayList<>(List.of(obstacle)));

        Graphics2D graphics = mock(Graphics2D.class);
        Room room = new Room(roomData);
        room.getDoors().add(new Door());
        room.draw(graphics);
        Mockito.verify(graphics).setColor(Color.RED);
        Mockito.verify(graphics).drawString("69", 250, 250);
        Mockito.verify(graphics).fill(new Rectangle(0, 0, 20, 20));
        Mockito.verify(graphics).fill(new Rectangle(10, 10, 10, 10));
        Mockito.verify(graphics).fill(new Rectangle(20, 10, 10, 10));
    }

    @Test
    public void testRemoveItem() {
        RoomData roomData = mock(RoomData.class);
        Room room = new Room(roomData);
        DamagePotionData mockDP = mock(DamagePotionData.class);
        room.getItems().put(1, new DamagePotion(mockDP));
        assertEquals(1, room.getItems().size());
        room.removeItem(1);
        assertEquals(0, room.getItems().size());
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
