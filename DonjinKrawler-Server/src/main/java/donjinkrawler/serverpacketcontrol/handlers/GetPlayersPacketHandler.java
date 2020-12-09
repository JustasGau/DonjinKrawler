package donjinkrawler.serverpacketcontrol.handlers;

import donjinkrawler.serverpacketcontrol.Request;
import krawlercommon.ConnectionManager;
import krawlercommon.packets.GetPlayersPacket;

public class GetPlayersPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof GetPlayersPacket)) {
            return this.next(request);
        }

        GetPlayersPacket packet = (GetPlayersPacket) request.getObject();
        packet.setPlayers(ConnectionManager.getInstance().getAllPlayers());

        request.getGameServer().getKryo().sendToTCP(request.getConnection().getID(), packet);

        return true;
    }
}
