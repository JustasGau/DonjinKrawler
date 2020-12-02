package donjinkrawler.clientpacketcontrol.handlers;

import donjinkrawler.clientpacketcontrol.Request;
import krawlercommon.packets.CharacterAttackPacket;

public class CharacterAttackPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof CharacterAttackPacket) || !request.isGameActive()) {
            return this.next(request);
        }

        CharacterAttackPacket character = (CharacterAttackPacket) request.getObject();
        request.getGame().drawPlayerAttack(character.id);

        return true;
    }
}
