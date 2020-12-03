package donjinkrawler.logging;


import donjinkrawler.config.ConfigSingleton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoggerSingletonTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    static ConfigSingleton configSingleton;

    @BeforeAll
    static void setUp() {
        configSingleton = mock(ConfigSingleton.class);
        when(configSingleton.getPropertyValue("logging.fileName")).thenReturn("test-log");
        when(configSingleton.getPropertyValue("logging.level")).thenReturn(null);
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public static void tearDown() {
        System.setOut(originalOut);
        File dir = new File(ClassLoader.getSystemResource(".").getFile() + "/logs/");
        if (dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
        }
        dir.delete();
    }

    @Test
    public void testInitFileOutput() {
        LoggerSingleton logger = new LoggerSingleton(configSingleton);
        assertTrue(logger.isLogEnabled());
    }

    @Test
    public void testInfoLogging() {
        String logLevel = "INFO";
        when(configSingleton.getPropertyValue("logging.fileName")).thenReturn(null);
        when(configSingleton.getPropertyValue("logging.level", "INFO")).thenReturn(logLevel);
        when(configSingleton.getPropertyValue("logging.level")).thenReturn(logLevel);
        LoggerSingleton logger = new LoggerSingleton(configSingleton);
        logger.info("Hello world");
        assertEquals("[" + logLevel + "] Hello world", getLastMessageFromStream());
    }

    @Test
    public void testErrorLogging() {
        String logLevel = "ERROR";
        when(configSingleton.getPropertyValue("logging.fileName")).thenReturn(null);
        when(configSingleton.getPropertyValue("logging.level", "INFO")).thenReturn(logLevel);
        when(configSingleton.getPropertyValue("logging.level")).thenReturn(logLevel);
        LoggerSingleton logger = new LoggerSingleton(configSingleton);
        logger.error("Hello world");
        assertEquals("[" + logLevel + "] Hello world", getLastMessageFromStream());
    }

    @Test
    public void testDebugLogging() {
        String logLevel = "DEBUG";
        when(configSingleton.getPropertyValue("logging.fileName")).thenReturn(null);
        when(configSingleton.getPropertyValue("logging.level", "INFO")).thenReturn(logLevel);
        when(configSingleton.getPropertyValue("logging.level")).thenReturn(logLevel);
        LoggerSingleton logger = new LoggerSingleton(configSingleton);
        logger.debug("Hello world");
        assertEquals("[" + logLevel + "] Hello world", getLastMessageFromStream());
    }

    @Test
    public void testCallerClassMethod() {
        LoggerSingleton loggerSingleton = LoggerSingleton.getInstance();
        String expected = "donjinkrawler.logging.LoggerSingletonTest#testCallerClassMethod";
        String actual = loggerSingleton.getCallerClassMethod();
        assertEquals(expected, actual);
    }

    private String getLastMessageFromStream() {
        return outContent.toString().split("\n")[outContent.toString().split("\n").length - 1];
    }
}
