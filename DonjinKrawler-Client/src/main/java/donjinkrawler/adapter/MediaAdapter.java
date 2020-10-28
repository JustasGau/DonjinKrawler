package donjinkrawler.adapter;

public class MediaAdapter implements MediaPlayer {
    AdvancedMediaPlayer advancedMusicPlayer;

    public MediaAdapter(String audioType) {
        switch (audioType) {
            case "wav" -> advancedMusicPlayer = new WavPlayer();
            case "mp3" -> advancedMusicPlayer = new Mp3Player();
            default -> {

            }
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        switch (audioType) {
            case "wav" -> advancedMusicPlayer.playWav(fileName);
            case "mp3" -> advancedMusicPlayer.playMp3(fileName);
            default -> {

            }
        }
    }
}
