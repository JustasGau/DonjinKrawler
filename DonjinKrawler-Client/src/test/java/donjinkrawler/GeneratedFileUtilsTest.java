package donjinkrawler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedFileUtilsTest {
    @Test
    @Disabled
    public void testGetResourceFolderFiles() throws IOException, URISyntaxException {
        assertEquals(1, FileUtils.getResourceFolderFiles("").size());
    }
}

