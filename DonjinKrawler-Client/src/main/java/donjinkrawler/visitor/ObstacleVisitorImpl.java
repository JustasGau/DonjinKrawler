package donjinkrawler.visitor;

import donjinkrawler.Player;
import krawlercommon.map.obstacles.Lava;
import krawlercommon.map.obstacles.Slime;
import krawlercommon.map.obstacles.Spikes;
import krawlercommon.visitor.ObstacleVisitor;

public class ObstacleVisitorImpl implements ObstacleVisitor {

    Player player;

    public ObstacleVisitorImpl(Player player) {
        this.player = player;
    }

    @Override
    public void visit(Lava lava) {
        player.reduceHealthFromObstacle(10);
    }

    @Override
    public void visit(Spikes spikes) {
        player.reduceHealthFromObstacle(5);
    }

    @Override
    public void visit(Slime slime) {
        player.reducePlayerSpeed();
    }
}
