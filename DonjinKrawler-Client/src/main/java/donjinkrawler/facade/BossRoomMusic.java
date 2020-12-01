package donjinkrawler.facade;

import donjinkrawler.adapter.AudioPlayer;

public class BossRoomMusic implements Music {
    @Override
    public void play() {
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.play("wav", "boss-fight.wav", false);
    }
}
