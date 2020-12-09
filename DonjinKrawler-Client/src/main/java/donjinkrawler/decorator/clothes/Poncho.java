package donjinkrawler.decorator.clothes;

import donjinkrawler.visitor.ClothingVisitor;

import java.awt.*;

public class Poncho extends Clothing {

    @Override
    public void accept(ClothingVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Image getImage() {
        if (this.image == null) {
            this.loadImage("poncho");
        }
        return this.image.getImage();
    }
}