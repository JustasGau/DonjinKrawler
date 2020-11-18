package krawlercommon.map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedWallTest {
    @Test
    public void testConstructor() {
        Wall actualWall = new Wall(2, 3, 1, 1);
        assertEquals("1", actualWall.getTexture());
        assertEquals(3, actualWall.getY());
        assertEquals(2, actualWall.getX());
        assertEquals(1, actualWall.getHeight());
        assertEquals(1, actualWall.getWidth());
    }

    @Test
    public void testConstructor2() {
        Wall actualWall = new Wall(2, 3, 1, 1, "Texture");
        assertEquals("Texture", actualWall.getTexture());
        assertEquals(3, actualWall.getY());
        assertEquals(2, actualWall.getX());
        assertEquals(1, actualWall.getHeight());
        assertEquals(1, actualWall.getWidth());
    }

    @Test
    public void testConstructor3() {
        Wall actualWall = new Wall(2, 3, 1, 1, "");
        assertEquals("1", actualWall.getTexture());
        assertEquals(3, actualWall.getY());
        assertEquals(2, actualWall.getX());
        assertEquals(1, actualWall.getHeight());
        assertEquals(1, actualWall.getWidth());
    }

    @Test
    public void testSetX() {
        Wall wall = new Wall();
        wall.setX(2);
        assertEquals(2, wall.getX());
    }

    @Test
    public void testSetY() {
        Wall wall = new Wall();
        wall.setY(3);
        assertEquals(3, wall.getY());
    }

    @Test
    public void testSetWidth() {
        Wall wall = new Wall();
        wall.setWidth(1);
        assertEquals(1, wall.getWidth());
    }

    @Test
    public void testSetHeight() {
        Wall wall = new Wall();
        wall.setHeight(1);
        assertEquals(1, wall.getHeight());
    }

    @Test
    public void testSetTexture() {
        Wall wall = new Wall();
        wall.setTexture("Texture");
        assertEquals("Texture", wall.getTexture());
    }
}

