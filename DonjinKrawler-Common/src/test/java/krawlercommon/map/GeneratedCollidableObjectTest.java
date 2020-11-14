package krawlercommon.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratedCollidableObjectTest {
    @Test
    public void testSetX() {
        CollidableObject collidableObject = new CollidableObject();
        collidableObject.setX(2);
        assertEquals(2, collidableObject.getTopX());
    }

    @Test
    public void testSetY() {
        CollidableObject collidableObject = new CollidableObject();
        collidableObject.setY(3);
        assertEquals(3, collidableObject.getY());
    }

    @Test
    public void testSetWidth() {
        CollidableObject collidableObject = new CollidableObject();
        collidableObject.setWidth(1);
        assertEquals(1, collidableObject.getWidth());
    }

    @Test
    public void testSetHeight() {
        CollidableObject collidableObject = new CollidableObject();
        collidableObject.setHeight(1);
        assertEquals(1, collidableObject.getHeight());
    }

    @Test
    public void testGetBotX() {
        assertEquals(0, (new CollidableObject()).getBotX());
    }

    @Test
    public void testGetBotY() {
        assertEquals(0, (new CollidableObject()).getBotY());
    }
}

