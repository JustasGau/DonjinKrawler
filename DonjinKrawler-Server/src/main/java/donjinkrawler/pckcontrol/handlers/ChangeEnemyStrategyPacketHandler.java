package donjinkrawler.pckcontrol.handlers;

import donjinkrawler.pckcontrol.Request;
import krawlercommon.enemies.Enemy;
import krawlercommon.packets.ChangeEnemyStrategyPacket;

public class ChangeEnemyStrategyPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof ChangeEnemyStrategyPacket)) {
            return this.next(request);
        }

        ChangeEnemyStrategyPacket packet = (ChangeEnemyStrategyPacket) request.getObject();

        for (Enemy enemy : request.getGameServer().getCurrentRoom().getEnemies()) {
            if (enemy != null && enemy.getID() == packet.id) {
                enemy.setCurrentStrategy(packet.strategy);
                break;
            }
        }

        return true;
    }
}
