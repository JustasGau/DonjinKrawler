package donjinkrawler.clientpacketcontrol.handlers;

import donjinkrawler.clientpacketcontrol.Request;
import krawlercommon.packets.CreatePlayerPacket;

public class CreatePlayerPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof CreatePlayerPacket) || !request.isGameActive()) {
            return this.next(request);
        }

        CreatePlayerPacket createPlayerPacket = (CreatePlayerPacket) request.getObject();
        request.getGame().addPlayerShell(createPlayerPacket.player);

        return true;
    }
}
