package donjinkrawler;

import com.esotericsoftware.kryonet.Client;
import donjinkrawler.facade.MusicMaker;
import donjinkrawler.items.Armor;
import donjinkrawler.items.BaseItem;
import donjinkrawler.items.SpeedPotion;
import donjinkrawler.items.Weapon;
import donjinkrawler.items.tiers.CommonTier;
import krawlercommon.PlayerData;
import krawlercommon.enemies.Boss;
import krawlercommon.items.ArmorData;
import krawlercommon.items.SpeedPotionData;
import krawlercommon.items.WeaponData;
import krawlercommon.iterator.door.DoorCollection;
import krawlercommon.map.*;
import krawlercommon.map.obstacles.Lava;
import krawlercommon.map.obstacles.Slime;
import krawlercommon.map.obstacles.Spikes;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testSetX() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.setX(10);
        assertEquals(10, player.getX());
    }

    @Test
    public void testSetY() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.setY(10);
        assertEquals(10, player.getY());
    }

    @Test
    public void testSetCoordinates() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.setCoordinates(1, 2);
        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
    }

    @Test
    public void testSetHasChangedPosition() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.setHasChangedPosition(true);
        assertEquals(true, player.hasChangedPosition());
    }

    @Test
    public void testGetName() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        assertEquals("JhonnyTest", player.getName());
    }

    @Test
    public void testGetImage() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        InputStream is = getClass().getResourceAsStream("/craft.png");
        ImageIcon fake = new ImageIcon(ImageIO.read(is));
        assertEquals(fake.getImage().getClass().getName(), player.getImage().getClass().getName());
    }

    @Test
    public void testGetAttackImage() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.setAttackTimer(11);
        assertNull(player.getAttackImage());
        InputStream is = getClass().getResourceAsStream("/craft.png");
        ImageIcon fake = new ImageIcon(ImageIO.read(is));
        player.isAttacking(true);
        player.setAttackTimer(1);
        assertEquals(fake.getImage().getClass().getName(), player.getAttackImage().getClass().getName());
    }

    @Test
    public void testGetId() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 69, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        assertEquals(69, player.getId());
    }

    @Test
    public void testSetId() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.setId(69);
        assertEquals(69, player.getId());
    }

    @Test
    public void testGetData() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        assertEquals(data, player.getData());
    }

    @Test
    public void testGetWidthAndGetHeight() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("craft.png").getFile());
        Image image = ii.getImage();

        int width = image.getWidth(null);
        int height = image.getHeight(null);

        assertEquals(width, player.getWidth());
        assertEquals(height, player.getHeight());
    }

    @Test
    public void testSetHealth() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.setHealth(69);
        assertEquals(69, player.getHealth());
    }

    @Test
    public void testSetHasNotifiedObservers() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.setHasNotifiedObservers(true);
        assertEquals(true, player.hasNotifiedObservers());
    }

    @Test
    public void testIncrementTimer() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.setAttackTimer(10);
        player.incrementTimer();
        assertEquals(11, player.getAttackTimer());
    }

    @Test
    public void testTestKeyPressed() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        Inventory.doNotOpenInventory = true;
        KeyEvent e = new KeyEvent(new Button(), KeyEvent.VK_I, System.currentTimeMillis(), 0, KeyEvent.VK_I, 'Z');
        player.keyPressed(e);

        e = new KeyEvent(new Button(), KeyEvent.VK_LEFT, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'Z');
        player.keyPressed(e);

        e = new KeyEvent(new Button(), KeyEvent.VK_RIGHT, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'Z');
        player.keyPressed(e);

        e = new KeyEvent(new Button(), KeyEvent.VK_UP, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'Z');
        player.keyPressed(e);

        e = new KeyEvent(new Button(), KeyEvent.VK_DOWN, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'Z');
        player.keyPressed(e);

        e = new KeyEvent(new Button(), KeyEvent.VK_U, System.currentTimeMillis(), 0, KeyEvent.VK_U, 'Z');
        player.keyPressed(e);

        MusicMaker.doNotPlayMusic = true;
        e = new KeyEvent(new Button(), KeyEvent.VK_M, System.currentTimeMillis(), 0, KeyEvent.VK_M, 'Z');
        player.keyPressed(e);

        e = new KeyEvent(new Button(), KeyEvent.VK_SPACE, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, 'Z');
        player.keyPressed(e);

        assertTrue(true);
    }

    @Test
    public void testGetDamage() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        assertEquals(5, player.getDamage());
    }

    @Test
    public void testFindTarget() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.isAttacking(true);
        player.findTarget();
        assertTrue(true);
    }

    @Test
    public void testTestKeyReleased() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        KeyEvent e = new KeyEvent(new Button(), KeyEvent.VK_LEFT, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'Z');
        player.keyReleased(e);

        e = new KeyEvent(new Button(), KeyEvent.VK_RIGHT, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'Z');
        player.keyReleased(e);

        e = new KeyEvent(new Button(), KeyEvent.VK_UP, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'Z');
        player.keyReleased(e);

        e = new KeyEvent(new Button(), KeyEvent.VK_DOWN, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'Z');
        player.keyReleased(e);

        e = new KeyEvent(new Button(), KeyEvent.VK_U, System.currentTimeMillis(), 0, KeyEvent.VK_U, 'Z');
        player.keyReleased(e);

        assertTrue(true);
    }

    @Test
    public void testAttachAndDetachObserver() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        Boss newBaus = new Boss();

        player.attachObserver(newBaus);
        assertTrue(player.getObservers().contains(newBaus));

        player.detachObserver(newBaus);
        assertFalse(player.getObservers().contains(newBaus));
    }

    @Test
    public void testDetachAllObservers() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        Boss boss1 = new Boss();
        Boss boss2 = new Boss();

        player.attachObserver(boss1);
        player.attachObserver(boss2);

        player.detachAllObservers();
        assertFalse(player.getObservers().contains(boss1));
        assertFalse(player.getObservers().contains(boss2));
    }

    @Test
    public void testNotifyObservers() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.attachObserver(new Boss());
        player.attachObserver(new Boss());
        player.notifyObservers();
        assertTrue(true);
    }

    @Test
    public void testGetBotX() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.getBotX();
        assertTrue(true);
    }

    @Test
    public void testGetBotY() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));
        player.getBotY();
        assertTrue(true);
    }

    @Test
    public void testMove() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        List<Wall> walls = new ArrayList<>();
        DoorCollection doors = new DoorCollection();
        List<Obstacle> obstacles = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();
        HashMap<Integer, BaseItem> items = new HashMap<>();

        player.move(walls, doors, obstacles, decorations, items);
        assertTrue(true);
    }

    @Test
    public void testCollideWithWall() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        List<Wall> walls = new ArrayList<>();
        DoorCollection doors = new DoorCollection();
        List<Obstacle> obstacles = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();
        HashMap<Integer, BaseItem> items = new HashMap<>();

        walls.add(new Wall(2, 3, 5, 5));

        assertNull(player.move(walls, doors, obstacles, decorations, items));
    }

    @Test
    public void testCollideWithObstacle() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        List<Wall> walls = new ArrayList<>();
        DoorCollection doors = new DoorCollection();
        List<Obstacle> obstacles = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();
        HashMap<Integer, BaseItem> items = new HashMap<>();

        obstacles.add(new Lava(2, 3, 5, 5));

        assertNull(player.move(walls, doors, obstacles, decorations, items));
    }

    @Test
    public void testCollideWithLavaObstacle() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        List<Wall> walls = new ArrayList<>();
        DoorCollection doors = new DoorCollection();
        List<Obstacle> obstacles = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();
        HashMap<Integer, BaseItem> items = new HashMap<>();

        Obstacle o = new Lava(2, 3, 5, 5);
        obstacles.add(o);

        assertNull(player.move(walls, doors, obstacles, decorations, items));
    }

    @Test
    public void testCollideWithSpikesObstacle() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        List<Wall> walls = new ArrayList<>();
        DoorCollection doors = new DoorCollection();
        List<Obstacle> obstacles = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();
        HashMap<Integer, BaseItem> items = new HashMap<>();

        Obstacle o = new Spikes(2, 3, 5, 5);
        obstacles.add(o);

        assertNull(player.move(walls, doors, obstacles, decorations, items));
    }

    @Test
    public void testCollideWithSlimeObstacle() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        List<Wall> walls = new ArrayList<>();
        DoorCollection doors = new DoorCollection();
        List<Obstacle> obstacles = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();
        HashMap<Integer, BaseItem> items = new HashMap<>();

        Obstacle o = new Slime(2, 3, 5, 5);
        obstacles.add(o);

        assertNull(player.move(walls, doors, obstacles, decorations, items));
    }

    @Test
    public void testCollideWithDoor() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        List<Wall> walls = new ArrayList<>();
        DoorCollection doors = new DoorCollection();
        List<Obstacle> obstacles = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();
        HashMap<Integer, BaseItem> items = new HashMap<>();

        doors.add(new Door(DoorDirection.TOP, 2, 3));

        assertNull(player.move(walls, doors, obstacles, decorations, items));
    }

    @Test
    public void testCollideWithItem() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        List<Wall> walls = new ArrayList<>();
        DoorCollection doors = new DoorCollection();
        List<Obstacle> obstacles = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();
        HashMap<Integer, BaseItem> items = new HashMap<>();

        items.put(1, new SpeedPotion(new SpeedPotionData(1, 2, 3)));

        assertEquals(1, player.move(walls, doors, obstacles, decorations, items));
    }

    @Test
    public void testCollideWithArmor() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        List<Wall> walls = new ArrayList<>();
        DoorCollection doors = new DoorCollection();
        List<Obstacle> obstacles = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();
        HashMap<Integer, BaseItem> items = new HashMap<>();

        items.put(1, new Armor(new ArmorData(1, 2, 3), new CommonTier(), 10, 15, 20));

        assertEquals(1, player.move(walls, doors, obstacles, decorations, items));
    }

    @Test
    public void testCollideWithWeapon() throws IOException {
        PlayerData data = new PlayerData("JhonnyTest", 1, 2, 3);
        Player player = new Player(data, new Client(), new donjinkrawler.Client(".."));

        List<Wall> walls = new ArrayList<>();
        DoorCollection doors = new DoorCollection();
        List<Obstacle> obstacles = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();
        HashMap<Integer, BaseItem> items = new HashMap<>();

        items.put(1, new Weapon(new WeaponData(1, 2, 3), new CommonTier(), 10, 15));

        assertEquals(1, player.move(walls, doors, obstacles, decorations, items));
    }
}
