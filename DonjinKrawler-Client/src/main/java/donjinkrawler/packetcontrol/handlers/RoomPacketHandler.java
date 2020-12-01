package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.Client;
import krawlercommon.packets.RoomPacket;

public class RoomPacketHandler extends PacketHandler{
    @Override
    public boolean handle(Object object, Client client) {
        if(! (object instanceof RoomPacket) || client.getGame() == null) {
            return this.next(object, client);
        }

        client.getGame().changeRoom((RoomPacket) object);

        return true;
    }
}
