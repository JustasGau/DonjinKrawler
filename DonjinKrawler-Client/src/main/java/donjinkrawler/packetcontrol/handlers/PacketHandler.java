package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.packetcontrol.Request;

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
     *
     * @param request
     */
    public abstract boolean handle(Request request);

    /**
     * Runs check on the next object in chain or ends traversing if we're in
     * last object in chain.
     *
     * @param request
     */
    protected boolean next(Request request) {
        if (next == null) {
            return true;
        }
        return this.next.handle(request);
    }
}
