package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.Client;
import krawlercommon.packets.IdPacket;

public class IdPacketHandler extends PacketHandler{
    @Override
    public boolean handle(Object object, Client client) {
        if(! (object instanceof IdPacket)) {
            return this.next(object, client);
        }

        IdPacket idPacket = (IdPacket) object;

        client.setName(idPacket.playerData.getName());
        client.initUI(idPacket.currentRoom, idPacket.playerData);

        return true;
    }
}
