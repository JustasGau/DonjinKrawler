package krawlercommon.packets;

public class ChatMessagePacket {
    public String message;
    public int id;
    public String from;

    public ChatMessagePacket() {
    }

    public ChatMessagePacket(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public ChatMessagePacket(String message, String from) {
        this.message = message;
        this.from = from;
    }
}
