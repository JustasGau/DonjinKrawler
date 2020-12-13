package donjinkrawler;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class SwingUtils {

    public static void drawHealthBar(Graphics2D g2d, int x, int y, int width, int height, double health) {
        g2d.setColor(Color.BLACK);
        Rectangle2D.Double healthBarOutline = new Rectangle2D.Double(x - 1.0, y - 14.0, width + 2.0, height + 2.0);
        g2d.fill(healthBarOutline);
        if (health >= 70) {
            g2d.setColor(Color.GREEN);
        } else if (health >= 40) {
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.RED);
        }
        Rectangle2D.Double healthBar = new Rectangle2D.Double(x, y - 13.5, health * width / 100.0, height);
        g2d.fill(healthBar);
    }


    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}