package donjinkrawler;

import com.esotericsoftware.kryonet.Client;
import donjinkrawler.adapter.AudioPlayer;
import donjinkrawler.chat.Chat;
import donjinkrawler.command.DamageCommand;
import donjinkrawler.command.MoveCommand;
import donjinkrawler.command.PlayerCommander;
import donjinkrawler.facade.MusicMaker;
import donjinkrawler.items.BaseItem;
import donjinkrawler.logging.LoggerSingleton;
import donjinkrawler.visitor.ItemVisitor;
import donjinkrawler.visitor.ItemVisitorImpl;
import donjinkrawler.visitor.ObstacleVisitorImpl;
import krawlercommon.PlayerData;
import krawlercommon.composite.RawBonus;
import krawlercommon.enemies.Enemy;
import krawlercommon.iterator.Iterator;
import krawlercommon.iterator.door.DoorCollection;
import krawlercommon.iterator.observer.ObserverCollection;
import krawlercommon.map.*;
import krawlercommon.observer.Observer;
import krawlercommon.observer.Subject;
import krawlercommon.packets.ChangeEnemyStrategyPacket;
import krawlercommon.packets.CharacterAttackPacket;
import krawlercommon.packets.DamageEnemyPacket;
import krawlercommon.packets.KickPlayerPacket;
import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveTowardPlayer;
import krawlercommon.visitor.ObstacleVisitor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static donjinkrawler.Game.shells;

public class Player implements Subject {
    private final LoggerSingleton logger = LoggerSingleton.getInstance();

    private final PlayerData data;
    private final Inventory inventory;
    private final MusicMaker musicMaker;
    private final Chat chat;
    private final donjinkrawler.Client gameClient;
    int obstacleCollisionCount = 0;
    private int dx;
    private int dy;
    private int width;
    private int height;
    private Image image;
    private Boolean hasChangedPosition = false;
    private Boolean hasNotifiedObservers = false;
    private ObserverCollection observerCollection;
    private Client client;
    private AudioPlayer audioPlayer = new AudioPlayer();
    private PlayerCommander commander = new PlayerCommander();
    private Boolean backwards = false;
    private Image attackIMG;
    private Boolean attack = false;
    private Boolean canAttack = false;
    private int attackTimer = 0;
    private RawBonus armorBonus = null;
    private RawBonus weaponBonus = null;
    private ItemVisitor itemVisitor;
    private boolean isDead;
    private ObstacleVisitor obstacleVisitor;

    public Player(PlayerData playerData, Client client, donjinkrawler.Client gameClient) {
        data = playerData;

        this.client = client;
        this.gameClient = gameClient;
        this.observerCollection = new ObserverCollection();
        this.inventory = new Inventory();
        this.musicMaker = new MusicMaker();
        this.chat = new Chat(this.client, this.getName(), this.getId());

        itemVisitor = new ItemVisitorImpl(data, inventory, armorBonus, weaponBonus);
        obstacleVisitor = new ObstacleVisitorImpl(this);
        loadImage();
    }

    private void loadImage() {
        try {
            InputStream stream = getClass().getResourceAsStream("/craft.png");
            ImageIcon ii = new ImageIcon(ImageIO.read(stream));
            image = ii.getImage();
            height = image.getHeight(null);
            width = image.getWidth(null);
            ii = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/attack.png")));
            attackIMG = ii.getImage();
        } catch (Exception e) {
            logger.error("Failed loading image");
            logger.error(e);
        }
    }

    public Integer move(List<Wall> walls, DoorCollection doors, List<Obstacle> obstacles, List<Decoration> decorations,
                        HashMap<Integer, BaseItem> items) {
        if (isDead) {
            return null;
        }
        if (isCollidingWithDoor(doors)) {
            return null;
        }
        if (isCollidingWithImmovableObject(walls) || isCollidingWithImmovableObject(decorations)) {
            return null;
        }
        Integer itemId = isCollidingWithItem(items);
        if (itemId != null) {
            return itemId;
        }
        if (isCollidingWithObstacle(obstacles)) {
            return null;
        }
        if (backwards) {
            commander.undo();
        } else {
            commander.execute(new MoveCommand(this, dx, dy));
        }
        return null;
    }

    private boolean isCollidingWithDoor(DoorCollection doors) {
        for (Iterator i = doors.getIterator(); i.hasNext(); ) {
            Door door = (Door) i.getNext();
            if (isCollidingWith(door)) {
                if (backwards) {
                    commander.undo();
                } else {
                    commander.execute(new MoveCommand(this, dx, dy));
                }
                return true;
            }
        }
        return false;
    }

    private boolean isCollidingWithImmovableObject(List<? extends CollidableObject> objects) {
        for (CollidableObject object : objects) {
            if (isCollidingWith(object)) {
                return true;
            }
        }
        return false;
    }

    private Integer isCollidingWithItem(HashMap<Integer, BaseItem> objects) {
        for (HashMap.Entry<Integer, BaseItem> itemData : objects.entrySet()) {
            if (isCollidingWith(itemData.getValue())) {
                itemData.getValue().accept(itemVisitor);
                return itemData.getKey();
            }
        }
        return null;
    }

    private boolean isCollidingWithObstacle(List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            if (isCollidingWith(obstacle)) {
                handleObstacleCollision(obstacle);
                return true;
            }
        }
        return false;
    }

    private void handleObstacleCollision(Obstacle obstacle) {
        obstacle.accept(obstacleVisitor);
        if (backwards) {
            commander.undo();
        } else {
            commander.execute(new MoveCommand(this, dx, dy));
        }
    }

    public void reducePlayerSpeed() {
        if (dx != 0) {
            if (dx > 0) {
                dx = 1;
            } else {
                dx = -1;
            }
        }
        if (dy != 0) {
            if (dy > 0) {
                dy = 1;
            } else {
                dy = -1;
            }
        }
    }

    public void reduceHealthFromObstacle(int health) {
        obstacleCollisionCount++;
        if (obstacleCollisionCount % 25 == 0) {
            commander.execute(new DamageCommand(this, health));
            obstacleCollisionCount = 0;
            audioPlayer.play("wav", "hurt.wav", false);
            if (data.getHealth() < 50) {
                notifyObservers();
                setHasNotifiedObservers(true);
            }
        }
    }

    private boolean isCollidingWith(CollidableObject obj) {
        int topCornerX = data.getX() + dx;
        int topCornerY = data.getY() + dy;
        int botCornerX = data.getX() + width + dx;
        int botCornerY = data.getY() + height + dy;
        return obj.getTopX() < botCornerX && obj.getBotX() > topCornerX
                && obj.getTopY() < botCornerY && obj.getBotY() > topCornerY;
    }

    private boolean isCollidingWith(Door door) {
        int topCornerX = data.getX() + dx;
        int topCornerY = data.getY() + dy;
        int botCornerX = data.getX() + width + dx;
        int botCornerY = data.getY() + height + dy;
        return door.checkCollision(topCornerX, topCornerY, botCornerX, botCornerY, width, height);
    }

    private boolean isCollidingWith(BaseItem obj) {

        int playerX = data.getX() + dx;
        int playerY = data.getY() + dy;

        int x1 = playerX - 8;
        int y1 = playerY - 8;
        int x2 = playerX + 8;
        int y2 = playerY + 8;

        int objX = obj.getData().getX();
        int objY = obj.getData().getY();

        return objX > x1
                && objX < x2
                && objY > y1
                && objY < y2;
    }

    public int getX() {
        return data.getX();
    }

    public void setX(int val) {
        data.setX(val);
    }

    public int getY() {
        return data.getY();
    }

    public void setY(int val) {
        data.setY(val);
    }

    public int getBotX() {
        return data.getX() + image.getWidth(null);
    }

    public int getBotY() {
        return data.getY() + image.getHeight(null);
    }

    public void setCoordinates(int x, int y) {
        data.setX(x);
        data.setY(y);
    }

    public Boolean hasChangedPosition() {
        return hasChangedPosition;
    }

    public void setHasChangedPosition(Boolean val) {
        this.hasChangedPosition = val;
    }

    public String getName() {
        return data.getName();
    }

    public Image getImage() {
        return image;
    }

    public Image getAttackImage() {
        if (attack || attackTimer < 10) {
            CharacterAttackPacket packet = new CharacterAttackPacket();
            packet.id = getId();
            client.sendTCP(packet);
            return attackIMG;
        } else {
            return null;
        }
    }

    public int getId() {
        return data.getId();
    }

    public void setId(int id) {
        data.setId(id);
    }

    public PlayerData getData() {
        return data;
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }

    public double getHealth() {
        return data.getHealth();
    }

    public void setHealth(double val) {
        if (val <= 0) {
            this.die();
        }
        data.setHealth(val);
    }

    private void die() {
        data.setHealth(0);
        this.isDead = true;
        audioPlayer.play("wav", "game-over.wav", false);
        this.client.sendTCP(new KickPlayerPacket(this.getId()));
        this.gameClient.shutDown();
    }

    public void setHasNotifiedObservers(Boolean hasNotifiedObservers) {
        this.hasNotifiedObservers = hasNotifiedObservers;
    }

    public Boolean hasNotifiedObservers() {
        return this.hasNotifiedObservers;
    }

    public void incrementTimer() {
        if (attackTimer == 60) {
            canAttack = true;
        } else {
            attackTimer++;
            attack = false;
        }
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }

    public void setAttackTimer(int timer) {
        this.attackTimer = timer;
    }

    public void keyPressed(KeyEvent e) {

        if (isDead) {
            return;
        }

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_I) {
            this.inventory.open();
            return;
        }

        hasChangedPosition = true;

        // cast it to an int since coords are  whole numbers
        int playerSpeed = (int) data.getSpeed().getFinalValue();
        if (key == KeyEvent.VK_LEFT) {
            dx = -1 * playerSpeed;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = playerSpeed;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -playerSpeed;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = playerSpeed;
        }

        if (key == KeyEvent.VK_U) {
            backwards = true;
        }

        if (key == KeyEvent.VK_M) {
            this.musicMaker.playBackgroundMusic();
        }

        if (key == KeyEvent.VK_COMMA) {
            this.musicMaker.playPrevBackgroundMusic();
        }

        if (key == KeyEvent.VK_PERIOD) {
            this.musicMaker.playNextBackgroundMusic();
        }

        if (key == KeyEvent.VK_N) {
            this.musicMaker.stopBackgroundMusic();
        }

        if (key == KeyEvent.VK_SLASH) {
            this.musicMaker.lockPlayer();
        }

        if (key == KeyEvent.VK_C) {
            this.chat.open();
        }

        if (key == KeyEvent.VK_SPACE) {
            if (canAttack) {
                attack = true;
                canAttack = false;
                attackTimer = 0;
                findTarget();
            }

        }
    }

    public double getDamage() {
        return data.getDamage().getFinalValue();
    }

    public void findTarget() {
        if (attack) {
            for (AbstractShellInterface enemy : shells.values()) {
                if ((enemy.getX() >= (data.getX() - 20)) && enemy.getX() <= (data.getX() + 20)
                        && enemy.getY() >= (data.getY() - 20) && enemy.getY() < (data.getY() + 20)) {
                    DamageEnemyPacket packet = new DamageEnemyPacket();
                    packet.id = enemy.getID();
                    packet.damage = getDamage();
                    client.sendTCP(packet);
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        hasChangedPosition = false;
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }

        if (key == KeyEvent.VK_U) {
            backwards = false;
        }
    }

    public ArrayList<Observer> getObservers() {
        return new ArrayList<>(observerCollection.observers.values());
    }

    @Override
    public void attachObserver(Observer observer) {
        this.observerCollection.add(observer);
    }

    @Override
    public void detachObserver(Observer observer) {
        this.observerCollection.remove(observer);
    }

    @Override
    public void detachAllObservers() {
        this.observerCollection = new ObserverCollection();
    }

    @Override
    public void notifyObservers() {
        for (Iterator i = observerCollection.getIterator(); i.hasNext(); ) {
            Observer observer = (Observer) i.getNext();
            if (observer != null) {
                EnemyStrategy enemyStrategy = new MoveTowardPlayer();
                observer.update(enemyStrategy);
                ChangeEnemyStrategyPacket packet = new ChangeEnemyStrategyPacket();
                packet.id = ((Enemy) observer).getID();
                packet.strategy = enemyStrategy;
                client.sendTCP(packet);
            }
        }
    }

    public void isAttacking(boolean b) {
        this.attack = b;
    }

    public MusicMaker getMusic() {
        return this.musicMaker;
    }

    public void kill() {
        this.data.setHealth(0);
        this.isDead = true;
    }

    public void receiveMessage(String from, String message) {
        audioPlayer.play("wav", "message.wav", false);
        this.chat.addMessage(from, message);
    }
}