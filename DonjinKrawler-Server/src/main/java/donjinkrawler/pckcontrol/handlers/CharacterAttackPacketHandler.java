package donjinkrawler.pckcontrol.handlers;

import com.esotericsoftware.kryonet.Connection;
import donjinkrawler.GameServer;
import krawlercommon.packets.CharacterAttackPacket;

public class CharacterAttackPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Object object, GameServer gameServer, Connection connection) {
        if(! (object instanceof CharacterAttackPacket)) {
            return this.next(object, gameServer, connection);
        }

        CharacterAttackPacket packet = (CharacterAttackPacket) object;
        gameServer.getKryo().sendToAllExceptUDP(connection.getID(), packet);

        return true;
    }
}
