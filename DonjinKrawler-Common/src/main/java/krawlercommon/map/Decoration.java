package krawlercommon.map;

public class Decoration extends CollidableObject {

    private int imageNumber;

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
