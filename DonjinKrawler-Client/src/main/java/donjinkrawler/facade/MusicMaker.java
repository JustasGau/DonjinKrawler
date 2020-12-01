package donjinkrawler.facade;

import donjinkrawler.adapter.AudioPlayer;

public class MusicMaker {

    public static Boolean doNotPlayMusic = false;

    private final AudioPlayer player;
    private final Music bossRoomMusic;
    private final Music itemRoomMusic;

    public MusicMaker() {
        this.player = new AudioPlayer();

        player.addTrack("wav", "background-music-1.wav");
        player.addTrack("wav", "background-music-2.wav");
        player.addTrack("wav", "background-music-3.wav");
        player.addTrack("wav", "background-music-4.wav");

        bossRoomMusic = new BossRoomMusic();
        itemRoomMusic = new ItemRoomMusic();
    }

    public void playBossRoomMusic() {
        //
    }

    public void playItemRoomMusic() {
        //
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
