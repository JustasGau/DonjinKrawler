package donjinkrawler.pckcontrol.handlers;

import donjinkrawler.pckcontrol.Request;

public abstract class PacketHandler {

    private PacketHandler next;

    public PacketHandler linkWith(PacketHandler next) {
        this.next = next;
        return next;
    }

    public abstract boolean handle(Request request);

    protected boolean next(Request request) {
        if (next == null) {
            return true;
        }
        return this.next.handle(request);
    }

}
