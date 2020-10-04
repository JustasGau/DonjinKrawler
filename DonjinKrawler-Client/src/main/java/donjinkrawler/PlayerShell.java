package donjinkrawler;

import javax.swing.*;

public class PlayerShell extends AbstractShell {

    public PlayerShell(String name) {
        this.name = name;
        loadImage();
    }

    protected void loadImage() {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("craft.png").getFile());
        image = ii.getImage();
    }
}