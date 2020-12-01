package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.Client;
import krawlercommon.packets.MoveCharacter;

public class MoveCharacterPacketHandler extends PacketHandler{
    @Override
    public boolean handle(Object object, Client client) {
        if(! (object instanceof MoveCharacter) || client.getGame() == null) {
            return this.next(object, client);
        }

        MoveCharacter moveCharacter = (MoveCharacter) object;
        client.getGame().changeShellPosition(moveCharacter);

        return true;
    }
}
