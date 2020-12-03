package donjinkrawler.adapter.state;

import donjinkrawler.adapter.AudioPlayer;

public class StoppedState extends State {

    public StoppedState(AudioPlayer player) {
        super(player);
    }

    @Override
    public void onLock() {
        player.changeState(new LockedState(player, this));
    }

    @Override
    public void onPlay() {
        player.startPlayback();
        player.changeState(new PlayingState(player));
    }

    @Override
    public void onStop() {
        player.changeState(new ReadyState(player));
    }

    @Override
    public void onNext() {
        player.nextTrack();
    }

    @Override
    public void onPrevious() {
        player.previousTrack();
    }
}
