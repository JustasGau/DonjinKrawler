package donjinkrawler.adapter.state;

import donjinkrawler.adapter.AudioPlayer;

public class PlayingState extends State {

    public PlayingState(AudioPlayer player) {
        super(player);
    }

    @Override
    public void onLock() {
        player.changeState(new LockedState(player, this));
    }

    @Override
    public void onPlay() {
        // Do nothing - keep playing music
    }

    @Override
    public void onStop() {
        player.stop();
        player.changeState(new StoppedState(player));
    }

    @Override
    public void onNext() {
        player.stop();
        player.nextTrack();
        player.startPlayback();
    }

    @Override
    public void onPrevious() {
        player.stop();
        player.previousTrack();
        player.startPlayback();
    }
}
