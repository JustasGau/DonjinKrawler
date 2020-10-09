package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import krawlercommon.packets.ConnectPacket;
import krawlercommon.packets.EnemyPacket;
import krawlercommon.packets.MessagePacket;
import krawlercommon.RegistrationManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private com.esotericsoftware.kryonet.Client kryoClient;
    private final JFrame frame = new JFrame();
    private final int screenWidth = 500;
    private final int screenHeight = 500;
    private final JLabel messageLabel = new JLabel("...");
    private Game game;
    private final int serverPort = 59001;
    private final String serverAddress;
    private String name;
    private int[][] mapGrid;
    private Map<String, Integer> messageCounterMap = new HashMap<>();

    public Client(String serverAddress) throws IOException {
        setupKryo();
        this.serverAddress = serverAddress;
        logIn();
//        initUI();
    }

    private void setupKryo() throws IOException {
        kryoClient = new com.esotericsoftware.kryonet.Client();
        kryoClient.start();
        kryoClient.connect(5000000, "localhost", 54555, 54777);
        RegistrationManager.registerKryo(kryoClient.getKryo());

        kryoClient.addListener(new Listener() {

            public void received(Connection connection, Object object) {
                if (object instanceof MessagePacket) {
                    MessagePacket messagePacket = (MessagePacket) object;
                    if (messagePacket.message.startsWith("MAP")) {
                        increaseCounter("MAP");
                        parseMap(messagePacket.message);
                        initUI();
                    } else if (messagePacket.message.startsWith("MSG")) {
                        increaseCounter("MSG");
                        messageLabel.setText(messagePacket.message.substring(4));
                    } else if (messagePacket.message.startsWith("CRT") && game != null) {
                        increaseCounter("CRT");
                        game.addPlayerShell(messagePacket.message.substring(4));
                    } else if (messagePacket.message.startsWith("POZ") && game != null) {
                        increaseCounter("POZ");
                        game.changeShellPosition(messagePacket.message);
                    } else if (messagePacket.message.startsWith("DLT") && game != null) {
                        increaseCounter("DLT");
                        game.deletePlayerShell(messagePacket.message.substring(4));
                    } else if (messagePacket.message.startsWith("ENI") && game != null) {
                        increaseCounter("ENI");
                        game.updateEnemyInfo(messagePacket.message);
                    } else if (messagePacket.message.startsWith("ROM") && game != null) {
                        increaseCounter("ROM");
                        game.changeRoom(messagePacket.message);
                    }
                } else if (object instanceof EnemyPacket) {
                    EnemyPacket enemyPacket = (EnemyPacket) object;
                    game.addEnemies(enemyPacket.getEnemies());
                }
                for (String key : messageCounterMap.keySet()) {
                    System.out.println(key + " " + messageCounterMap.get(key));
                }
            }
        });
    }

    private void increaseCounter(String msg) {
        messageCounterMap.putIfAbsent(msg, 0);
        messageCounterMap.put(msg, messageCounterMap.get(msg) + 1);
    }

    private void logIn() {
        name = getName();
        if ((name != null) && (name.length() > 0)) {
            ConnectPacket connectPacket = new ConnectPacket();
            connectPacket.name = name;
            kryoClient.sendTCP(connectPacket);
        } else {
            System.exit(0);
        }
    }

    private void parseMap(String response) {
        String data = response.substring(4);
        String[] arrOfStr = data.split(" ", 0);
        int gridSize = Integer.parseInt(arrOfStr[0]);
        String mapString = arrOfStr[1];
        mapGrid = parseMapString(gridSize, mapString);
        printMap(mapGrid, gridSize);
    }

    private int[][] parseMapString(int gridSize, String mapString) {
        int[][] tempMapGrid = new int[gridSize][gridSize];
        int currentChar = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                tempMapGrid[i][j] = Character.getNumericValue(mapString.charAt(currentChar));
                currentChar++;
            }
        }

        return tempMapGrid;
    }

    private void printMap(int[][] arr, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    private void initUI() {

        frame.setTitle("Donjin Krawler. Player - " + name);
        frame.setSize(screenWidth, screenHeight);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);
        game = new Game(kryoClient, messageLabel, new Player(name), mapGrid);
        frame.add(game);
        frame.setVisible(true);

    }

    private String getName() {
        return JOptionPane.showInputDialog(frame, "Pasirink vardą:", "Vartotojo vardas",
                JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) throws IOException {
        String address;
        if (args.length > 0) {
            address = args[0];
        } else {
            address = InetAddress.getByName("localhost").getHostAddress();
        }
        new Client(address);
    }
}