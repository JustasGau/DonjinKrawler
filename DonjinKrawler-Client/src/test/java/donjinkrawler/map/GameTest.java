package donjinkrawler.map;

import donjinkrawler.Game;
import donjinkrawler.Player;
import krawlercommon.enemies.EnemyGenerator;
import krawlercommon.enemies.small.SmallEnemyFactory;
import krawlercommon.map.RoomData;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Mock
    com.esotericsoftware.kryonet.Client client;

    @Mock
    JLabel jLabel;

    @Mock
    Player player;

    @Test
    public void testIfEnemiesAreMultiplying() {
        RoomData roomData = new RoomData();
        EnemyGenerator enemyGenerator = new EnemyGenerator(new SmallEnemyFactory());
        roomData.getEnemies().addAll(enemyGenerator.generateRandomEnemies(5));
        Map<Integer, RoomData> rooms = new HashMap<>();
        rooms.put(0, roomData);
        Game game = new Game(client, jLabel, player, rooms, 0, false);
        game.addEnemies(enemyGenerator.generateRandomEnemies(5));
        assertEquals(5, game.getShells().size());
        game.addEnemies(enemyGenerator.generateRandomEnemies(5));
        assertEquals(5, game.getShells().size());
    }
}
