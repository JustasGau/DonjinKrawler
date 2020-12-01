package donjinkrawler.pckcontrol.handlers;

import com.esotericsoftware.kryonet.Connection;
import donjinkrawler.GameServer;
import krawlercommon.packets.LoginPacket;

public class LoginPacketHandler extends PacketHandler{
    @Override
    public boolean handle(Object object, GameServer gameServer, Connection connection) {
        if(! (object instanceof LoginPacket)) {
            return this.next(object, gameServer, connection);
        }

        LoginPacket loginPacket = (LoginPacket) object;

        gameServer.createNewPlayer(connection, loginPacket);
        gameServer.addTimer();

        return true;
    }
}
