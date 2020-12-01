package donjinkrawler.items;

import krawlercommon.KrawlerCloneable;
import krawlercommon.items.ItemLocationData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public abstract class BaseItem implements KrawlerCloneable {

    protected ItemLocationData itemData;
    protected Image image;

    protected void loadImage(String imagePath) {
        try {
            InputStream stream = getClass().getResourceAsStream("/".concat(imagePath));
            ImageIcon ii = new ImageIcon(ImageIO.read(stream));
            image = ii.getImage();
        } catch (Exception ignored) {

        }
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
