import com.esotericsoftware.kryonet.Client;
import donjinkrawler.Player;
import krawlercommon.PlayerData;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PlayerTest {

    @Test
    public void testSetX()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        player.setX(10);
        assertEquals(10, player.getX());
    }

    @Test
    public void testSetY()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        player.setY(10);
        assertEquals(10, player.getY());
    }

    @Test
    public void testSetCoordinates()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        player.setCoordinates(1,2);
        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
    }

    @Test
    public void testSetHasChangedPosition()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        player.setHasChangedPosition(true);
        assertEquals(true, player.hasChangedPosition());
    }

    @Test
    public void testGetName()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        assertEquals("JhonnyTest", player.getName());
    }

    @Test
    public void testGetImage()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        ImageIcon fake = new ImageIcon(ClassLoader.getSystemResource("craft.png").getFile());
        assertEquals(fake.getImage().getClass().getName(), player.getImage().getClass().getName());
    }

    @Test
    public void testGetAttackImage()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        player.setAttackTimer(11);
        assertNull(player.getAttackImage());
        ImageIcon fake = new ImageIcon(ClassLoader.getSystemResource("craft.png").getFile());
        player.isAttacking(true);
        player.setAttackTimer(1);
        assertEquals(fake.getImage().getClass().getName(), player.getAttackImage().getClass().getName());
    }

    @Test
    public void testGetId()  {
        PlayerData data = new PlayerData("JhonnyTest",69,2,3);
        Player player = new Player(data, new Client());
        assertEquals(69, player.getId());
    }

    @Test
    public void testSetId()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        player.setId(69);
        assertEquals(69, player.getId());
    }

    @Test
    public void testGetData()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        assertEquals(data, player.getData());
    }

    @Test
    public void testGetWidthAndGetHeight()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());

        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("craft.png").getFile());
        Image image = ii.getImage();

        int width = image.getWidth(null);
        int height = image.getHeight(null);

        assertEquals(width, player.getWidth());
        assertEquals(height, player.getHeight());
    }

    @Test
    public void testSetHealth()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        player.setHealth(69);
        assertEquals(69, player.getHealth());
    }

    @Test
    public void testSetHasNotifiedObservers()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        player.setHasNotifiedObservers(true);
        assertEquals(true, player.hasNotifiedObservers());
    }

    @Test
    public void testIncrementTimer()  {
        PlayerData data = new PlayerData("JhonnyTest",1,2,3);
        Player player = new Player(data, new Client());
        player.setAttackTimer(10);
        player.incrementTimer();
        assertEquals(11, player.getAttackTimer());
    }
}
