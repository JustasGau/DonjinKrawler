package donjinkrawler.decorator;

import donjinkrawler.AbstractShellInterface;
import donjinkrawler.ShellType;
import donjinkrawler.decorator.clothes.Clothing;
import donjinkrawler.decorator.clothes.Maracas;

import java.util.List;

public class MaracasEnemy extends EnemyClothingDecorator {

    public MaracasEnemy(AbstractShellInterface source) {
        super(source);
    }

    @Override
    public List<Clothing> addClothing() {
        List<Clothing> clothes = wrappee.addClothing();
        clothes.add(new Maracas());
        return clothes;
    }

    @Override
    public ShellType getShellType() {
        return ShellType.ENEMY;
    }
}