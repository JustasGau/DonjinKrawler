package donjinkrawler.pckcontrol.handlers;

import donjinkrawler.pckcontrol.Request;
import krawlercommon.packets.RoomPacket;

public class RoomPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof RoomPacket)) {
            return this.next(request);
        }

        RoomPacket roomPacket = (RoomPacket) request.getObject();

        request.getGameServer().setCurrentRoom(roomPacket.id);
        request.getGameServer().setCurrentDirection(roomPacket.direction);
        request.getGameServer().sendEnemies(true);
        request.getGameServer().getKryo().sendToAllExceptUDP(request.getConnection().getID(), roomPacket);

        return true;
    }
}
