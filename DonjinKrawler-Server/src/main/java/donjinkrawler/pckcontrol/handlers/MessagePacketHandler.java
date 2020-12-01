package donjinkrawler.pckcontrol.handlers;

import com.esotericsoftware.kryonet.Connection;
import donjinkrawler.GameServer;
import krawlercommon.ConnectionManager;
import krawlercommon.packets.MessagePacket;

public class MessagePacketHandler extends PacketHandler {
    @Override
    public boolean handle(Object object, GameServer gameServer, Connection connection) {
        if(! (object instanceof MessagePacket)) {
            return this.next(object, gameServer, connection);
        }

        MessagePacket messagePacket = (MessagePacket) object;

        String playerName = ConnectionManager.getInstance().getPlayerFromConnection(connection).getName();
        messagePacket.message = messagePacket.message + " " + playerName;
        gameServer.getKryo().sendToAllExceptUDP(connection.getID(), messagePacket);

        return true;
    }
}
