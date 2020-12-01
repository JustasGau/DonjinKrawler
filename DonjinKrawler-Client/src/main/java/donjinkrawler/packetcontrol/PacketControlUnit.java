package donjinkrawler.packetcontrol;

import donjinkrawler.Client;
import donjinkrawler.packetcontrol.handlers.*;

public class PacketControlUnit {

    private final PacketHandler packetHandler;

    public PacketControlUnit() {
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
        packetHandler.handle(object, client);
    }
}
