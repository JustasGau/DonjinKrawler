package donjinkrawler.facade;

public class MusicMaker {
    private Music backgroundMusic;
    private Music bossRoomMusic;
    private Music itemRoomMusic;

    public MusicMaker() {
        backgroundMusic = new BackgroundMusic();
        bossRoomMusic = new BossRoomMusic();
        itemRoomMusic = new ItemRoomMusic();
    }

    public void playBackgroundMusic() {
        backgroundMusic.play();
    }

    public void playBossRoomMusic() {
        bossRoomMusic.play();
    }

    public void playItemRoomMusic() {
        itemRoomMusic.play();
    }
}
