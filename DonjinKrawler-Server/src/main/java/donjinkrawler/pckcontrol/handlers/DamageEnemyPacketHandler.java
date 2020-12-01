package donjinkrawler.pckcontrol.handlers;

import com.esotericsoftware.kryonet.Connection;
import donjinkrawler.GameServer;
import krawlercommon.enemies.Enemy;
import krawlercommon.packets.DamageEnemyPacket;

public class DamageEnemyPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Object object, GameServer gameServer, Connection connection) {
        if(! (object instanceof DamageEnemyPacket)) {
            return this.next(object, gameServer, connection);
        }

        DamageEnemyPacket packet = (DamageEnemyPacket) object;

        for (Enemy enemy : gameServer.getCurrentRoom().getEnemies()) {
            if (enemy != null && enemy.getID() == packet.id) {
                enemy.damage(packet.damage);
                break;
            }
        }

        return true;
    }
}
