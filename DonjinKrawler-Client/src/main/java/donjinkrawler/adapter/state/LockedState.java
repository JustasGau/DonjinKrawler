package donjinkrawler.adapter.state;

import donjinkrawler.adapter.AudioPlayer;

public class LockedState extends State {

    private final State prevState;

    public LockedState(AudioPlayer player, State prevState) {
        super(player);
        this.prevState = prevState;
    }

    @Override
    public void onLock() {
        player.changeState(this.prevState);
    }

    @Override
    public void onPlay() {
        // Doing nothing because player is locked
    }

    public void onStop() {
        // Doing nothing because player is locked
    }

    @Override
    public void onNext() {
        // Doing nothing because player is locked
    }

    @Override
    public void onPrevious() {
        // Doing nothing because player is locked
    }
}