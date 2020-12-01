package donjinkrawler.pckcontrol.handlers;

import com.esotericsoftware.kryonet.Connection;
import donjinkrawler.GameServer;
import krawlercommon.packets.RoomPacket;

public class RoomPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Object object, GameServer gameServer, Connection connection) {
        if(! (object instanceof RoomPacket)) {
            return this.next(object, gameServer, connection);
        }

        RoomPacket roomPacket = (RoomPacket) object;

        gameServer.setCurrentRoom(roomPacket.id);
        gameServer.setCurrentDirection(roomPacket.direction);
        gameServer.sendEnemies(true);
        gameServer.getKryo().sendToAllExceptUDP(connection.getID(), roomPacket);

        return true;
    }
}
