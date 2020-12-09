package krawlercommon.map.obstacles;

import krawlercommon.map.Obstacle;
import krawlercommon.visitor.ObstacleVisitor;

public final class Slime extends Obstacle {

    public Slime() {

    }

    public Slime(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void accept(ObstacleVisitor visitor) {
        visitor.visit(this);
    }

    public String getImageNumber() {
        if (this.imageNumber == null) {
            this.imageNumber = "3";
        }
        return imageNumber;
    }
}
