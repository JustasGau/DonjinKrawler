package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.Client;
import krawlercommon.packets.ChangeEnemyStrategyPacket;

public class ChangeEnemyStrategyPacketHandler extends PacketHandler{
    @Override
    public boolean handle(Object object, Client client) {
        if(! (object instanceof ChangeEnemyStrategyPacket) || client.getGame() == null) {
            return this.next(object, client);
        }

        ChangeEnemyStrategyPacket enemy = (ChangeEnemyStrategyPacket) object;
        client.getGame().updateEnemyStrategy(enemy.id, enemy.strategy);

        return true;
    }
}
