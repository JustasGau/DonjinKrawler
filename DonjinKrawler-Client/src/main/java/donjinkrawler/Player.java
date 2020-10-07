package donjinkrawler;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import donjinkrawler.observer.Observer;
import donjinkrawler.observer.Pet;
import donjinkrawler.observer.Subject;

public class Player implements Subject {
    private ArrayList<Pet> pets;
    private int dx;
    private int dy;
    private int width;
    private int height;
    private int x = 250;
    private int y = 250;
    private final String name;
    private Image image;
    private Boolean hasChangedPosition = false;

    public Player(String name) {
        this.name = name;
        loadImage();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("craft.png").getFile());
        image = ii.getImage();
        height = image.getHeight(null);
        width = image.getWidth(null);
    }

    public void move() {
        if ((dx < 0 && x - dx > 2) || (dx > 0 && x + width + dx < 500))
            x += dx;
        if ((dy < 0 && y - dy > 2) || (dy > 0 && y + height + 50 + dy < 500))
            y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Boolean hasChangedPosition() {
        return hasChangedPosition;
    }

    public void setHasChangedPosition(Boolean val) {
        this.hasChangedPosition = val;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
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
    public void attach(Pet pet) {
        pets.add(pet);
    }

    @Override
    public void detach(Pet pet) {
        pets.remove(pet);
    }

    @Override
    public void notifyMovement() {
        for (Pet pet : pets) {
            pet.update(this.x, this.y);
        }
    }
}