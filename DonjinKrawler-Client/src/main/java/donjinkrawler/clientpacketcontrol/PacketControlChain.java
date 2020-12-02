package donjinkrawler.clientpacketcontrol;

import donjinkrawler.Client;
import donjinkrawler.clientpacketcontrol.handlers.*;

public class PacketControlChain {

    private final PacketHandler packetHandler;

    public PacketControlChain() {
        PacketHandler packetHandler = (new MessagePacketHandler());

        packetHandler.linkWith(new MapPacketHandler())
                .linkWith(new IdPacketHandler())
                .linkWith(new MoveCharacterPacketHandler())
                .linkWith(new EnemyPacketHandler())
                .linkWith(new CreatePlayerPacketHandler())
                .linkWith(new DisconnectPacketPacketHandler())
                .linkWith(new RoomPacketHandler())
                .linkWith(new ChangeEnemyStrategyPacketHandler())
                .linkWith(new CharacterAttackPacketHandler())
                .linkWith(new ServerFullPacketHandler());

        this.packetHandler = packetHandler;
    }

    public void handle(Client client, Object object) {
        packetHandler.handle(new Request(client, object));
    }
}
