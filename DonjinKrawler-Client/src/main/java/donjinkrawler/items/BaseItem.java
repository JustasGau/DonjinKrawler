package donjinkrawler.items;

import donjinkrawler.logging.LoggerSingleton;
import donjinkrawler.visitor.ItemVisitor;
import krawlercommon.KrawlerCloneable;
import krawlercommon.items.ItemLocationData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public abstract class BaseItem implements KrawlerCloneable {
    private LoggerSingleton logger = LoggerSingleton.getInstance();

    protected ItemLocationData itemData;
    protected Image image;

    protected void loadImage(String imagePath) {
        try {
            InputStream stream = getClass().getResourceAsStream("/".concat(imagePath));
            ImageIcon ii = new ImageIcon(ImageIO.read(stream));
            image = ii.getImage();
        } catch (Exception e) {
            logger.error("Failed loading image");
            logger.error(e);
        }
    }

    public Image getImage() {
        return image;
    }

    public abstract ItemLocationData getData();

    public abstract String getDescription();

    public abstract void accept(ItemVisitor visitor);

    @Override
    public BaseItem clone() throws CloneNotSupportedException {
        return (BaseItem) super.clone();
    }

    @Override
    public abstract BaseItem deepCopy() throws CloneNotSupportedException;
}
