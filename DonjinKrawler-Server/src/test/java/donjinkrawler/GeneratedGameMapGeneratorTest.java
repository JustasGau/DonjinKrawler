package donjinkrawler;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratedGameMapGeneratorTest {
    @Test
    public void testGenerate() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> (new GameMapGenerator(0)).generate());
        assertThrows(NegativeArraySizeException.class, () -> (new GameMapGenerator(-1)).generate());
        assertTrue((new GameMapGenerator(40)).generate() instanceof java.util.ArrayList);
    }

    @Test
    public void testGenerateRoomsFromString() {
        GameMapGenerator gameMapGenerator = new GameMapGenerator(3);
        assertEquals(1, gameMapGenerator.generateRoomsFromString(new ArrayList<String>()).size());
    }

    @Test
    public void testGenerateRoomsFromString2() {
        GameMapGenerator gameMapGenerator = new GameMapGenerator(3);
        ArrayList<String> stringList = new ArrayList<String>();
        stringList.add("E");
        assertEquals(1, gameMapGenerator.generateRoomsFromString(stringList).size());
    }

    @Test
    public void testGenerateRoomsFromString3() {
        GameMapGenerator gameMapGenerator = new GameMapGenerator(3);
        ArrayList<String> stringList = new ArrayList<String>();
        stringList.add("E");
        stringList.add("E");
        assertEquals(2, gameMapGenerator.generateRoomsFromString(stringList).size());
    }

    @Test
    public void testGenerateRoomsFromString4() {
        GameMapGenerator gameMapGenerator = new GameMapGenerator(3);
        ArrayList<String> stringList = new ArrayList<String>();
        stringList.add("E");
        stringList.add("E");
        stringList.add("E");
        assertEquals(3, gameMapGenerator.generateRoomsFromString(stringList).size());
    }
}

