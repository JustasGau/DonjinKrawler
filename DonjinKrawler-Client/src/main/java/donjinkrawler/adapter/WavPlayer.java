package donjinkrawler.adapter;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class WavPlayer implements AdvancedMediaPlayer {
    @Override
    public void playMp3(String fileName) {}

    @Override
    public void playWav(String fileName) {
        try {
            File audioFile = new File(ClassLoader.getSystemResource("sounds/".concat(fileName)).getFile());
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
