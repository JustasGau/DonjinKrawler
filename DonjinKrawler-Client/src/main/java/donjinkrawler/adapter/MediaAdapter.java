package donjinkrawler.adapter;

public class MediaAdapter implements MediaPlayer {
    AdvancedMediaPlayer advancedMusicPlayer;

    public MediaAdapter(String audioType) {
        switch (audioType) {
            case "wav":
                advancedMusicPlayer = new WavPlayer();
                break;

            case "mp3":
                advancedMusicPlayer = new Mp3Player();
                break;

            default:
                break;
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        switch (audioType) {
            case "wav":
                advancedMusicPlayer.playWav(fileName);
                break;

            case "mp3":
                advancedMusicPlayer.playMp3(fileName);
                break;

            default:
                break;
        }
    }
}
