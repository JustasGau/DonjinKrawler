package donjinkrawler.adapter;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.File;

public class WavPlayer implements AdvancedMediaPlayer {

    private Clip clip;

    @Override
    public void playMp3(String fileName, boolean repeat) {
    }

    @Override
    public void playWav(String fileName, boolean repeat) {
        try {
            File audioFile = new File(ClassLoader.getSystemResource("sounds/".concat(fileName)).getFile());
            this.clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent lineEvent) {
                    if (lineEvent.getType().equals(LineEvent.Type.STOP) && repeat) {
                        clip.setMicrosecondPosition(0);
                        clip.start();
                    }
                }
            });

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        this.clip.close();
    }
}
