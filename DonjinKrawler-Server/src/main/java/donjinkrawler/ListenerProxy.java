package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import krawlercommon.ConnectionManager;
import krawlercommon.PlayerData;
import krawlercommon.packets.ServerFullPacket;

public class ListenerProxy extends Listener {

    @Override
    public void connected(Connection connection) {
        if (ConnectionManager.getInstance().getAllPlayers().size() > 3) {
            connection.sendTCP(new ServerFullPacket());
        } else {
            PlayerData newPlayer = new PlayerData(
                    "",
                    ConnectionManager.getInstance().getIncrementingPlayerIDs(),
                    250,
                    250);
            ConnectionManager.getInstance().addPlayer(connection, newPlayer);
        }
        super.connected(connection);
    }

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }
}
