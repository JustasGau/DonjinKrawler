package donjinkrawler.proxy;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import krawlercommon.ConnectionManager;
import krawlercommon.packets.ServerFullPacket;

public class ListenerProxy extends Listener {

    BaseListener baseListener;

    public ListenerProxy(BaseListener baseListener) {
        this.baseListener = baseListener;
    }

    @Override
    public synchronized void connected(Connection connection) {
        // Adds additional security to server, if 4 players are connected, no more are allowed.
        if (ConnectionManager.getInstance().getAllPlayers().size() > 3) {
            connection.sendTCP(new ServerFullPacket());
        } else {
            baseListener.connected(connection);
        }
    }
}
