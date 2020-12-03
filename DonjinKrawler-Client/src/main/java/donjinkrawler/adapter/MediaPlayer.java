package donjinkrawler.adapter;

public interface MediaPlayer {
    void play(String audioType, String fileName, boolean repeat);

    void stop();
}
