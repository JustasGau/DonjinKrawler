package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.Client;
import krawlercommon.packets.DisconnectPacket;

public class DisconnectPacketPacketHandler extends PacketHandler{

    @Override
    public boolean handle(Object object, Client client) {
        if(! (object instanceof DisconnectPacket) || client.getGame() == null) {
            return this.next(object, client);
        }

        DisconnectPacket dcPacket = (DisconnectPacket) object;
        client.getGame().deletePlayerShell(dcPacket.id);

        return true;
    }
}
