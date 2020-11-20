package krawlercommon.map;

import java.io.Serializable;

public class Decoration extends CollidableObject implements Serializable {

    private int imageNumber;

    public Decoration() {
    }

    public Decoration(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    @Override
    public Decoration clone() throws CloneNotSupportedException {
        return (Decoration) super.clone();
    }
}
