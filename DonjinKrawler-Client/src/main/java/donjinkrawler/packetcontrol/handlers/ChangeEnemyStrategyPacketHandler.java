package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.packetcontrol.Request;
import krawlercommon.packets.ChangeEnemyStrategyPacket;

public class ChangeEnemyStrategyPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof ChangeEnemyStrategyPacket) || !request.isGameActive()) {
            return this.next(request);
        }

        ChangeEnemyStrategyPacket enemy = (ChangeEnemyStrategyPacket) request.getObject();
        request.getGame().updateEnemyStrategy(enemy.id, enemy.strategy);

        return true;
    }
}
