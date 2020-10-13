package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import krawlercommon.packets.*;
import krawlercommon.RegistrationManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;

public class Client {

    private static final int screenWidth = 500;
    private static final int screenHeight = 500;
    private static final int SERVER_TCP_PORT = 54555;
    private static final int SERVER_UDP_PORT = 54777;

    private com.esotericsoftware.kryonet.Client kryoClient;
    private final JFrame frame = new JFrame();
    private final JLabel messageLabel = new JLabel("...");
    private Game game;
    private final String serverAddress;
    private String name;
    private int[][] mapGrid;

    public Client(String serverAddress) throws IOException {
        setupKryo();
        this.serverAddress = serverAddress;
        logIn();
    }

    private void setupKryo() throws IOException {
        Log.set(Log.LEVEL_DEBUG);
        kryoClient = new com.esotericsoftware.kryonet.Client();
        kryoClient.start();
        kryoClient.connect(5000, serverAddress, SERVER_TCP_PORT, SERVER_UDP_PORT);
        RegistrationManager.registerKryo(kryoClient.getKryo());

        kryoClient.addListener(new Listener() {

            public void received(Connection connection, Object object) {
                if (object instanceof MessagePacket) {
                    handleMessagePacket((MessagePacket) object);
                } else if (object instanceof MapPacket) {
                    parseMap((MapPacket) object);
                } else if (object instanceof IdPacket) {
                    handleIdPacket((IdPacket) object);
                } else if (object instanceof EnemyPacket && game != null) {
                    EnemyPacket enemyPacket = (EnemyPacket) object;
                    game.addEnemies(enemyPacket.getEnemies());
                } else if (object instanceof MoveCharacter && game != null) {
                    MoveCharacter msg = (MoveCharacter) object;
                    game.changeShellPosition(msg);
                } else if (object instanceof CreatePlayerPacket && game != null) {
                    CreatePlayerPacket packet = (CreatePlayerPacket) object;
                    game.addPlayerShell(packet.player);
                } else if (object instanceof DisconnectPacket && game != null) {
                    DisconnectPacket dcPacket = (DisconnectPacket) object;
                    game.deletePlayerShell(dcPacket.id);
                } else if (object instanceof RoomPacket && game != null) {
                    game.changeRoom((RoomPacket) object);
                }
            }
        });
    }

    private void handleIdPacket(IdPacket idPacket) {
        name = idPacket.name;
        initUI(idPacket.id);
    }

    private void initUI(int playerId) {

        frame.setTitle("Donjin Krawler. Player - " + name);
        frame.setSize(screenWidth, screenHeight);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);
        game = new Game(kryoClient, messageLabel, new Player(playerId, name), mapGrid);
        frame.add(game);
        frame.setVisible(true);

    }

    private void handleMessagePacket(MessagePacket messagePacket) {
        if (messagePacket.message.startsWith("MSG")) {
            messageLabel.setText(messagePacket.message.substring(4));
        } else if (messagePacket.message.startsWith("ENI") && game != null) {
            game.updateEnemyInfo(messagePacket.message);
        }
    }

    private void parseMap(MapPacket mapPacket) {
        int gridSize = mapPacket.gridSize;
        mapGrid = parseMapString(gridSize, mapPacket.mapString);
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

    private void logIn() {
        name = getName();
        if ((name != null) && (name.length() > 0)) {
            LoginPacket loginPacket = new LoginPacket();
            loginPacket.name = name;
            if (kryoClient.isConnected()) {
                kryoClient.sendTCP(loginPacket);
            }
        } else {
            System.exit(0);
        }
    }

    private String getName() {
        return JOptionPane.showInputDialog(frame, "Choose name:", "Username",
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