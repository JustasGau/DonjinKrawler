package donjinkrawler.pckcontrol.handlers;

import donjinkrawler.pckcontrol.Request;
import krawlercommon.ConnectionManager;
import krawlercommon.packets.MessagePacket;

public class MessagePacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if(! (request.getObject() instanceof MessagePacket)) {
            return this.next(request);
        }

        MessagePacket messagePacket = (MessagePacket) request.getObject();

        String playerName = ConnectionManager.getInstance().getPlayerFromConnection(request.getConnection()).getName();
        messagePacket.message = messagePacket.message + " " + playerName;
        request.getGameServer().getKryo().sendToAllExceptUDP(request.getConnection().getID(), messagePacket);

        return true;
    }
}
