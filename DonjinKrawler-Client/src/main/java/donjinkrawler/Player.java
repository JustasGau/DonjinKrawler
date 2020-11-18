package donjinkrawler;

import com.esotericsoftware.kryonet.Client;
import donjinkrawler.adapter.AudioPlayer;
import donjinkrawler.command.DamageCommand;
import donjinkrawler.command.MoveCommand;
import donjinkrawler.command.PlayerCommander;
import donjinkrawler.facade.MusicMaker;
import donjinkrawler.items.Armor;
import donjinkrawler.items.BaseItem;
import donjinkrawler.items.Weapon;
import krawlercommon.PlayerData;
import krawlercommon.enemies.Enemy;
import krawlercommon.map.*;
import krawlercommon.observer.Observer;
import krawlercommon.observer.Subject;
import krawlercommon.packets.ChangeEnemyStrategyPacket;
import krawlercommon.packets.CharacterAttackPacket;
import krawlercommon.packets.DamageEnemyPacket;
import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveTowardPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static donjinkrawler.Game.shells;

public class Player implements Subject {
    private final PlayerData data;
    int obstacleCollisionCount = 0;
    private int dx;
    private int dy;
    private int width;
    private int height;
    private Image image;
    private Boolean hasChangedPosition = false;
    private Boolean hasNotifiedObservers = false;
    private ArrayList<Observer> observers;
    private Client client;
    private AudioPlayer audioPlayer = new AudioPlayer();
    private PlayerCommander commander = new PlayerCommander();
    private Boolean backwards = false;
    private Image attackIMG;
    private double damage = 20;
    private Boolean attack = false;
    private Boolean canAttack = false;
    private int attackTimer = 0;
    private final Inventory inventory;

    public Player(PlayerData playerData, Client client) {
        this.client = client;
        this.observers = new ArrayList<>();
        this.inventory = new Inventory();

        data = playerData;
        loadImage();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("craft.png").getFile());
        image = ii.getImage();
        height = image.getHeight(null);
        width = image.getWidth(null);
        ii = new ImageIcon(ClassLoader.getSystemResource("attack.png").getFile());
        attackIMG = ii.getImage();
    }

    public Integer move(List<Wall> walls, List<Door> doors, List<Obstacle> obstacles, List<Decoration> decorations,
                        HashMap<Integer, BaseItem> items) {
        if (isCollidingWithObstacle(obstacles)) {
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
        if (backwards) {
            commander.undo();
        } else {
            commander.execute(new MoveCommand(this, dx, dy));
        }
        return null;
    }

    private boolean isCollidingWithDoor(List<Door> doors) {
        for (Door door : doors) {
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
                handleItemCollision(itemData.getValue());
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
        if (obstacle.getObstacleType() == ObstacleType.LAVA) {
            reduceHealthFromObstacle(10);
        } else if (obstacle.getObstacleType() == ObstacleType.SPIKES) {
            reduceHealthFromObstacle(5);
        } else if (obstacle.getObstacleType() == ObstacleType.SLIME) {
            reducePlayerSpeed();
        }
        if (backwards) {
            commander.undo();
        } else {
            commander.execute(new MoveCommand(this, dx, dy));
        }
    }

    private void handleItemCollision(BaseItem item) {
        if (item instanceof Armor) {
            this.inventory.addArmor((Armor) item);
        } else if (item instanceof Weapon) {
            this.inventory.addWeapon((Weapon) item);
        }
    }

    private void reducePlayerSpeed() {
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

    private void reduceHealthFromObstacle(int health) {
        obstacleCollisionCount++;
        if (obstacleCollisionCount % 25 == 0) {
            commander.execute(new DamageCommand(this, health));
            obstacleCollisionCount = 0;
            audioPlayer.play("wav", "hurt.wav");
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
        data.setHealth(val);
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

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_I) {
            this.inventory.open();
            return;
        }

        hasChangedPosition = true;

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }

        if (key == KeyEvent.VK_U) {
            backwards = true;
        }

        if (key == KeyEvent.VK_M) {
            MusicMaker musicMaker = new MusicMaker();
            musicMaker.playBackgroundMusic();
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
        return damage;
    }

    public void findTarget() {
        if (attack) {
            for (AbstractShellInterface enemy : shells.values()) {
                if ((enemy.getX() >= (data.getX() - 10)) && enemy.getX() <= (data.getX() + 10)
                        && enemy.getY() >= (data.getY() - 10) && enemy.getY() < (data.getY() + 10)) {
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
        return this.observers;
    }

    @Override
    public void attachObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void detachObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void detachAllObservers() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
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

    public void setAttackTimer(int timer) {
        this.attackTimer = timer;
    }
}