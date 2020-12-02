package donjinkrawler.pckcontrol;

import com.esotericsoftware.kryonet.Connection;
import donjinkrawler.GameServer;

public class Request {

    private final GameServer gameServer;
    private final Object object;
    private final Connection connection;

    public Request(GameServer gameServer, Object object, Connection connection) {
        this.gameServer = gameServer;
        this.object = object;
        this.connection = connection;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public Object getObject() {
        return object;
    }

    public Connection getConnection() {
        return connection;
    }
}
