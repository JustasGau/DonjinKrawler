package krawlercommon.map;

public class Obstacle extends CollidableObject {

    private ObstacleType obstacleType;

    public Obstacle() {

    }

    public Obstacle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public ObstacleType getObstacleType() {
        return obstacleType;
    }

    public void setObstacleType(ObstacleType obstacleType) {
        this.obstacleType = obstacleType;
    }
}
