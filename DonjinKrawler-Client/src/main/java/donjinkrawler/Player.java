package donjinkrawler;

import command.*;
import krawlercommon.PlayerData;
import krawlercommon.enemies.Enemy;
import krawlercommon.items.ItemLocationData;
import krawlercommon.map.*;
import krawlercommon.observer.Observer;
import krawlercommon.observer.Subject;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import com.esotericsoftware.kryonet.Client;
import krawlercommon.packets.ChangeEnemyStrategyPacket;
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
    private PlayerCommander commander = new PlayerCommander();
    private Boolean backwards = false;
    private Inventory inventory;

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
    }

    public void move(List<Wall> walls, List<Door> doors, List<Obstacle> obstacles, List<Decoration> decorations,
                     HashMap<Integer, ItemLocationData> items) {
        if (isCollidingWithObstacle(obstacles)) {
            return;
        }
        if (isCollidingWithDoor(doors)) {
            return;
        }
        if (isCollidingWithImmovableObject(walls) || isCollidingWithImmovableObject(decorations)) {
            return;
        }
        if(isCollidingWithItem(items)) {
            return;
        }
        if (backwards) {
            commander.undo();
        } else {
            commander.execute(new MoveCommand(this, dx, dy));
        }
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

    private boolean isCollidingWithItem(HashMap<Integer, ItemLocationData> objects) {
        for (HashMap.Entry<Integer, ItemLocationData> itemData : objects.entrySet()) {
            if (isCollidingWith(itemData.getValue())) {
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
        if (backwards) {
            commander.undo();
        } else {
            commander.execute(new MoveCommand(this, dx, dy));
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

    private boolean isCollidingWith(ItemLocationData obj) {

        int playerX = data.getX() + dx;
        int playerY = data.getY() + dy;

        int x1 = playerX - 4;
        int y1 = playerY - 4;
        int x2 = playerX + 4;
        int y2 = playerY + 4;

        int objX = obj.getX();
        int objY = obj.getY();

        return objX > x1
                && objX < x2
                && objY > y1
                && objY < y2;
    }

    public int getX() {
        return data.getX();
    }

    public int getY() {
        return data.getY();
    }

    public void setX(int val) {
        data.setX(val);
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

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_I) {
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
        for(Observer observer: observers) {
            observer.update(new MoveTowardPlayer());
            ChangeEnemyStrategyPacket packet = new ChangeEnemyStrategyPacket();
            packet.id = ((Enemy) observer).getID();
            packet.strategy = new MoveTowardPlayer();
            client.sendTCP(packet);
        }
    }
}