package donjinkrawler.decorator.clothes;

import donjinkrawler.logging.LoggerSingleton;
import donjinkrawler.visitor.ClothingVisitor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public abstract class Clothing {
    protected ImageIcon image;
    LoggerSingleton logger = LoggerSingleton.getInstance();

    public abstract Image getImage();

    public abstract void accept(ClothingVisitor visitor);

    protected void loadImage(String imageName) {
        try {
            InputStream stream = getClass().getResourceAsStream("/" + imageName + ".png");
            ImageIcon ii = new ImageIcon(ImageIO.read(stream));
            Image scaledImage = ii.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
            this.image = new ImageIcon(scaledImage);
        } catch (Exception e) {
            logger.error("Failed loading image");
            logger.error(e);
        }
    }
}
