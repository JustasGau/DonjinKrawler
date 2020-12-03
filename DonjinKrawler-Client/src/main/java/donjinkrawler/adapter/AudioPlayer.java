package donjinkrawler.adapter;

import donjinkrawler.adapter.state.ReadyState;
import donjinkrawler.adapter.state.State;
import donjinkrawler.adapter.tracks.Track;

import java.util.ArrayList;
import java.util.List;

public class AudioPlayer implements MediaPlayer {

    private final List<Track> playlist = new ArrayList<>();
    private MediaAdapter mediaAdapter;
    private State state;
    private int currentTrack = 0;

    public AudioPlayer() {
        this.state = new ReadyState(this);
    }

    @Override
    public void play(String audioType, String fileName, boolean repeat) {
        mediaAdapter = new MediaAdapter(audioType);
        mediaAdapter.play(audioType, fileName, repeat);
    }

    @Override
    public void stop() {
        if (this.mediaAdapter != null) {
            mediaAdapter.stop();
        }
    }

    public void addTrack(String audioType, String fileName) {
        this.playlist.add(new Track(audioType, fileName));
    }

    public void changeState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void startPlayback() {
        Track track = playlist.get(currentTrack);
        this.play(track.getType(), track.getFile(), true);
    }

    public void nextTrack() {
        currentTrack++;
        if (currentTrack > playlist.size() - 1) {
            currentTrack = 0;
        }
    }

    public void previousTrack() {
        currentTrack--;
        if (currentTrack < 0) {
            currentTrack = playlist.size() - 1;
        }
    }

    public void resetCurrentTrack() {
        this.currentTrack = 0;
    }
}
