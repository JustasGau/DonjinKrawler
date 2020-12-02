package donjinkrawler.proxy;

import com.esotericsoftware.kryonet.Connection;
import krawlercommon.ConnectionManager;
import krawlercommon.packets.ServerFullPacket;

public class ListenerProxy extends BaseListener {

    @Override
    public void connected(Connection connection) {
        // Adds additional security to server, if 4 players are connected, no more are allowed.
        if (ConnectionManager.getInstance().getAllPlayers().size() > 3) {
            connection.sendTCP(new ServerFullPacket());
        } else {
            super.connected(connection);
        }
    }
}
