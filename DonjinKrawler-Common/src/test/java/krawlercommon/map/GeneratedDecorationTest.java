package krawlercommon.map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedDecorationTest {
    @Test
    public void testSetImageNumber() {
        Decoration decoration = new Decoration();
        decoration.setImageNumber(10);
        assertEquals(10, decoration.getImageNumber());
    }
}

