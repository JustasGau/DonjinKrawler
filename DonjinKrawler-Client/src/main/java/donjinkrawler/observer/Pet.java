package donjinkrawler.observer;

import donjinkrawler.AbstractShell;

import javax.swing.*;

public class Pet extends AbstractShell implements Observer {

    public Pet(int x, int y) {
        this.name = "Kitty";
        this.x = x;
        this.y = y;
        loadImage();
    }

    @Override
    public void update(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    protected void loadImage() {
        var kittyImage= new ImageIcon(ClassLoader.getSystemResource("kitty.png").getFile()).getImage();
        this.image = kittyImage;
    }
}
