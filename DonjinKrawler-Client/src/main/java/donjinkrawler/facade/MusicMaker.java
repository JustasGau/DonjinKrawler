package donjinkrawler.facade;

import donjinkrawler.adapter.AudioPlayer;
import donjinkrawler.adapter.state.PlayingState;

public class MusicMaker {

    public static Boolean doNotPlayMusic = false;

    private final AudioPlayer player;

    public MusicMaker() {
        this.player = new AudioPlayer();

        player.addTrack("wav", "background-music-1.wav");
        player.addTrack("wav", "background-music-2.wav");
        player.addTrack("wav", "background-music-3.wav");
        player.addTrack("wav", "background-music-4.wav");
    }

    public void playBossRoomMusic() {
        if(doNotPlayMusic) {
            return;
        }
        this.player.stop();
        this.player.play("wav", "boss-fight.wav", true);
        this.player.changeState(new PlayingState(this.player));
    }

    public void playItemRoomMusic() {
        if(doNotPlayMusic) {
            return;
        }
        this.player.stop();
        this.player.play("wav", "item-room.wav", true);
        this.player.changeState(new PlayingState(this.player));
    }

    public void playBackgroundMusic() {
        if(doNotPlayMusic) {
            return;
        }
        this.player.getState().onPlay();
    }

    public void playPrevBackgroundMusic() {
        this.player.getState().onPrevious();
    }

    public void playNextBackgroundMusic() {
        this.player.getState().onNext();
    }

    public void stopBackgroundMusic() {
        this.player.getState().onStop();
    }

    public void lockPlayer() {
        this.player.getState().onLock();
    }
}
