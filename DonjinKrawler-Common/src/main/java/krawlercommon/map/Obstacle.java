package krawlercommon.map;

import krawlercommon.visitor.ObstacleVisitor;

public abstract class Obstacle extends CollidableObject {

    protected String imageNumber = null;

    public Obstacle() {

    }

    public Obstacle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void accept(ObstacleVisitor visitor);

    @Override
    public Obstacle clone() throws CloneNotSupportedException {
        return (Obstacle) super.clone();
    }

    public String getImageNumber() {
        return imageNumber;
    }
}
