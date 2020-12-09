package krawlercommon.map.obstacles;

import krawlercommon.map.Obstacle;
import krawlercommon.visitor.ObstacleVisitor;

public final class Spikes extends Obstacle {

    @Override
    public void accept(ObstacleVisitor visitor) {
        visitor.visit(this);
    }

    public String getImageNumber() {
        if (this.imageNumber == null) {
            this.imageNumber = "2";
        }
        return imageNumber;
    }
}
