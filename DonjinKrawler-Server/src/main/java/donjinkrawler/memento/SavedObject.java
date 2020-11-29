package donjinkrawler.memento;

import krawlercommon.map.RoomData;

import java.util.HashMap;

public class SavedObject {
    HashMap<Integer, RoomData>  rooms;
    int currentRoom;
    String direction;

    public SavedObject(int currentRoom, String direction, HashMap<Integer, RoomData> rooms) {
        this.currentRoom = currentRoom;
        this.rooms = rooms;
        this.direction = direction;
    }

    public HashMap<Integer, RoomData> getRooms() {
        return rooms;
    }

    public int getCurrentRoom() {
        return currentRoom;
    }

    public String getDirection() {
        return direction;
    }
}
