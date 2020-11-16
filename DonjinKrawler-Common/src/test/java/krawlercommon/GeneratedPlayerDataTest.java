package krawlercommon;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedPlayerDataTest {
    @Test
    public void testSetName() {
        PlayerData playerData = new PlayerData();
        playerData.setName("Name");
        assertEquals("Name", playerData.getName());
    }

    @Test
    public void testSetId() {
        PlayerData playerData = new PlayerData();
        playerData.setId(1);
        assertEquals(1, playerData.getId());
    }

    @Test
    public void testSetX() {
        PlayerData playerData = new PlayerData();
        playerData.setX(2);
        assertEquals(2, playerData.getX());
    }

    @Test
    public void testSetY() {
        PlayerData playerData = new PlayerData();
        playerData.setY(3);
        assertEquals(3, playerData.getY());
    }

    @Test
    public void testSetHealth() {
        PlayerData playerData = new PlayerData();
        playerData.setHealth(10.0);
        assertEquals(10.0, playerData.getHealth());
    }
}

