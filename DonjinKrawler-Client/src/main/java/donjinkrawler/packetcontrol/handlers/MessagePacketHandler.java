package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.Client;
import krawlercommon.packets.MessagePacket;

public class MessagePacketHandler extends PacketHandler{

    @Override
    public boolean handle(Object object, Client client) {

        if(! (object instanceof MessagePacket)) {
            return this.next(object, client);
        }

        MessagePacket messagePacket = (MessagePacket) object;

        if (messagePacket.message.startsWith("MSG")) {
            client.setMessageLabelText(messagePacket.message.substring(4));
        } else if (messagePacket.message.startsWith("ENI") && client.getGame() != null) {
            client.getGame().updateEnemyInfo(messagePacket.message);
        }

        return true;
    }
}
