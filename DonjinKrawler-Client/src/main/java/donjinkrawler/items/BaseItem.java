package donjinkrawler.items;

import donjinkrawler.prototype.KrawlerCloneable;
import krawlercommon.items.ItemLocationData;

import javax.swing.*;
import java.awt.*;

public abstract class BaseItem implements KrawlerCloneable {

    protected ItemLocationData itemData;
    protected Image image;

    protected void loadImage(String imagePath) {
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource(imagePath).getFile());
        image = ii.getImage();
    }

    public Image getImage() {
        return image;
    }

    public abstract ItemLocationData getData();

    public abstract String getDescription();


    @Override
    public BaseItem clone() throws CloneNotSupportedException {
        return (BaseItem) super.clone();
    }

    @Override
    public abstract BaseItem deepCopy() throws CloneNotSupportedException;
}
