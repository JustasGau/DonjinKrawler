package donjinkrawler.packetcontrol.handlers;

import donjinkrawler.Client;
import krawlercommon.packets.ServerFullPacket;

import javax.swing.*;

public class ServerFullPacketHandler extends PacketHandler{
    @Override
    public boolean handle(Object object, Client client) {
        if(! (object instanceof ServerFullPacket)) {
            return this.next(object, client);
        }

        JOptionPane.showMessageDialog(client.getFrame(), "Server is full, sorry!");
        System.exit(0);

        return true;
    }
}
