package donjinkrawler.clientpacketcontrol.handlers;

import donjinkrawler.clientpacketcontrol.Request;
import krawlercommon.packets.KickPlayerPacket;

public class KickPlayerPacketHandler extends PacketHandler {

    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof KickPlayerPacket)) {
            return this.next(request);
        }

        request.getClient().kickOut();

        return true;
    }
}
