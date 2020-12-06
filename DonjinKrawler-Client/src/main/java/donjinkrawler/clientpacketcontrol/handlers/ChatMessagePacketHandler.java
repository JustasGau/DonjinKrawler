package donjinkrawler.clientpacketcontrol.handlers;

import donjinkrawler.clientpacketcontrol.Request;
import krawlercommon.packets.ChatMessagePacket;

public class ChatMessagePacketHandler extends PacketHandler {

    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof ChatMessagePacket) || !request.isGameActive()) {
            return this.next(request);
        }

        ChatMessagePacket packet = (ChatMessagePacket) request.getObject();
        request.getGame().getPlayer().getChat().addMessage(packet.from, packet.message);

        return true;
    }

}
