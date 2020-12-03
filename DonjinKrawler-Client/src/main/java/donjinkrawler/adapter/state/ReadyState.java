package donjinkrawler.adapter.state;

import donjinkrawler.adapter.AudioPlayer;

public class ReadyState extends State{
    public ReadyState(AudioPlayer player) {
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
        player.resetCurrentTrack();
    }

    @Override
    public void onNext() {
        player.nextTrack();
        player.startPlayback();
        player.changeState(new PlayingState(player));
    }

    @Override
    public void onPrevious() {
        player.previousTrack();
        player.startPlayback();
        player.changeState(new PlayingState(player));
    }
}
