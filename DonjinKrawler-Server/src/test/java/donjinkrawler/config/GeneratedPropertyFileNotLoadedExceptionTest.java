package donjinkrawler.config;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("generated")
public class GeneratedPropertyFileNotLoadedExceptionTest {
    @Test
    public void testConstructor() {
        PropertyFileNotLoadedException actualPropertyFileNotLoadedException = new PropertyFileNotLoadedException(
                Object.class, "An error occurred");
        assertEquals("donjinkrawler.config.PropertyFileNotLoadedException: java.lang.Object:An error occurred",
                actualPropertyFileNotLoadedException.toString());
        assertEquals("java.lang.Object:An error occurred", actualPropertyFileNotLoadedException.getLocalizedMessage());
        assertNull(actualPropertyFileNotLoadedException.getCause());
        assertEquals("java.lang.Object:An error occurred", actualPropertyFileNotLoadedException.getMessage());
        assertEquals(0, actualPropertyFileNotLoadedException.getSuppressed().length);
    }

    @Test
    public void testConstructor2() {
        PropertyFileNotLoadedException actualPropertyFileNotLoadedException = new PropertyFileNotLoadedException(
                "An error occurred");
        assertEquals("donjinkrawler.config.PropertyFileNotLoadedException: An error occurred",
                actualPropertyFileNotLoadedException.toString());
        assertEquals("An error occurred", actualPropertyFileNotLoadedException.getLocalizedMessage());
        assertNull(actualPropertyFileNotLoadedException.getCause());
        assertEquals("An error occurred", actualPropertyFileNotLoadedException.getMessage());
        assertEquals(0, actualPropertyFileNotLoadedException.getSuppressed().length);
    }
}

