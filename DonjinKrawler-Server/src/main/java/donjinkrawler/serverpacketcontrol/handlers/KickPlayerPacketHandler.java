package donjinkrawler.serverpacketcontrol.handlers;

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

        request.getGameServer().getKryo().sendToAllExceptTCP(request.getConnection().getID(), disconnectPacket);
        ConnectionManager.getInstance().removeConnectionById(disconnectPacket.id);

        return true;
    }
}
