package donjinkrawler.pckcontrol.handlers;

import com.esotericsoftware.kryonet.Connection;
import donjinkrawler.GameServer;
import krawlercommon.ConnectionManager;
import krawlercommon.PlayerData;
import krawlercommon.packets.MoveCharacter;

public class MoveCharacterPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Object object, GameServer gameServer, Connection connection) {

        if(! (object instanceof MoveCharacter)) {
            return this.next(object, gameServer, connection);
        }

        MoveCharacter packet = (MoveCharacter) object;
        PlayerData player = ConnectionManager.getInstance().getPlayerFromConnection(connection);
        player.setX(packet.x);
        player.setY(packet.y);
        gameServer.getKryo().sendToAllExceptTCP(connection.getID(), object);

        return true;
    }
}
