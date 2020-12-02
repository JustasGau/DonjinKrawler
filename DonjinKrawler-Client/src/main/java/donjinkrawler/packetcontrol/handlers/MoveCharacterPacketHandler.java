package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.packetcontrol.Request;
import krawlercommon.packets.MoveCharacter;

public class MoveCharacterPacketHandler extends PacketHandler{
    @Override
    public boolean handle(Request request) {
        if(! (request.getObject() instanceof MoveCharacter) || !request.isGameActive()) {
            return this.next(request);
        }

        MoveCharacter moveCharacter = (MoveCharacter) request.getObject();
        request.getGame().changeShellPosition(moveCharacter);

        return true;
    }
}
