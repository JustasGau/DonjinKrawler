package krawlercommon.items;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedItemLocationDataTest {
    @Test
    public void testSetX() {
        ItemLocationData generateRandomPotionResult = ItemGenerator.generateRandomPotion(1, 2, 3);
        generateRandomPotionResult.setX(2);
        assertEquals(2, generateRandomPotionResult.getX());
    }

    @Test
    public void testSetY() {
        ItemLocationData generateRandomPotionResult = ItemGenerator.generateRandomPotion(1, 2, 3);
        generateRandomPotionResult.setY(3);
        assertEquals(3, generateRandomPotionResult.getY());
    }
}

