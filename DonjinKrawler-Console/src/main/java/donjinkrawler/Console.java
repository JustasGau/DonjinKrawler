package donjinkrawler;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import donjinkrawler.interpreter.InvalidCommandException;
import donjinkrawler.interpreter.Expression;
import krawlercommon.PlayerData;
import krawlercommon.RegistrationManager;
import krawlercommon.packets.GetPlayersPacket;
import krawlercommon.packets.LoginPacket;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;

public class Console {
    private static final int SERVER_TCP_PORT = 54555;
    private static final int SERVER_UDP_PORT = 54777;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandParser commandParser = new CommandParser();
        Client client = getClient();
        System.out.println("Console started. Waiting for commands");
        System.out.println("Type 'help' to learn more");

        while (true) {
            try {
                Expression expression = commandParser.parse(scanner.nextLine());
                expression.interpret(client);
            } catch (InvalidCommandException exception) {
                System.out.println(exception);
            }
        }
    }

    public static Client getClient() {
        Client client = new Client(65536, 65536);
        client.getKryo().setReferences(true);
        RegistrationManager.registerKryo(client.getKryo());
        client.start();
        try {
            client.connect(5000, InetAddress.getByName("localhost").getHostAddress(), SERVER_TCP_PORT, SERVER_UDP_PORT);
            System.out.println("Console successfully connected to server");

            LoginPacket loginPacket = new LoginPacket();
            loginPacket.name = "Admin";
            client.sendTCP(loginPacket);

            client.addListener(new Listener() {
                @Override
                public void received(Connection connection, Object object) {
                    if (object instanceof GetPlayersPacket) {
                        List<PlayerData> players = ((GetPlayersPacket) object).getPlayers();

                        for (PlayerData player : players) {
                            System.out.printf("%s %s\n", player.getId(), player.getName());
                        }
                    }
                }
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return client;
    }
}
