package donjinkrawler.clientpacketcontrol.handlers;

import donjinkrawler.clientpacketcontrol.Request;
import krawlercommon.packets.EnemyPacket;

public class EnemyPacketHandler extends PacketHandler {

    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof EnemyPacket) || !request.isGameActive()) {
            return this.next(request);
        }

        EnemyPacket enemyPacket = (EnemyPacket) request.getObject();

        if (enemyPacket.isUpdate()) {
            request.getGame().updateEnemies(enemyPacket.getEnemies());
        } else {
            request.getGame().addEnemies(enemyPacket.getEnemies());
        }

        return true;
    }
}
