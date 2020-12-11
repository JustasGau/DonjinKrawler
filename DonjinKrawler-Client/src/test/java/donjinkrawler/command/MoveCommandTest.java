package donjinkrawler.command;

import com.esotericsoftware.kryonet.Client;
import donjinkrawler.Player;
import krawlercommon.PlayerData;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveCommandTest {

    @Mock
    donjinkrawler.Client mockClient;

    @Test
    public void testMoveCommandConstructor1() throws IOException {
        Player testPlayer = new Player(new PlayerData(), new Client(), mockClient);
        MoveCommand mv = new MoveCommand(testPlayer, 30, 20);
        assertEquals(30, mv.dx);
        assertEquals(20, mv.dy);
    }

    @Test
    public void testMoveCommandConstructor2() throws IOException {
        Player testPlayer = new Player(new PlayerData(), new Client(), mockClient);
        testPlayer.setX(50);
        testPlayer.setY(60);
        MoveCommand mv = new MoveCommand(testPlayer, 30, 20);
        assertEquals(50, mv.oldX);
        assertEquals(60, mv.oldY);
    }

    @Test
    public void testMoveCommandExecute() throws IOException {
        PlayerCommander commander = new PlayerCommander();
        Player testPlayer = new Player(new PlayerData(), new Client(), mockClient);
        testPlayer.setX(100);
        testPlayer.setY(200);
        MoveCommand mv = new MoveCommand(testPlayer, 30, 20);
        commander.execute(mv);
        assertEquals(130, testPlayer.getX());
        assertEquals(220, testPlayer.getY());
    }

    @Test
    public void testMoveCommandUndo() throws IOException {
        PlayerCommander commander = new PlayerCommander();
        Player testPlayer = new Player(new PlayerData(), new Client(), mockClient);
        testPlayer.setX(100);
        testPlayer.setY(200);
        MoveCommand mv = new MoveCommand(testPlayer, 30, 20);
        commander.execute(mv);
        commander.undo();
        assertEquals(100, testPlayer.getX());
        assertEquals(200, testPlayer.getY());
    }
}
