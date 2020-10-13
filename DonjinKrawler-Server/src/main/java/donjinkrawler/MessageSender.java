package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import krawlercommon.packets.MessagePacket;

public class MessageSender {

    public static void sendMessageToSingle(Connection connection, Server server, String message) {
        MessagePacket messagePacket = constructMessage(message);
        server.sendToUDP(connection.getID(), messagePacket);
    }

    public static void sendMessageToAll(Server server, String message) {
        MessagePacket messagePacket = constructMessage(message);
        server.sendToAllUDP(messagePacket);
    }

    public static void sendMessageToAllExcept(Connection connection, Server server, String message) {
        MessagePacket messagePacket = constructMessage(message);
        server.sendToAllExceptUDP(connection.getID(), messagePacket);
    }

    private static MessagePacket constructMessage(String message) {
        MessagePacket messagePacket = new MessagePacket();
        messagePacket.message = "MSG " + message;
        return messagePacket;
    }
}
