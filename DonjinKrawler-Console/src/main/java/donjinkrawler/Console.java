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
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public final class Console {
    private static final int SERVER_TCP_PORT = 54555;
    private static final int SERVER_UDP_PORT = 54777;
    private static final int CLIENT_WRITE_BUFFER_SIZE = 65536;
    private static final int CLIENT_OBJECT_BUFFER_SIZE = 65536;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandParser commandParser = new CommandParser();
        Client client = getClient(args);
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

    public static String getAddress(String[] args) {
        if (args.length > 0) {
            return args[0];
        } else {
            try {
                return InetAddress.getByName("localhost").getHostAddress();
            } catch (UnknownHostException ex) {
                return "localhost";
            }
        }
    }

    public static Client getClient(String[] args) {
        Client client = new Client(CLIENT_WRITE_BUFFER_SIZE, CLIENT_OBJECT_BUFFER_SIZE);
        client.getKryo().setReferences(true);
        RegistrationManager.registerKryo(client.getKryo());
        client.start();

        try {
            client.connect(5000, getAddress(args), SERVER_TCP_PORT, SERVER_UDP_PORT);
            System.out.println("Console successfully connected to server");

            LoginPacket loginPacket = new LoginPacket();
            loginPacket.name = "Blogasis Policininkas";
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
