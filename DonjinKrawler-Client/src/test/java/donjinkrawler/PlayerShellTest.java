package donjinkrawler;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PlayerShellTest {

    @Test
    public void testLoadImage() {
        PlayerShell shell = new PlayerShell("Test");
        shell.loadImage();
        ImageIcon fake = new ImageIcon(ClassLoader.getSystemResource("craft.png").getFile());
        assertEquals(fake.getImage().getClass().getName(), shell.getImage().getClass().getName());
        ImageIcon fake2 = new ImageIcon(ClassLoader.getSystemResource("attack.png").getFile());
        shell.isAttacking(true);
        assertEquals(fake2.getImage().getClass().getName(), shell.getAttackImage().getClass().getName());
        shell.isAttacking(false);
        assertNull(shell.getAttackImage());
    }

    @Test
    public void testAddClothing() {
        PlayerShell shell = new PlayerShell("Test");
        Map<String, ImageIcon> c = shell.addClothing();
        assertNull(c);
    }

    @Test
    public void testDamage() {
        PlayerShell shell = new PlayerShell("Test");
        shell.damage(10);
        assertEquals(90, shell.getHealth());
    }
}
