package donjinkrawler.pckcontrol.handlers;

import donjinkrawler.pckcontrol.Request;
import krawlercommon.packets.CharacterAttackPacket;

public class CharacterAttackPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if(! (request.getObject() instanceof CharacterAttackPacket)) {
            return this.next(request);
        }

        CharacterAttackPacket packet = (CharacterAttackPacket) request.getObject();
        request.getGameServer().getKryo().sendToAllExceptUDP(request.getConnection().getID(), packet);

        return true;
    }
}
