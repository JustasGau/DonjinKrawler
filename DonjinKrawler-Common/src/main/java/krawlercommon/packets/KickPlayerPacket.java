package krawlercommon.packets;

public class KickPlayerPacket {

    public int id;
    public boolean selfKick;

    public KickPlayerPacket(int id, boolean selfKick) {
        this.id = id;
        this.selfKick = selfKick;
    }

    public KickPlayerPacket() {
        this.selfKick = false;
    }
}
