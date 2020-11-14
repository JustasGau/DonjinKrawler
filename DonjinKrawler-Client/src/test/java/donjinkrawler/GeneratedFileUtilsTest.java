package donjinkrawler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratedFileUtilsTest {
    @Test
    @Disabled
    public void testGetResourceFolderFiles() {
        assertEquals(1, FileUtils.getResourceFolderFiles("").length);
    }
}

