package donjinkrawler.interpreter;

import com.esotericsoftware.kryonet.Client;
import krawlercommon.packets.KickPlayerPacket;

public class KickExpression implements Expression {
    private ArgumentExpression operand;

    public KickExpression(ArgumentExpression operand) {
        this.operand = operand;
    }

    @Override
    public void interpret(Client client) {
        System.out.printf("Sending DisconnectPacket to player id %s\n", operand.getValue());
        KickPlayerPacket kickPlayerPacket = new KickPlayerPacket();
        kickPlayerPacket.id = Integer.parseInt(operand.getValue());
        client.sendTCP(kickPlayerPacket);
    }
}
