package donjinkrawler.adapter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Mp3Player implements AdvancedMediaPlayer {
    @Override
    public void playMp3(String fileName) {
        try {
            File soundFile = new File(ClassLoader.getSystemResource("sounds/".concat(fileName)).getFile());
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playWav(String fileName) {}
}
