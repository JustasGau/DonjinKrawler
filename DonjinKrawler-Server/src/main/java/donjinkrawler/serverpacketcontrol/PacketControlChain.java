package donjinkrawler.serverpacketcontrol;

import com.esotericsoftware.kryonet.Connection;
import donjinkrawler.GameServer;
import donjinkrawler.serverpacketcontrol.handlers.*;

public class PacketControlChain {

    private final PacketHandler packetHandler;

    public PacketControlChain() {
        PacketHandler packetHandler = (new LoginPacketHandler());

        packetHandler.linkWith(new MessagePacketHandler())
                .linkWith(new MoveCharacterPacketHandler())
                .linkWith(new RoomPacketHandler())
                .linkWith(new ChangeEnemyStrategyPacketHandler())
                .linkWith(new CharacterAttackPacketHandler())
                .linkWith(new DamageEnemyPacketHandler())
                .linkWith(new ChatMessagePacketHandler());

        this.packetHandler = packetHandler;
    }

    public void handle(GameServer gameServer, Object object, Connection connection) {
        packetHandler.handle(new Request(gameServer, object, connection));
    }
}
