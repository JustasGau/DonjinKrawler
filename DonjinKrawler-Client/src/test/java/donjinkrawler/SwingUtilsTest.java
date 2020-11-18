package donjinkrawler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;

public class SwingUtilsTest {

    Graphics2D graphics;

    @BeforeEach
    public void setUp() {
        graphics = mock(Graphics2D.class);
    }

    @Test
    public void testDrawGreenHealthBar() {
        SwingUtils.drawHealthBar(graphics, 250, 250, 10, 10, 100);
        Mockito.verify(graphics, atMost(2)).fill(any());
        Mockito.verify(graphics).setColor(Color.GREEN);
    }

    @Test
    public void testDrawYellowHealthBar() {
        SwingUtils.drawHealthBar(graphics, 250, 250, 10, 10, 50);
        Mockito.verify(graphics, atMost(2)).fill(any());
        Mockito.verify(graphics).setColor(Color.YELLOW);
    }

    @Test
    public void testDrawRedHealthBar() {
        SwingUtils.drawHealthBar(graphics, 250, 250, 10, 10, 10);
        Mockito.verify(graphics, atMost(2)).fill(any());
        Mockito.verify(graphics).setColor(Color.RED);
    }
}
