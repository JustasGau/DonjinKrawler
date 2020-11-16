package donjinkrawler;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AbstractShellTest {

    @Test
    public void testSetX() {
        PlayerShell shell = new PlayerShell("Test");
        shell.setX(9);
        assertEquals(9, shell.getX());
    }

    @Test
    public void testSetY() {
        PlayerShell shell = new PlayerShell("Test");
        shell.setY(10);
        assertEquals(10, shell.getY());
    }

    @Test
    public void testSetInfo() {
        PlayerShell shell = new PlayerShell("Test");
        shell.setInfo("test-info");
        assertEquals("test-info", shell.getInfo());
    }

    @Test
    public void testSetHealth() {
        PlayerShell shell = new PlayerShell("Test");
        shell.setHealth(70);
        assertEquals(70, shell.getHealth());
    }
}
