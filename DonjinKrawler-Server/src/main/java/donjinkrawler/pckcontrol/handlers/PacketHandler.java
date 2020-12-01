package donjinkrawler.pckcontrol.handlers;

import donjinkrawler.GameServer;

public abstract class PacketHandler {

    private PacketHandler next;

    /**
     * Builds chains of middleware objects.
     */
    public PacketHandler linkWith(PacketHandler next) {
        this.next = next;
        return next;
    }

    /**
     * Subclasses will implement this method with concrete checks.
     */
    public abstract boolean handle(Object object, GameServer gameServer);

    /**
     * Runs check on the next object in chain or ends traversing if we're in
     * last object in chain.
     */
    protected boolean next(Object object, GameServer gameServer) {
        if (next == null) {
            return true;
        }
        return this.next.handle(object, gameServer);
    }

}
