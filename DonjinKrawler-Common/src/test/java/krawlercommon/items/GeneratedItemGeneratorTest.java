package krawlercommon.items;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedItemGeneratorTest {
    @Test
    public void testGenerateRandomItem() {
        assertEquals(1, ItemGenerator.generateRandomItem(1).getId());
        assertEquals(0, ItemGenerator.generateRandomItem(0).getId());
        assertEquals(30, ItemGenerator.generateRandomItem(30).getId());
        assertEquals(-2147483648, ItemGenerator.generateRandomItem(-2147483648).getId());
    }

    @Test
    public void testGenerateRandomItem2() {
        ItemLocationData actualGenerateRandomItemResult = ItemGenerator.generateRandomItem(1, 2, 3);
        assertEquals(1, actualGenerateRandomItemResult.getId());
        assertEquals(3, actualGenerateRandomItemResult.getY());
        assertEquals(2, actualGenerateRandomItemResult.getX());
    }

    @Test
    public void testGenerateRandomPotion() {
        assertEquals(1, ItemGenerator.generateRandomPotion(1).getId());
        assertEquals(123, ItemGenerator.generateRandomPotion(123).getId());
        assertEquals(0, ItemGenerator.generateRandomPotion(0).getId());
    }

    @Test
    public void testGenerateRandomPotion2() {
        ItemLocationData actualGenerateRandomPotionResult = ItemGenerator.generateRandomPotion(1, 2, 3);
        assertEquals(1, actualGenerateRandomPotionResult.getId());
        assertEquals(3, actualGenerateRandomPotionResult.getY());
        assertEquals(2, actualGenerateRandomPotionResult.getX());
    }
}

