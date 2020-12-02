package donjinkrawler;

import com.esotericsoftware.kryonet.Client;
import krawlercommon.RegistrationManager;

import java.io.IOException;
import java.util.Scanner;

public class Console {
    private static final int SERVER_TCP_PORT = 54555;
    private static final int SERVER_UDP_PORT = 54777;

    public static void main(String[] args) {
        com.esotericsoftware.kryonet.Client client = new Client(65536, 65536);
        client.getKryo().setReferences(true);
        RegistrationManager.registerKryo(client.getKryo());
        client.start();
        try {
            client.connect(5000, "localhost", SERVER_TCP_PORT, SERVER_UDP_PORT);
            System.out.println("Successfully connected to server");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            System.out.println(command);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
