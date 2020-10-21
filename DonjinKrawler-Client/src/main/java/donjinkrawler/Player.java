package donjinkrawler;

import donjinkrawler.adapter.AudioPlayer;
import krawlercommon.PlayerData;
import krawlercommon.enemies.Enemy;
import krawlercommon.map.*;
import krawlercommon.observer.Observer;
import krawlercommon.observer.Subject;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import com.esotericsoftware.kryonet.Client;
import krawlercommon.packets.ChangeEnemyStrategyPacket;
import krawlercommon.strategies.EnemyStrategy;
import krawlercommon.strategies.MoveTowardPlayer;

public class Player implements Subject {
    private final PlayerData data;
    private int dx;
    private int dy;
    private int width;
    private int height;
    private Image image;
    private Boolean hasChangedPosition = false;
    private Boolean hasNotifiedObservers = false;
    int obstacleCollisionCount = 0;
    private ArrayList<Observer> observers;
    private Client client;
    private AudioPlayer audioPlayer = new AudioPlayer();

    public Player(PlayerData playerData, Client client) {
        this.client = client;
        this.observers = new ArrayList<>();
        data = playerData;
        loadImage();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("craft.png").getFile());
        image = ii.getImage();
        height = image.getHeight(null);
        width = image.getWidth(null);
    }

    public void move(List<Wall> walls, List<Door> doors, List<Obstacle> obstacles, List<Decoration> decorations) {
        if (isCollidingWithObstacle(obstacles)) {
            return;
        }
        if (isCollidingWithDoor(doors)) {
            return;
        }
        if (isCollidingWithImmovableObject(walls) || isCollidingWithImmovableObject(decorations)) {
            return;
        }
        data.setX(data.getX() + dx);
        data.setY(data.getY() + dy);

    }

    private boolean isCollidingWithDoor(List<Door> doors) {
        for (Door door : doors) {
            if (isCollidingWith(door)) {
                data.setX(data.getX() + dx);
                data.setY(data.getY() + dy);
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
        data.setX(data.getX() + dx);
        data.setY(data.getY() + dy);
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
            data.setHealth(data.getHealth() - health);
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

    public int getX() {
        return data.getX();
    }

    public int getY() {
        return data.getY();
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

    public void setHasNotifiedObservers(Boolean hasNotifiedObservers) {
        this.hasNotifiedObservers = hasNotifiedObservers;
    }

    public void keyPressed(KeyEvent e) {
        hasChangedPosition = true;
        int key = e.getKeyCode();

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
        for (Observer observer: observers) {
            EnemyStrategy enemyStrategy = new MoveTowardPlayer();
            observer.update(enemyStrategy);
            ChangeEnemyStrategyPacket packet = new ChangeEnemyStrategyPacket();
            packet.id = ((Enemy) observer).getID();
            packet.strategy = enemyStrategy;
            client.sendTCP(packet);
        }
    }
}