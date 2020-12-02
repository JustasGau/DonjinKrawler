package donjinkrawler.clientpacketcontrol.handlers;

import donjinkrawler.clientpacketcontrol.Request;
import krawlercommon.packets.IdPacket;

public class IdPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof IdPacket)) {
            return this.next(request);
        }

        IdPacket idPacket = (IdPacket) request.getObject();

        request.getClient().setName(idPacket.playerData.getName());
        request.getClient().initUI(idPacket.currentRoom, idPacket.playerData);

        return true;
    }
}
