package donjinkrawler;

import donjinkrawler.decorator.clothes.Clothing;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PlayerShellTest {

    @Test
    public void testLoadImage() throws IOException {
        PlayerShell shell = new PlayerShell("Test");
        shell.loadImage();
        ImageIcon fake = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/craft.png")));
        assertEquals(fake.getImage().getClass().getName(), shell.getImage().getClass().getName());
        ImageIcon fake2 = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/attack.png")));
        shell.setIsAttacking(true);
        assertEquals(fake2.getImage().getClass().getName(), shell.getAttackImage().getClass().getName());
        shell.setIsAttacking(false);
        assertNull(shell.getAttackImage());
    }

    @Test
    public void testAddClothing() {
        PlayerShell shell = new PlayerShell("Test");
        List<Clothing> c = shell.addClothing();
        assertNull(c);
    }

    @Test
    public void testDamage() {
        PlayerShell shell = new PlayerShell("Test");
        shell.damage(10);
        assertEquals(90, shell.getHealth());
    }
}
