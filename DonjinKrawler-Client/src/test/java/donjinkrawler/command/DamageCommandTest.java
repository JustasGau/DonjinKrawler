package donjinkrawler.command;

import com.esotericsoftware.kryonet.Client;
import donjinkrawler.Player;
import krawlercommon.PlayerData;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DamageCommandTest {
    
    @Mock
    donjinkrawler.Client mockClient;

    @Test
    public void testDamageCommandConstructor() throws IOException {
        Player testPlayer = new Player(new PlayerData(), new Client(), mockClient);
        DamageCommand dmg = new DamageCommand(testPlayer, 20);
        assertEquals(20, dmg.damage);
    }

    @Test
    public void testDamageCommandExecute() throws IOException {
        PlayerCommander commander = new PlayerCommander();
        Player testPlayer = new Player(new PlayerData(), new Client(), mockClient);
        testPlayer.setHealth(100);
        DamageCommand dmg = new DamageCommand(testPlayer, 20);
        commander.execute(dmg);
        assertEquals(80, testPlayer.getHealth());
    }

    @Test
    public void testDamageCommandUndo() throws IOException {
        PlayerCommander commander = new PlayerCommander();
        Player testPlayer = new Player(new PlayerData(), new Client(), mockClient);
        testPlayer.setHealth(100);
        DamageCommand dmg = new DamageCommand(testPlayer, 20);
        commander.execute(dmg);
        commander.undo();
        assertEquals(100, testPlayer.getHealth());
    }
}
