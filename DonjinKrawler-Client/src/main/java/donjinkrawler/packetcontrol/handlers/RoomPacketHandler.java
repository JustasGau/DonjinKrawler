package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.packetcontrol.Request;
import krawlercommon.packets.RoomPacket;

public class RoomPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof RoomPacket) || !request.isGameActive()) {
            return this.next(request);
        }

        request.getGame().changeRoom((RoomPacket) request.getObject());

        return true;
    }
}
