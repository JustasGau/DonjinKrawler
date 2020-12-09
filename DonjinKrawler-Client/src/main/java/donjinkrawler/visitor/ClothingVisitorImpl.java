package donjinkrawler.visitor;

import donjinkrawler.AbstractShellInterface;
import donjinkrawler.Game;
import donjinkrawler.decorator.clothes.Maracas;
import donjinkrawler.decorator.clothes.Poncho;
import donjinkrawler.decorator.clothes.Sombrero;

import java.awt.*;

public class ClothingVisitorImpl implements ClothingVisitor {

    Graphics2D graphics;
    Game game;
    AbstractShellInterface body;

    public ClothingVisitorImpl(Graphics2D graphics, Game game, AbstractShellInterface body) {
        this.graphics = graphics;
        this.game = game;
        this.body = body;
    }

    @Override
    public void visit(Maracas maracas) {
        graphics.drawImage(maracas.getImage(), body.getX(), body.getY() + 5, game);
    }

    @Override
    public void visit(Sombrero sombrero) {
        graphics.drawImage(sombrero.getImage(), body.getX(), body.getY() - 5, game);
    }

    @Override
    public void visit(Poncho poncho) {
        graphics.drawImage(poncho.getImage(), body.getX(), body.getY(), game);
    }
}
