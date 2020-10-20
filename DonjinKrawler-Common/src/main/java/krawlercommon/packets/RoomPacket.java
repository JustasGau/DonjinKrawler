package krawlercommon.packets;

import krawlercommon.enemies.Enemy;

import java.util.ArrayList;

public class RoomPacket {

    public int x;
    public int y;
    public String direction;
    public int id;
    public ArrayList<Enemy> enemies;
}
