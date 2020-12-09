package krawlercommon.map.obstacles;

import krawlercommon.map.Obstacle;
import krawlercommon.visitor.ObstacleVisitor;

public final class Lava extends Obstacle {

    public Lava() {

    }

    public Lava(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void accept(ObstacleVisitor visitor) {
        visitor.visit(this);
    }

    public String getImageNumber() {
        if (this.imageNumber == null) {
            this.imageNumber = "1";
        }
        return imageNumber;
    }
}
