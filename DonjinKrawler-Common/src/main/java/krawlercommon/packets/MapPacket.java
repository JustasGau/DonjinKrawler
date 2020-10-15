package krawlercommon.packets;

import krawlercommon.map.RoomData;

import java.util.HashMap;

public class MapPacket {
    public int gridSize;
    // TODO: do we need a HashMap?
    public HashMap<Integer, RoomData> rooms;
}
