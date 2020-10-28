package krawlercommon;

import java.util.*;

public class ConnectionManager {

    public static Map<Integer, PlayerData> playerConnectionMap = null;

    public static void init(Map<Integer, PlayerData> map) {
        playerConnectionMap = map;
    }

    public static Map<Integer, PlayerData> getConnectionMap() {
        return playerConnectionMap;
    }
}