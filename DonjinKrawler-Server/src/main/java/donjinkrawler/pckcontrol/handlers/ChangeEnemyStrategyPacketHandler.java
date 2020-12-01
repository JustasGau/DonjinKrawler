package donjinkrawler.pckcontrol.handlers;

import com.esotericsoftware.kryonet.Connection;
import donjinkrawler.GameServer;
import krawlercommon.enemies.Enemy;
import krawlercommon.packets.ChangeEnemyStrategyPacket;

public class ChangeEnemyStrategyPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Object object, GameServer gameServer, Connection connection) {
        if(! (object instanceof ChangeEnemyStrategyPacket)) {
            return this.next(object, gameServer, connection);
        }

        ChangeEnemyStrategyPacket packet = (ChangeEnemyStrategyPacket) object;

        for (Enemy enemy : gameServer.getCurrentRoom().getEnemies()) {
            if (enemy != null && enemy.getID() == packet.id) {
                enemy.setCurrentStrategy(packet.strategy);
                break;
            }
        }

        return true;
    }
}
