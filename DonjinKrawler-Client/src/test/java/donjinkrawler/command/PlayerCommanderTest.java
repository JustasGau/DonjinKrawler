package donjinkrawler.command;

import com.esotericsoftware.kryonet.Client;
import donjinkrawler.Player;
import krawlercommon.PlayerData;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerCommanderTest {
    
    @Mock
    donjinkrawler.Client mockClient;

    @Test
    public void testExecute() throws IOException {
        PlayerCommander commander = new PlayerCommander();
        commander.execute(new MoveCommand(new Player(new PlayerData(), new Client(),
                mockClient), 0, 0));
        assertEquals(1, commander.history.size());
    }

    @Test
    public void testUndo1() throws IOException {
        PlayerCommander commander = new PlayerCommander();
        commander.execute(new MoveCommand(new Player(new PlayerData(), new Client(),
                mockClient), 0, 0));
        commander.undo();
        assertEquals(0, commander.history.size());
    }

    @Test
    public void testUndo2() throws IOException {
        PlayerCommander commander = new PlayerCommander();
        commander.execute(new MoveCommand(new Player(new PlayerData(), new Client(),
                mockClient), 0, 0));
        commander.undo();
        assertEquals(0, commander.history.size());

    }
}
