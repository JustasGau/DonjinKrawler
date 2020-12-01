package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.Client;
import krawlercommon.packets.CreatePlayerPacket;

public class CreatePlayerPacketHandler extends PacketHandler{
    @Override
    public boolean handle(Object object, Client client) {
        if(! (object instanceof CreatePlayerPacket) || client.getGame() == null) {
            return this.next(object, client);
        }

        CreatePlayerPacket createPlayerPacket = (CreatePlayerPacket) object;
        client.getGame().addPlayerShell(createPlayerPacket.player);

        return true;
    }
}
