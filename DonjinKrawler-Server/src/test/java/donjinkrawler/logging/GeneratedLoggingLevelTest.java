package donjinkrawler.logging;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
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

