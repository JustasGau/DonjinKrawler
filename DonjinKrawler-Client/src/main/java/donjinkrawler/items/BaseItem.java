package donjinkrawler.items;

import krawlercommon.items.ItemData;

import javax.swing.*;
import java.awt.*;

public abstract class BaseItem {

    protected ItemData itemData;
    protected Image image;

    protected void loadImage(String imagePath) {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource(imagePath).getFile());
        image = ii.getImage();
    }

    public Image getImage() {
        return image;
    }

    public abstract ItemData getData();

}
