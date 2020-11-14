package donjinkrawler.adapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneratedMediaAdapterTest {
    @Test
    public void testConstructor() {
        assertThrows(IllegalStateException.class, () -> new MediaAdapter("Audio Type"));
        assertTrue((new MediaAdapter("mp3")).advancedMusicPlayer instanceof Mp3Player);
        assertTrue((new MediaAdapter("wav")).advancedMusicPlayer instanceof WavPlayer);
    }
}

