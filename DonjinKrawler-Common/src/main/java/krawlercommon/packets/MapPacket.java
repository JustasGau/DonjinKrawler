package krawlercommon.packets;

import krawlercommon.map.RoomData;

import java.util.HashMap;

public class MapPacket {
    public int gridSize;
    public boolean update = false;
    public int room = 0;
    // TODO: do we need a HashMap?
    public HashMap<Integer, RoomData> rooms;
}
