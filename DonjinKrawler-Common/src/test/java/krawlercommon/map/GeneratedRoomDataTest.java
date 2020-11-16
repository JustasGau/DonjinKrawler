package krawlercommon.map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("generated")
public class GeneratedRoomDataTest {
    @Test
    public void testConstructor() {
        RoomData actualRoomData = new RoomData();
        assertEquals(0, actualRoomData.getWalls().size());
        assertEquals(0, actualRoomData.getItems().size());
        assertEquals(0, actualRoomData.getDecorations().size());
        assertEquals(0, actualRoomData.getObstacles().size());
        assertEquals(0, actualRoomData.getEnemies().size());
    }

    @Test
    public void testSetId() {
        RoomData roomData = new RoomData();
        roomData.setId(1);
        assertEquals(1, roomData.getId());
    }

    @Test
    public void testSetLeft() {
        RoomData roomData = new RoomData();
        RoomData roomData1 = new RoomData();
        roomData.setLeft(roomData1);
        assertSame(roomData1, roomData.getLeft());
    }

    @Test
    public void testSetRight() {
        RoomData roomData = new RoomData();
        RoomData roomData1 = new RoomData();
        roomData.setRight(roomData1);
        assertSame(roomData1, roomData.getRight());
    }

    @Test
    public void testSetTop() {
        RoomData roomData = new RoomData();
        RoomData roomData1 = new RoomData();
        roomData.setTop(roomData1);
        assertSame(roomData1, roomData.getTop());
    }

    @Test
    public void testSetBottom() {
        RoomData roomData = new RoomData();
        RoomData roomData1 = new RoomData();
        roomData.setBottom(roomData1);
        assertSame(roomData1, roomData.getBottom());
    }

    @Test
    public void testSetCleared() {
        RoomData roomData = new RoomData();
        roomData.setCleared(true);
        assertTrue(roomData.isCleared());
    }

    @Test
    public void testSetTileTexture() {
        RoomData roomData = new RoomData();
        roomData.setTileTexture(1);
        assertEquals(1, roomData.getTileTexture());
    }

    @Test
    public void testSetRoomType() {
        RoomData roomData = new RoomData();
        roomData.setRoomType(RoomType.BOSS);
        assertEquals(RoomType.BOSS, roomData.getRoomType());
    }

    @Test
    public void testSetAdjacentRooms() {
        RoomData roomData = new RoomData();
        RoomData roomData1 = new RoomData();
        roomData.setAdjacentRooms(roomData1);
        RoomData right = roomData1.getRight();
        assertSame(roomData, right);
        assertSame(roomData1, right.getLeft());
    }

    @Test
    public void testSetAdjacentRooms2() {
        RoomData roomData = new RoomData();
        roomData.setLeft(new RoomData());
        RoomData roomData1 = new RoomData();
        roomData.setAdjacentRooms(roomData1);
        RoomData left = roomData1.getLeft();
        assertSame(roomData, left);
        assertSame(roomData1, left.getRight());
    }

    @Test
    public void testSetAdjacentRooms3() {
        RoomData roomData = new RoomData();
        roomData.setRight(new RoomData());
        RoomData roomData1 = new RoomData();
        roomData1.setAdjacentRooms(roomData);
        RoomData left = roomData.getLeft();
        assertSame(roomData1, left);
        assertSame(roomData, left.getRight());
    }

    @Test
    public void testSetAdjacentRooms4() {
        RoomData roomData = new RoomData();
        roomData.setRight(new RoomData());
        roomData.setLeft(new RoomData());
        RoomData roomData1 = new RoomData();
        roomData.setAdjacentRooms(roomData1);
        RoomData bottom = roomData1.getBottom();
        assertSame(roomData, bottom);
        assertSame(roomData1, bottom.getTop());
    }

    @Test
    public void testSetAdjacentRooms5() {
        RoomData roomData = new RoomData();
        roomData.setLeft(new RoomData());
        RoomData roomData1 = new RoomData();
        roomData1.setLeft(new RoomData());
        roomData.setAdjacentRooms(roomData1);
        RoomData bottom = roomData1.getBottom();
        assertSame(roomData, bottom);
        assertSame(roomData1, bottom.getTop());
    }

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        RoomData actualDeepCopyResult = (new RoomData()).deepCopy();
        assertEquals(0, actualDeepCopyResult.getTileTexture());
        assertNull(actualDeepCopyResult.getRoomType());
        assertEquals(0, actualDeepCopyResult.getWalls().size());
        assertEquals(0, actualDeepCopyResult.getItems().size());
        assertEquals(0, actualDeepCopyResult.getId());
        assertEquals(0, actualDeepCopyResult.getDecorations().size());
        assertEquals(0, actualDeepCopyResult.getObstacles().size());
        assertFalse(actualDeepCopyResult.isCleared());
        assertEquals(0, actualDeepCopyResult.getEnemies().size());
    }

    @Test
    public void testDeepCopy2() throws CloneNotSupportedException {
        RoomData roomData = new RoomData();
        roomData.setBottom(new RoomData());
        RoomData actualDeepCopyResult = roomData.deepCopy();
        assertEquals(0, actualDeepCopyResult.getTileTexture());
        assertNull(actualDeepCopyResult.getRoomType());
        assertEquals(0, actualDeepCopyResult.getWalls().size());
        assertEquals(0, actualDeepCopyResult.getItems().size());
        assertEquals(0, actualDeepCopyResult.getId());
        assertEquals(0, actualDeepCopyResult.getDecorations().size());
        assertEquals(0, actualDeepCopyResult.getObstacles().size());
        assertFalse(actualDeepCopyResult.isCleared());
        assertEquals(0, actualDeepCopyResult.getEnemies().size());
    }

    @Test
    public void testDeepCopy3() throws CloneNotSupportedException {
        RoomData roomData = new RoomData();
        roomData.setTop(new RoomData());
        RoomData actualDeepCopyResult = roomData.deepCopy();
        assertEquals(0, actualDeepCopyResult.getTileTexture());
        assertNull(actualDeepCopyResult.getRoomType());
        assertEquals(0, actualDeepCopyResult.getWalls().size());
        assertEquals(0, actualDeepCopyResult.getEnemies().size());
        assertFalse(actualDeepCopyResult.isCleared());
        assertEquals(0, actualDeepCopyResult.getItems().size());
        assertEquals(0, actualDeepCopyResult.getObstacles().size());
        assertEquals(0, actualDeepCopyResult.getDecorations().size());
        assertEquals(0, actualDeepCopyResult.getId());
    }

    @Test
    public void testDeepCopy4() throws CloneNotSupportedException {
        RoomData roomData = new RoomData();
        roomData.setLeft(new RoomData());
        RoomData actualDeepCopyResult = roomData.deepCopy();
        assertEquals(0, actualDeepCopyResult.getTileTexture());
        assertNull(actualDeepCopyResult.getRoomType());
        assertEquals(0, actualDeepCopyResult.getWalls().size());
        assertEquals(0, actualDeepCopyResult.getItems().size());
        assertEquals(0, actualDeepCopyResult.getEnemies().size());
        assertFalse(actualDeepCopyResult.isCleared());
        assertEquals(0, actualDeepCopyResult.getObstacles().size());
        assertEquals(0, actualDeepCopyResult.getDecorations().size());
        assertEquals(0, actualDeepCopyResult.getId());
    }

    @Test
    public void testDeepCopy5() throws CloneNotSupportedException {
        RoomData roomData = new RoomData();
        roomData.setRight(new RoomData());
        RoomData actualDeepCopyResult = roomData.deepCopy();
        assertEquals(0, actualDeepCopyResult.getTileTexture());
        assertNull(actualDeepCopyResult.getRoomType());
        assertEquals(0, actualDeepCopyResult.getWalls().size());
        assertEquals(0, actualDeepCopyResult.getEnemies().size());
        assertFalse(actualDeepCopyResult.isCleared());
        assertEquals(0, actualDeepCopyResult.getItems().size());
        assertEquals(0, actualDeepCopyResult.getObstacles().size());
        assertEquals(0, actualDeepCopyResult.getDecorations().size());
        assertEquals(0, actualDeepCopyResult.getId());
    }
}

