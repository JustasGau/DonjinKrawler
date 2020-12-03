package donjinkrawler.adapter;

public interface AdvancedMediaPlayer {
    void playMp3(String fileName, boolean repeat);

    void playWav(String fileName, boolean repeat);

    void stop();
}
