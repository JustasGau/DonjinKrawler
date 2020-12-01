package donjinkrawler.facade;

import donjinkrawler.adapter.AudioPlayer;

public class ItemRoomMusic implements Music {
    @Override
    public void play() {
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.play("wav", "item-room.wav", false);
    }
}
