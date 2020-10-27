package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import krawlercommon.PlayerData;
import krawlercommon.map.RoomData;
import krawlercommon.packets.*;
import krawlercommon.RegistrationManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

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
    private Map<Integer, RoomData> rooms;

    public Client(String serverAddress) throws IOException {
        setupKryo();
        this.serverAddress = serverAddress;
        logIn();
    }

    private void setupKryo() throws IOException {
        Log.set(Log.LEVEL_TRACE);
        kryoClient = new com.esotericsoftware.kryonet.Client(32768, 32768);
        kryoClient.getKryo().setReferences(true);
        kryoClient.start();
        kryoClient.connect(5000, serverAddress, SERVER_TCP_PORT, SERVER_UDP_PORT);
        RegistrationManager.registerKryo(kryoClient.getKryo());

        kryoClient.addListener(new Listener() {

            public void received(Connection connection, Object object) {
                if (object instanceof MessagePacket) {
                    handleMessagePacket((MessagePacket) object);
                } else if (object instanceof MapPacket) {
                    MapPacket mapPacket = (MapPacket) object;
                    rooms = mapPacket.rooms;
                } else if (object instanceof IdPacket) {
                    handleIdPacket((IdPacket) object);
                } else if (object instanceof MoveCharacter && game != null) {
                    MoveCharacter msg = (MoveCharacter) object;
                    game.changeShellPosition(msg);
                } else if (object instanceof EnemyPacket && game != null) {
                    EnemyPacket enemyPacket = (EnemyPacket) object;
                    if (enemyPacket.isUpdate())
                        game.updateEnemies(enemyPacket.getEnemies());
                    else
                        game.addEnemies(enemyPacket.getEnemies());
                } else if (object instanceof CreatePlayerPacket && game != null) {
                    CreatePlayerPacket packet = (CreatePlayerPacket) object;
                    game.addPlayerShell(packet.player);
                } else if (object instanceof DisconnectPacket && game != null) {
                    DisconnectPacket dcPacket = (DisconnectPacket) object;
                    game.deletePlayerShell(dcPacket.id);
                } else if (object instanceof RoomPacket && game != null) {
                    game.changeRoom((RoomPacket) object);
                } else if (object instanceof ChangeEnemyStrategyPacket && game != null) {
                    ChangeEnemyStrategyPacket enemy = (ChangeEnemyStrategyPacket) object;
                    game.updateEnemyStrategy(enemy.id, enemy.strategy);
                } else if (object instanceof CharacterAttackPacket && game != null) {
                    CharacterAttackPacket character = (CharacterAttackPacket) object;
                    game.drawPlayerAttack(character.id);
                }
            }
        });
    }

    private void handleIdPacket(IdPacket idPacket) {
        name = idPacket.playerData.getName();
        initUI(idPacket.currentRoom, idPacket.playerData);
    }

    private void initUI(int currentRoom, PlayerData playerData) {

        frame.setTitle("Donjin Krawler. Player - " + name);
        frame.setSize(screenWidth, screenHeight);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);
        game = new Game(kryoClient, messageLabel, new Player(playerData, kryoClient), rooms, currentRoom);
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