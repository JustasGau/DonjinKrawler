package donjinkrawler.pckcontrol;

import donjinkrawler.GameServer;
import donjinkrawler.pckcontrol.handlers.CharacterAttackPacketHandler;
import donjinkrawler.pckcontrol.handlers.PacketHandler;

public class PacketControlUnit {

    private final PacketHandler packetHandler;

    public PacketControlUnit() {
        PacketHandler packetHandler = (new LoginPacketHandler());

        packetHandler
                .linkWith(new MessagePacketHandler())
                .linkWith(new MoveCharacterPacketHandler())
                .linkWith(new RoomPacketHandler())
                .linkWith(new ChangeEnemyStrategyPacketHandler())
                .linkWith(new CharacterAttackPacketHandler())
                .linkWith(new DamageEnemyPacketHandler());

        this.packetHandler = packetHandler;
    }

    public void handle(GameServer gameServer, Object object) {
        packetHandler.handle(object, gameServer);
    }

}
