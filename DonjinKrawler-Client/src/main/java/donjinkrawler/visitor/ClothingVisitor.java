package donjinkrawler.visitor;

import donjinkrawler.decorator.clothes.Maracas;
import donjinkrawler.decorator.clothes.Poncho;
import donjinkrawler.decorator.clothes.Sombrero;

public interface ClothingVisitor {

    void visit(Maracas maracas);

    void visit(Sombrero maracas);

    void visit(Poncho maracas);
}
