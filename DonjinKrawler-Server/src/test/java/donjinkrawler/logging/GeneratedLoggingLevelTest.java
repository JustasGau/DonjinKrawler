package donjinkrawler.logging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratedLoggingLevelTest {
    @Test
    public void testValueOf() {
        assertEquals(LoggingLevel.DEBUG, LoggingLevel.valueOf("DEBUG"));
    }

    @Test
    public void testValues() {
        assertEquals(3, LoggingLevel.values().length);
    }
}

