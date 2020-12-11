package donjinkrawler;

import com.esotericsoftware.kryonet.Client;
import donjinkrawler.config.ConfigSingleton;
import krawlercommon.ConnectionManager;
import krawlercommon.RegistrationManager;
import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.big.BigChicken;
import krawlercommon.packets.*;
import krawlercommon.strategies.MoveTowardPlayer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Run tests only from the root of the test, otherwise they will fail because of setup issues.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
public class GameServerTest {

    private static GameServer gameServer;
    private static Client kryoClient;

    @BeforeAll
    public static void setup() {
        ConfigSingleton.getInstance().setTestProperty("krawler.useProxy", "false");
        gameServer = new GameServer();
        kryoClient = new com.esotericsoftware.kryonet.Client(32768, 32768);
    }

    @AfterAll
    public static void teardown() {
        ConfigSingleton.getInstance().resetProperties();
        gameServer.kryoServer.close();
        ConnectionManager.getInstance().resetConnectionManager();
    }

    @Test
    @Order(1)
    public void testStartServer() throws IOException {
        gameServer.startServer();
        assertTrue(gameServer.kryoServer.getKryo().getReferences());
        assertTrue(gameServer.kryoServer.getKryo().getNextRegistrationId() > 0);
        assertTrue(gameServer.rooms.size() > 0);
        assertNotNull(gameServer.rooms.get(0));
        kryoClient.getKryo().setReferences(true);
        kryoClient.start();
        kryoClient.connect(500000, "localhost", gameServer.KRYO_TCP_PORT, gameServer.KRYO_UDP_PORT);
        RegistrationManager.registerKryo(kryoClient.getKryo());
    }

    @Test
    @Order(2)
    public void testLoginPacket() throws InterruptedException {
        LoginPacket loginPacket = new LoginPacket();
        loginPacket.name = "TestUser";
        int initialId = ConnectionManager.getInstance().getPlayerIDs();
        kryoClient.sendUDP(loginPacket);
        // wait until packet is handled
        Thread.sleep(2000);
        assertTrue(ConnectionManager.getInstance().getAllPlayers().size() > 0);
        assertNotNull(ConnectionManager.getInstance().getAllPlayers().get(0));
        assertTrue(ConnectionManager.getInstance().getAllPlayers().get(0).getName().length() > 0);
    }

    @Test
    @Order(3)
    public void testPosUpdatePacket() throws InterruptedException {
        MoveCharacter posPacket = new MoveCharacter();
        posPacket.x = 250;
        posPacket.y = 250;
        posPacket.id = 1;
        kryoClient.sendUDP(posPacket);
        // wait until packet is handled
        Thread.sleep(1000);
        assertNotNull(ConnectionManager.getInstance().getAllPlayers().get(0));
        assertEquals(250, ConnectionManager.getInstance().getAllPlayers().get(0).getX());
        assertEquals(250, ConnectionManager.getInstance().getAllPlayers().get(0).getY());
    }

    @Test
    @Order(3)
    public void testRoomChange() throws InterruptedException {
        RoomPacket roomPacket = new RoomPacket();
        roomPacket.id = 5;
        kryoClient.sendUDP(roomPacket);
        // wait until packet is handled
        Thread.sleep(1000);
        assertEquals(5, gameServer.currentRoom);
    }

    @Test
    @Order(3)
    public void testEnemyStrategyChange() throws InterruptedException {
        ChangeEnemyStrategyPacket packet = new ChangeEnemyStrategyPacket();
        Enemy newEnemy = new BigChicken();
        packet.id = newEnemy.getID();
        packet.strategy = new MoveTowardPlayer();
        gameServer.rooms.get(gameServer.currentRoom).getEnemies().add(newEnemy);
        kryoClient.sendUDP(packet);
        // wait until packet is handled
        Thread.sleep(1000);

        Enemy enemy = gameServer.rooms.get(gameServer.currentRoom).getEnemies().stream()
                .filter(e -> e.getID() == newEnemy.getID())
                .collect(Collectors.toList()).get(0);
        assertEquals(Enemy.Phases.TOWARDS, enemy.getStrategyPhase());
    }

    @Test
    @Order(3)
    public void testPlayerDamageEnemy() throws InterruptedException {
        DamageEnemyPacket packet = new DamageEnemyPacket();
        Enemy newEnemy = new BigChicken();
        packet.id = newEnemy.getID();
        packet.damage = 7.5;
        gameServer.rooms.get(gameServer.currentRoom).getEnemies().add(newEnemy);
        kryoClient.sendUDP(packet);
        // wait until packet is handled
        Thread.sleep(1000);

        Enemy enemy = gameServer.rooms.get(gameServer.currentRoom).getEnemies().stream()
                .filter(e -> e.getID() == newEnemy.getID())
                .collect(Collectors.toList()).get(0);
        assertEquals(92.5, enemy.getHealth());
    }

    @Test
    @Order(4)
    public void testDisconnect() throws InterruptedException {
        kryoClient.close();
        // wait until packet is handled
        Thread.sleep(1000);
        assertEquals(0, ConnectionManager.getInstance().getAllPlayers().size());
    }
}
