package donjinkrawler.facade;

public class MusicMaker {
    private Music backgroundMusic;
    private Music bossRoomMusic;

    public MusicMaker() {
        backgroundMusic = new BackgroundMusic();
        bossRoomMusic = new BossRoomMusic();
    }

    public void playBackgroundMusic() {
        backgroundMusic.play();
    }

    public void playBossRoomMusic() {
        bossRoomMusic.play();
    }
}
