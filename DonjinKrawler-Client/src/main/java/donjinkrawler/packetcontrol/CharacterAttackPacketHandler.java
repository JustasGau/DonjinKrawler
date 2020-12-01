package donjinkrawler.packetcontrol;

import donjinkrawler.Client;
import krawlercommon.packets.CharacterAttackPacket;

public class CharacterAttackPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Object object, Client client) {
        if(! (object instanceof CharacterAttackPacket) || client.getGame() == null) {
            return this.next(object, client);
        }

        CharacterAttackPacket character = (CharacterAttackPacket) object;
        client.getGame().drawPlayerAttack(character.id);

        return true;
    }
}
