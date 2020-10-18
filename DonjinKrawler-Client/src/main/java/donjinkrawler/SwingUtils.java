package donjinkrawler;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class SwingUtils {

    public static void drawHealthBar(Graphics2D g2d, int x, int y, int width, int height, double health) {
        g2d.setColor(Color.BLACK);
        Rectangle2D.Double healthBarOutline = new Rectangle2D.Double(x - 1, y - 14, width + 2, height + 2);
        g2d.fill(healthBarOutline);
        g2d.setColor(Color.GREEN);
        Rectangle2D.Double healthBar = new Rectangle2D.Double(x, y - 13.5, health * width / 100, height);
        g2d.fill(healthBar);
    }


    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
