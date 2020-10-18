package donjinkrawler.map;

import donjinkrawler.Player;
import krawlercommon.map.DoorDirection;

public class GameMap {

    private Room currentRoom;

    public GameMap(Room room) {
        setCurrentRoom(room);
    }

    public DoorDirection update(Player player) {
        return getCurrentRoom().update(player);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
