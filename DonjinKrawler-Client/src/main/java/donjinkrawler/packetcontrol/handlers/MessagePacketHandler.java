package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.packetcontrol.Request;
import krawlercommon.packets.MessagePacket;

public class MessagePacketHandler extends PacketHandler {

    @Override
    public boolean handle(Request request) {

        if (!(request.getObject() instanceof MessagePacket)) {
            return this.next(request);
        }

        MessagePacket messagePacket = (MessagePacket) request.getObject();

        if (messagePacket.message.startsWith("MSG")) {
            request.getClient().setMessageLabelText(messagePacket.message.substring(4));
        } else if (messagePacket.message.startsWith("ENI") && request.getGame() != null) {
            request.getGame().updateEnemyInfo(messagePacket.message);
        }

        return true;
    }
}
