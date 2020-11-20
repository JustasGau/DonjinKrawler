package donjinkrawler.memento;

import krawlercommon.PlayerData;
import krawlercommon.map.RoomData;

import java.util.HashMap;
import java.util.Map;

public class SavedObject {
    HashMap<Integer, RoomData>  rooms;
    Map<Integer, PlayerData> players;
    int currentRoom;
    String direction;

    public SavedObject(int currentRoom, String direction, Map<Integer, PlayerData> players, HashMap<Integer, RoomData> rooms) {
        this.currentRoom = currentRoom;
        this.players = players;
        this.rooms = rooms;
        this.direction = direction;
    }

    public HashMap<Integer, RoomData> getRooms(){
        return rooms;
    }

    public Map<Integer, PlayerData> getPlayers(){
        return players;
    }

    public int getCurrentRoom(){
        return currentRoom;
    }

    public String getDirection(){
        return direction;
    }
}
