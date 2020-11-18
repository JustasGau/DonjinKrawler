package donjinkrawler.facade;

public class MusicMaker {

    public static Boolean doNotPlayMusic = false;

    private final Music backgroundMusic;
    private final Music bossRoomMusic;
    private final Music itemRoomMusic;

    public MusicMaker() {
        backgroundMusic = new BackgroundMusic();
        bossRoomMusic = new BossRoomMusic();
        itemRoomMusic = new ItemRoomMusic();
    }

    public void playBackgroundMusic() {
        if(doNotPlayMusic) {
            return;
        }
        backgroundMusic.play();
    }

    public void playBossRoomMusic() {
        if(doNotPlayMusic) {
            return;
        }
        bossRoomMusic.play();
    }

    public void playItemRoomMusic() {
        if(doNotPlayMusic) {
            return;
        }
        itemRoomMusic.play();
    }
}
