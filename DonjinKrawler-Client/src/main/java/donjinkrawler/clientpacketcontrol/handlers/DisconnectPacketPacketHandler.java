package donjinkrawler.clientpacketcontrol.handlers;

import donjinkrawler.clientpacketcontrol.Request;
import krawlercommon.packets.DisconnectPacket;

public class DisconnectPacketPacketHandler extends PacketHandler {

    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof DisconnectPacket) || !request.isGameActive()) {
            return this.next(request);
        }

        DisconnectPacket dcPacket = (DisconnectPacket) request.getObject();
        request.getGame().deletePlayerShell(dcPacket.id);

        return true;
    }
}
