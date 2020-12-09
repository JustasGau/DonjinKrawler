package donjinkrawler.serverpacketcontrol.handlers;

import donjinkrawler.serverpacketcontrol.Request;
import krawlercommon.packets.LoginPacket;

public class LoginPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof LoginPacket)) {
            return this.next(request);
        }

        LoginPacket loginPacket = (LoginPacket) request.getObject();

        if(loginPacket.isAdmin) {
            request.getGameServer().addTimer();
            return true;
        }

        request.getGameServer().createNewPlayer(request.getConnection(), loginPacket);
        request.getGameServer().addTimer();

        return true;
    }
}
