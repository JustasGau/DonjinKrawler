package donjinkrawler.serverpacketcontrol.handlers;

import donjinkrawler.serverpacketcontrol.Request;
import krawlercommon.ConnectionManager;
import krawlercommon.PlayerData;
import krawlercommon.packets.MoveCharacter;

public class MoveCharacterPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {

        if (!(request.getObject() instanceof MoveCharacter)) {
            return this.next(request);
        }

        MoveCharacter packet = (MoveCharacter) request.getObject();
        PlayerData player = ConnectionManager.getInstance().getPlayerFromConnection(request.getConnection());
        player.setX(packet.x);
        player.setY(packet.y);
        request.getGameServer().getKryo().sendToAllExceptTCP(request.getConnection().getID(), packet);

        return true;
    }
}
