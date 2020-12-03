package donjinkrawler.adapter;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class WavPlayer implements AdvancedMediaPlayer {

    private Clip clip;

    @Override
    public void playMp3(String fileName, boolean repeat) {
    }

    @Override
    public void playWav(String fileName, boolean repeat) {
        try {
            InputStream stream = getClass().getResourceAsStream("/sounds/".concat(fileName));
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(stream));
            this.clip = AudioSystem.getClip();
            clip.open(audioInput);

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
