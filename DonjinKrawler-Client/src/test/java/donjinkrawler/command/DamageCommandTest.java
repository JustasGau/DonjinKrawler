package donjinkrawler.command;

import com.esotericsoftware.kryonet.Client;
import donjinkrawler.Player;
import krawlercommon.PlayerData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DamageCommandTest {

    @Test
    public void testDamageCommandConstructor() {
        Player testPlayer = new Player(new PlayerData(), new Client());
        DamageCommand dmg = new DamageCommand(testPlayer, 20);
        assertEquals(20, dmg.damage);
    }

    @Test
    public void testDamageCommandExecute() {
        PlayerCommander commander = new PlayerCommander();
        Player testPlayer = new Player(new PlayerData(), new Client());
        testPlayer.setHealth(100);
        DamageCommand dmg = new DamageCommand(testPlayer, 20);
        commander.execute(dmg);
        assertEquals(80, testPlayer.getHealth());
    }

    @Test
    public void testDamageCommandUndo() {
        PlayerCommander commander = new PlayerCommander();
        Player testPlayer = new Player(new PlayerData(), new Client());
        testPlayer.setHealth(100);
        DamageCommand dmg = new DamageCommand(testPlayer, 20);
        commander.execute(dmg);
        commander.undo();
        assertEquals(100, testPlayer.getHealth());
    }
}
