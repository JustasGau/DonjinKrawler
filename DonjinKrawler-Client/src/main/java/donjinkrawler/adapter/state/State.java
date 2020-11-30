package donjinkrawler.adapter.state;

import donjinkrawler.adapter.AudioPlayer;

public abstract class State {
    AudioPlayer player;

    public State(AudioPlayer player) {
        this.player = player;
    }

    public abstract void onLock();

    public abstract void onPlay();

    public abstract void onStop();

    public abstract void onNext();

    public abstract void onPrevious();
}

