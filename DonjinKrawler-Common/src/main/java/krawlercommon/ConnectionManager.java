package krawlercommon;

import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager {

    private static final ConnectionManager instance = new ConnectionManager();

    private final Map<Integer, PlayerData> playerConnectionMap;

    private int playerIDs;

    private ConnectionManager() {
        playerConnectionMap = new HashMap<>();
        playerIDs = 1;
    }

    public static ConnectionManager getInstance() {
        return instance;
    }

    public int getPlayerIDs() {
        return playerIDs;
    }

    public int getIncrementingPlayerIDs() {
        return playerIDs++;
    }

    public void resetConnectionManager() {
        playerConnectionMap.clear();
        playerIDs = 1;
    }

    public void addPlayer(Connection connection, PlayerData player) {
        playerConnectionMap.put(connection.getID(), player);
    }

    public void removeConnection(Connection connection) {
        playerConnectionMap.remove(connection.getID());
    }

    public void removeConnectionById(int id) {
        playerConnectionMap.remove(id);
    }

    public PlayerData getPlayerFromConnection(Connection connection) {
        return playerConnectionMap.get(connection.getID());
    }

    public PlayerData getPlayerFromId(int id) {
        return playerConnectionMap.get(id);
    }

    public List<PlayerData> getAllPlayers() {
        if (playerConnectionMap.size() > 0) {
            return new ArrayList<>(playerConnectionMap.values());
        } else {
            return new ArrayList<>();
        }
    }

    public List<Integer> getAllConnections() {
        if (playerConnectionMap.size() > 0) {
            return new ArrayList<>(playerConnectionMap.keySet());
        } else {
            return new ArrayList<>();
        }
    }
}