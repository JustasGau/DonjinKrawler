package donjinkrawler.adapter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class WavPlayer implements AdvancedMediaPlayer {

    private Clip clip;

    @Override
    public void playMp3(String fileName) {}

    @Override
    public void playWav(String fileName) {
        try {
<<<<<<< HEAD
            File audioFile = new File(ClassLoader.getSystemResource("sounds/".concat(fileName)).getFile());
            this.clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));
=======
            InputStream stream = getClass().getResourceAsStream("/sounds/".concat(fileName));
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(stream));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
>>>>>>> edf5af2 (Added support for packaging to jar correctly)
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.clip.stop();
    }
}
