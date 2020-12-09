package donjinkrawler.serverpacketcontrol.handlers;

import donjinkrawler.serverpacketcontrol.Request;
import krawlercommon.enemies.Enemy;
import krawlercommon.packets.DamageEnemyPacket;

public class DamageEnemyPacketHandler extends PacketHandler {
    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof DamageEnemyPacket)) {
            return this.next(request);
        }

        DamageEnemyPacket packet = (DamageEnemyPacket) request.getObject();

        for (Enemy enemy : request.getGameServer().getCurrentRoom().getEnemies()) {
            if (enemy != null && enemy.getID() == packet.id) {
                enemy.damage(packet.damage);
                if (enemy.getHealth() < 0) {
                    request.getGameServer().getCurrentRoom().getEnemies().remove(enemy);
                }
                break;
            }
        }

        return true;
    }
}
