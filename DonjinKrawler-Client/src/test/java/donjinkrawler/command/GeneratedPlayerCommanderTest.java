package donjinkrawler.command;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedPlayerCommanderTest {
    @Test
    public void testConstructor() {
        assertEquals(0, (new PlayerCommander()).history.size());
    }
}

