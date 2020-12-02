package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.packetcontrol.Request;
import krawlercommon.packets.ServerFullPacket;

import javax.swing.*;

public class ServerFullPacketHandler extends PacketHandler{
    @Override
    public boolean handle(Request request) {
        if(! (request.getObject() instanceof ServerFullPacket)) {
            return this.next(request);
        }

        JOptionPane.showMessageDialog(request.getClient().getFrame(), "Server is full, sorry!");
        System.exit(0);

        return true;
    }
}
