package donjinkrawler.observer;

import donjinkrawler.Player;
import krawlercommon.PlayerData;
import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.big.BigChicken;
import krawlercommon.enemies.small.SmallChicken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class ObserverTest {
    @Mock
    com.esotericsoftware.kryonet.Client client;

    @Mock
    PlayerData playerData;

    public ObserverTest() {
        client = Mockito.mock(com.esotericsoftware.kryonet.Client.class);
        playerData = Mockito.mock(PlayerData.class);
    }

    @Test
    public void attachObserverTest() {
        Player player = new Player(playerData, client);
        Enemy enemy = new SmallChicken();
        player.attachObserver(enemy);
        Assertions.assertTrue(player.getObservers().contains(enemy));
    }

    @Test
    public void detachObserverTest() {
        Player player = new Player(playerData, client);
        Enemy enemy = new SmallChicken();
        player.attachObserver(enemy);
        player.detachObserver(enemy);
        Assertions.assertFalse(player.getObservers().contains(enemy));
    }

    @Test
    public void detachAllObserversTest() {
        Player player = new Player(playerData, client);
        Enemy enemy1 = new SmallChicken();
        Enemy enemy2 = new BigChicken();
        player.attachObserver(enemy1);
        player.attachObserver(enemy2);
        player.detachAllObservers();
        Assertions.assertEquals(0, player.getObservers().size());
    }

    @Test
    public void notifyObserversTest() {
        Player player = new Player(playerData, client);
        Enemy enemy = new SmallChicken();
        player.attachObserver(enemy);
        var initialEnemyStrategy = enemy.getInfo();
        player.notifyObservers();
        Assertions.assertNotSame(initialEnemyStrategy, enemy.getInfo());
    }
}
