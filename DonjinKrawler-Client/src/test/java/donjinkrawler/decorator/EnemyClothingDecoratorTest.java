package donjinkrawler.decorator;

import donjinkrawler.EnemyShell;
import donjinkrawler.decorator.clothes.Clothing;
import donjinkrawler.flyweight.EnemyFlyweight;
import donjinkrawler.flyweight.EnemySelector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class EnemyClothingDecoratorTest {

    private EnemyShell getEnemyObject(String name, int id, int x, int y) {
        EnemyFlyweight type = EnemySelector.getEnemyType(name);
        EnemyShell enemy = new EnemyShell(name, id, x, y, type);
        return enemy;
    }

    @Test
    public void testInit() {
        EnemyShell initialEnemy = getEnemyObject("test", 1, 10, 20);
        SombrerosEnemy test = new SombrerosEnemy(initialEnemy);
        assertSame(initialEnemy, test.wrappee);
    }

    @Test
    public void testGetX() {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("test", 1, 10, 20));
        assertEquals(10, test.getX());
    }

    @Test
    public void testGetY() {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("test", 1, 10, 20));
        assertEquals(20, test.getY());
    }

    @Test
    public void testName() {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("test", 1, 10, 20));
        assertEquals("test", test.getName());
    }

    @Test
    public void testSetX() {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("test", 1, 10, 20));
        test.setX(100);
        assertEquals(100, test.getX());
    }

    @Test
    public void testSetY() {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("test", 1, 10, 20));
        test.setY(100);
        assertEquals(100, test.getY());
    }

    @Test
    public void testGetId() {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("test", 1, 10, 20));
        assertEquals(1, test.getID());
    }

    @Test
    public void testGetInfo() {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("test", 1, 10, 20));
        assertEquals("No strategy", test.getInfo());
    }

    @Test
    public void testSetInfo() {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("test", 1, 10, 20));
        test.setInfo("Test info");
        assertEquals("Test info", test.getInfo());
    }

    @Test
    public void testGetHealth() {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("test", 1, 10, 20));
        assertEquals(100, test.getHealth());
    }

    @Test
    public void testDamage() {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("test", 1, 10, 20));
        test.damage(20);
        assertEquals(80, test.getHealth());
    }

    @Test
    public void testSetHealth() {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("test", 1, 10, 20));
        test.setHealth(200);
        assertEquals(200, test.getHealth());
    }

    @Test
    @Disabled
    public void testGetImage() throws IOException {
        SombrerosEnemy test = new SombrerosEnemy(getEnemyObject("Big-Zombie", 1, 10, 20));
        ImageIcon ii = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/zombie-big.png")));
        Image image = ii.getImage();
        assertEquals(image, test.getImage());
    }

    @Test
    public void testAddClothing() {
        MaracasEnemy test = new MaracasEnemy(getEnemyObject("Big-Zombie", 1, 10, 20));
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("maracas.png").getFile());
        List<Clothing> clothes = test.addClothing();
        assertEquals(1, clothes.size());
    }
}
