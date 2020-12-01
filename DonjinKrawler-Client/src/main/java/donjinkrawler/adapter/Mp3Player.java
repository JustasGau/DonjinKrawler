package donjinkrawler.adapter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Mp3Player implements AdvancedMediaPlayer {

    private Clip clip;

    @Override
    public void playMp3(String fileName) {
        try {
<<<<<<< HEAD
            File soundFile = new File(ClassLoader.getSystemResource("sounds/".concat(fileName)).getFile());
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
=======
            InputStream stream = getClass().getResourceAsStream("/sounds/".concat(fileName));
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(stream));
            Clip clip = AudioSystem.getClip();
>>>>>>> edf5af2 (Added support for packaging to jar correctly)
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playWav(String fileName) {}

    @Override
    public void stop() {
        this.clip.stop();
    }
}
