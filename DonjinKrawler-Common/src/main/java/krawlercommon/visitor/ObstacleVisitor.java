package krawlercommon.visitor;

import krawlercommon.map.obstacles.Lava;
import krawlercommon.map.obstacles.Slime;
import krawlercommon.map.obstacles.Spikes;

public interface ObstacleVisitor {

    void visit(Spikes spikes);

    void visit(Lava lava);

    void visit(Slime slime);
}
