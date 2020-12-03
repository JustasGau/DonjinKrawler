package donjinkrawler.interpreter;

import com.esotericsoftware.kryonet.Client;
import krawlercommon.packets.GetPlayersPacket;

public class GetPlayersExpression implements Expression {
    @Override
    public void interpret(Client client) {
        System.out.println("Fetching player list...");
        client.sendTCP(new GetPlayersPacket());
    }
}
