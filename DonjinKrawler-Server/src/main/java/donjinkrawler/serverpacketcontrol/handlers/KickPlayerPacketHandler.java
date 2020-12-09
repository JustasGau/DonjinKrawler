package donjinkrawler.serverpacketcontrol.handlers;

import com.esotericsoftware.kryonet.Connection;
import donjinkrawler.serverpacketcontrol.Request;
import krawlercommon.ConnectionManager;
import krawlercommon.packets.DisconnectPacket;
import krawlercommon.packets.KickPlayerPacket;

public class KickPlayerPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof KickPlayerPacket)) {
            return this.next(request);
        }

        DisconnectPacket disconnectPacket = new DisconnectPacket();
        disconnectPacket.id = ((KickPlayerPacket) request.getObject()).id;

        Integer playerConnectionId = ConnectionManager.getInstance().getConnectionIdByPlayerId(disconnectPacket.id);
        Connection[] allConnections = request.getGameServer().getKryo().getConnections();

        for (Connection connection : allConnections) {
            if (connection.getID() == playerConnectionId) {
                request.getGameServer().getKryo().sendToTCP(connection.getID(), new KickPlayerPacket());
                connection.close();
                break;
            }
        }

        request.getGameServer().getKryo().sendToAllExceptTCP(playerConnectionId, disconnectPacket);
        ConnectionManager.getInstance().removeConnectionById(playerConnectionId);

        return true;
    }
}
