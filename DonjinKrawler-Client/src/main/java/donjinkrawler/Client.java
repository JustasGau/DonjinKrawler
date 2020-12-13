package donjinkrawler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import donjinkrawler.clientpacketcontrol.PacketControlChain;
import krawlercommon.PlayerData;
import krawlercommon.RegistrationManager;
import krawlercommon.map.RoomData;
import krawlercommon.packets.LoginPacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

public final class Client {

    private static final int screenWidth = 500;
    private static final int screenHeight = 530;
    private static final int SERVER_TCP_PORT = 54555;
    private static final int SERVER_UDP_PORT = 54777;
    private final JFrame frame = new JFrame();
    private final JLabel messageLabel = new JLabel("...");
    private final String serverAddress;
    private com.esotericsoftware.kryonet.Client kryoClient;
    private Game game;
    private String name;
    private Map<Integer, RoomData> rooms;

    public Client(String serverAddress) throws IOException {
        this.serverAddress = serverAddress;
        setupKryo();
        logIn();
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

    private void setupKryo() throws IOException {
        Log.set(Log.LEVEL_ERROR);
        kryoClient = new com.esotericsoftware.kryonet.Client(65536, 65536);
        kryoClient.getKryo().setReferences(true);
        RegistrationManager.registerKryo(kryoClient.getKryo());
        kryoClient.start();
        kryoClient.connect(5000, serverAddress, SERVER_TCP_PORT, SERVER_UDP_PORT);

        PacketControlChain packetControlChain = new PacketControlChain();
        Client client = this;

        kryoClient.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                packetControlChain.handle(client, object);
            }
        });
    }

    public void initUI(int currentRoom, PlayerData playerData) {
        frame.setTitle("Donjin Krawler. Player - " + name);
        frame.setSize(screenWidth, screenHeight);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);
        game = new Game(kryoClient, messageLabel, new Player(playerData, kryoClient, this), rooms, currentRoom);
        frame.add(game);
        frame.setVisible(true);
    }

    public void shutDown() {
        JOptionPane.showMessageDialog(frame, "YOU DIED!", "GAME OVER", JOptionPane.PLAIN_MESSAGE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public void kickOut() {
        game.getPlayer().kill();
        JOptionPane.showMessageDialog(frame, "You have been kicked form the game.", "Warning!", JOptionPane.PLAIN_MESSAGE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return this.game;
    }

    public void setMessageLabelText(String text) {
        this.messageLabel.setText(text);
    }

    public void setRooms(Map<Integer, RoomData> rooms) {
        this.rooms = rooms;
    }

    public JFrame getFrame() {
        return this.frame;
    }
}