package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.Client;
import krawlercommon.packets.EnemyPacket;

public class EnemyPacketHandler extends PacketHandler {

    @Override
    public boolean handle(Object object, Client client) {
        if(! (object instanceof EnemyPacket) || client.getGame() == null) {
            return this.next(object, client);
        }

        EnemyPacket enemyPacket = (EnemyPacket) object;

        if (enemyPacket.isUpdate()) {
            client.getGame().updateEnemies(enemyPacket.getEnemies());
        } else {
            client.getGame().addEnemies(enemyPacket.getEnemies());
        }

        return true;
    }
}
