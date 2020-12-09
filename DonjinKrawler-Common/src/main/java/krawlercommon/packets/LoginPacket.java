package krawlercommon.packets;

public class LoginPacket {
    public String name;
    public boolean isAdmin;

    public LoginPacket(String name, boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public LoginPacket() {
        this.isAdmin = false;
    }
}
