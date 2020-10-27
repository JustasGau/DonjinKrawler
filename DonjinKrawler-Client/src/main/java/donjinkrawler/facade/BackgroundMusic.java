package donjinkrawler.facade;

import donjinkrawler.adapter.AudioPlayer;

public class BackgroundMusic implements Music {
    @Override
    public void play() {
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.play("wav", "background-music-1.wav");
    }
}
