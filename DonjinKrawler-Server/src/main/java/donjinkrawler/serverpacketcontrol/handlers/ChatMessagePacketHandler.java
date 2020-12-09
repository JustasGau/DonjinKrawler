package donjinkrawler.serverpacketcontrol.handlers;

import donjinkrawler.chatroom.RoomMate;
import donjinkrawler.serverpacketcontrol.Request;
import krawlercommon.packets.ChatMessagePacket;

public class ChatMessagePacketHandler extends PacketHandler {

    @Override
    public boolean handle(Request request) {
        if (!(request.getObject() instanceof ChatMessagePacket)) {
            return this.next(request);
        }

        ChatMessagePacket packet = (ChatMessagePacket) request.getObject();
        RoomMate roomMate = request.getGameServer().getChatRoom().getRoomMate(packet.id);

        roomMate.send(packet.message);

        return true;
    }
}
