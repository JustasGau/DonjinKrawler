package donjinkrawler.clientpacketcontrol.handlers;

import donjinkrawler.clientpacketcontrol.Request;
import krawlercommon.packets.MapPacket;

public class MapPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {

        if (!(request.getObject() instanceof MapPacket)) {
            return this.next(request);
        }

        MapPacket mapPacket = (MapPacket) request.getObject();

        if (mapPacket.update) {
            request.getGame().updateMap(mapPacket.rooms, mapPacket.room);
        } else {
            request.getClient().setRooms(mapPacket.rooms);
        }

        return true;
    }
}
