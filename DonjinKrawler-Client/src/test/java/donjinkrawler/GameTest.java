package donjinkrawler;

import krawlercommon.PlayerData;
import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.EnemyGenerator;
import krawlercommon.enemies.big.BigChicken;
import krawlercommon.enemies.small.SmallEnemyFactory;
import krawlercommon.map.RoomData;
import krawlercommon.map.RoomType;
import krawlercommon.packets.MoveCharacter;
import krawlercommon.packets.RoomPacket;
import krawlercommon.strategies.Attack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class GameTest {

    @Mock
    com.esotericsoftware.kryonet.Client client;

    @Mock
    JLabel jLabel;

    Player fakePlayer;

    @Mock
    Graphics mockGraphics;

    RoomData testRoomData;

    Map<Integer, RoomData> testRooms;


    @BeforeEach
    public void setup() throws CloneNotSupportedException {
        testRoomData = new RoomData();
        EnemyGenerator enemyGenerator = new EnemyGenerator(new SmallEnemyFactory());
        testRoomData.getEnemies().addAll(enemyGenerator.generateRandomEnemies(5));
        testRoomData.setTileTexture(1);
        testRoomData.setRoomType(RoomType.NORMAL);
        testRooms = new HashMap<>();
        testRooms.put(0, testRoomData);
        RoomData anotherRoom = testRoomData.clone();
        anotherRoom.setLeft(testRoomData);
        anotherRoom.setTop(anotherRoom);
        anotherRoom.setRight(anotherRoom);
        anotherRoom.setBottom(anotherRoom);
        testRoomData.setRight(anotherRoom);
        testRooms.put(1, anotherRoom);
        mockGraphics = mock(Graphics2D.class);
        when(mockGraphics.create()).thenReturn(mockGraphics);
        PlayerData playerData = new PlayerData("Arvydas", 1, 250, 250);
        client = mock(com.esotericsoftware.kryonet.Client.class);
        fakePlayer = new Player(playerData, client);
        when(client.sendTCP(any())).thenReturn(1);
    }

    @AfterEach
    public void teardown() {
        Game.shells.clear();
    }

    @Test
    public void testIfEnemiesAreMultiplying() {
        RoomData roomData = new RoomData();
        EnemyGenerator enemyGenerator = new EnemyGenerator(new SmallEnemyFactory());
        roomData.getEnemies().addAll(enemyGenerator.generateRandomEnemies(5));
        Map<Integer, RoomData> rooms = new HashMap<>();
        rooms.put(0, roomData);
        Game game = new Game(client, jLabel, fakePlayer, rooms, 0, false);
        game.getShells().clear();
        game.addEnemies(enemyGenerator.generateRandomEnemies(5));
        assertEquals(5, game.getShells().size());
        game.addEnemies(enemyGenerator.generateRandomEnemies(5));
        assertEquals(5, game.getShells().size());
    }

    @Test
    public void testPlayerDrawing() {
        Game game = new Game(client, jLabel, fakePlayer, testRooms, 0, false);
        game.paintComponent(mockGraphics);
        verifyPlayerDrawn(game);
    }

    private void verifyPlayerDrawn(Game game) {
        Mockito.verify(mockGraphics).drawImage(any(), eq(250), eq(250), eq(game));
        Mockito.verify(mockGraphics).drawImage(any(), eq(240), eq(240), eq(game));
        Mockito.verify(mockGraphics).setColor(eq(Color.RED));
        Mockito.verify(mockGraphics).drawString(eq("Arvydas"), eq(250), eq(280));
    }

    @Test
    public void testEnemyShellDrawing() {
        Game game = new Game(client, jLabel, fakePlayer, testRooms, 0, false);
        AbstractShellInterface fakeShell = new EnemyShell("Enemy", 69420, 200, 200);
        fakeShell.setInfo("Hello world!");
        game.getShells().put(69420, fakeShell);
        game.getShells().put(42, new PlayerShell("AnotherPlayer"));
        game.paintComponent(mockGraphics);
        verifyShellDrawn(game);
    }

    private void verifyShellDrawn(Game game) {
        Mockito.verify(mockGraphics, atLeast(2)).drawImage(any(), eq(250), eq(250), eq(game));
        Mockito.verify(mockGraphics, atLeast(1)).setColor(eq(Color.BLUE));
        Mockito.verify(mockGraphics).drawString(eq("Enemy 69420"), eq(200), eq(200 + 30));
        Mockito.verify(mockGraphics).drawImage(any(), eq(190), eq(190), eq(game));
        Mockito.verify(mockGraphics, atLeast(1)).setColor(eq(Color.YELLOW));
        Mockito.verify(mockGraphics).drawString(eq("Hello world!"), eq(200), eq(200 + 30 + 20));
    }

    @Test
    public void testGameUpdateWhenPlayerInDoor() {
        fakePlayer.setCoordinates(490, 250);
        PlayerShell secondPlayer = new PlayerShell("AnotherPlayer");
        Game game = new Game(client, jLabel, fakePlayer, testRooms, 0, false);
        game.getShells().put(42, secondPlayer);
        game.actionPerformed(mock(ActionEvent.class));
        assertEquals(40, fakePlayer.getX());
        assertEquals(250, fakePlayer.getY());
        assertEquals(40, secondPlayer.getX());
        assertEquals(250, secondPlayer.getY());
        assertEquals(testRooms.get(1), game.getCurrentRoom().getRoomData());
    }

    @Test
    public void testAddPlayerShell() {
        Game game = new Game(client, jLabel, fakePlayer, testRooms, 0, false);
        PlayerData playerData = new PlayerData("AnotherPlayer", 69, 250, 480);
        game.addPlayerShell(playerData);
        assertEquals(1, game.getShells().size());
        assertEquals(250, game.getShells().get(69).getX());
        assertEquals(480, game.getShells().get(69).getY());
    }

    @Test
    public void testRoomChange() {
        RoomPacket roomPacket = new RoomPacket();
        roomPacket.direction = "RIGHT";
        Game game = new Game(client, jLabel, fakePlayer, testRooms, 0, false);
        game.changeRoom(roomPacket);
        assertEquals(40, fakePlayer.getX());
        assertEquals(250, fakePlayer.getY());
        roomPacket.direction = "TOP";
        game.changeRoom(roomPacket);
        assertEquals(250, fakePlayer.getX());
        assertEquals(400, fakePlayer.getY());
        roomPacket.direction = "BOTTOM";
        game.changeRoom(roomPacket);
        assertEquals(250, fakePlayer.getX());
        assertEquals(40, fakePlayer.getY());
        roomPacket.direction = "LEFT";
        game.changeRoom(roomPacket);
        assertEquals(400, fakePlayer.getX());
        assertEquals(250, fakePlayer.getY());
    }

    @Test
    public void testUpdateEnemyStrategy() {
        Game game = new Game(client, jLabel, fakePlayer, testRooms, 0, false);
        AbstractShellInterface fakeShell = new EnemyShell("Enemy", 69420, 200, 200);
        fakeShell.setInfo("Hello world!");
        game.getShells().put(69420, fakeShell);
        game.updateEnemyStrategy(69420, new Attack());
        assertEquals("Attack", game.getShells().get(69420).getInfo());
    }

    @Test
    public void testUpdateEnemyInfo() {
        Game game = new Game(client, jLabel, fakePlayer, testRooms, 0, false);
        AbstractShellInterface fakeShell = new EnemyShell("Enemy", 69420, 200, 200);
        fakeShell.setInfo("Hello world!");
        game.getShells().put(69420, fakeShell);
        game.updateEnemyInfo("ENI 69420 MoveTowardPlayer");
        assertEquals("MoveTowardPlayer", game.getShells().get(69420).getInfo());
    }

    @Test
    public void testUpdateEnemies() {
        Game game = new Game(client, jLabel, fakePlayer, testRooms, 0, false);
        Enemy fakeEnemy = new BigChicken();
        AbstractShellInterface fakeShell = new EnemyShell("Enemy", 69420, 200, 200);
        fakeShell.setInfo("Hello world!");
        game.getShells().put(fakeEnemy.getID(), fakeShell);
        List<Enemy> enemyList = List.of(fakeEnemy);
        fakeEnemy.setX(300);
        fakeEnemy.setY(300);
        fakeEnemy.setHealth(70);
        game.updateEnemies(enemyList);
        assertEquals(300, fakeShell.getX());
        assertEquals(300, fakeShell.getY());
        assertEquals(70, fakeShell.getHealth());
    }

    @Test
    public void testUpdateShellPos() {
        MoveCharacter packet = new MoveCharacter();
        packet.x = 300;
        packet.y = 350;
        packet.id = 69420;
        Game game = new Game(client, jLabel, fakePlayer, testRooms, 0, false);
        AbstractShellInterface fakeShell = new EnemyShell("Enemy", 69420, 200, 200);
        game.getShells().put(69420, fakeShell);
        game.changeShellPosition(packet);
        assertEquals(300, fakeShell.getX());
        assertEquals(350, fakeShell.getY());
    }

    @Test
    public void testDrawPlayerAttack() {
        Game game = new Game(client, jLabel, fakePlayer, testRooms, 0, false);
        AbstractShellInterface fakeShell = new PlayerShell("Player");
        game.getShells().put(69420, fakeShell);
        game.drawPlayerAttack(69420);
        assertTrue(fakeShell.isAttacking());
    }
}
