package donjinkrawler.proxy;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import krawlercommon.ConnectionManager;
import krawlercommon.PlayerData;
import krawlercommon.packets.MessagePacket;

public class BaseListener extends Listener {

    @Override
    public void connected(Connection connection) {
        PlayerData newPlayer = new PlayerData(
                "",
                ConnectionManager.getInstance().getIncrementingPlayerIDs(),
                250,
                250);
        ConnectionManager.getInstance().addPlayer(connection, newPlayer);
        MessagePacket messagePacket = new MessagePacket();
        messagePacket.message = "MSG WELCOME TO THE SERVER!";
        connection.sendTCP(messagePacket);
    }
}
