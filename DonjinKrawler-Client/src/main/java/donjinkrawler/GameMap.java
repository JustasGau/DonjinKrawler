package donjinkrawler;

import krawlercommon.map.DoorDirection;

public class GameMap {

    Room currentRoom;

    public GameMap(Room room) {
        currentRoom = room;
    }

    public DoorDirection update(Player player) {
        return currentRoom.update(player);
    }

}
