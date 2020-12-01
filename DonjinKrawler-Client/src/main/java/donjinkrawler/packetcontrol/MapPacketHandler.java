package donjinkrawler.packetcontrol;

import donjinkrawler.Client;
import krawlercommon.packets.MapPacket;

public class MapPacketHandler extends PacketHandler{
    @Override
    public boolean handle(Object object, Client client) {

        if(! (object instanceof MapPacket)) {
            return this.next(object, client);
        }

        MapPacket mapPacket = (MapPacket) object;

        if (mapPacket.update) {
            client.getGame().updateMap(mapPacket.rooms);
        } else {
            client.setRooms(mapPacket.rooms);
        }

        return true;
    }
}
