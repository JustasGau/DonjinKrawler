package donjinkrawler.decorator;

import donjinkrawler.AbstractShellInterface;
import donjinkrawler.ShellType;
import donjinkrawler.decorator.clothes.Clothing;
import donjinkrawler.decorator.clothes.Sombrero;

import java.util.List;

public class SombrerosEnemy extends EnemyClothingDecorator {

    public SombrerosEnemy(AbstractShellInterface source) {
        super(source);
    }

    @Override
    public List<Clothing> addClothing() {
        List<Clothing> clothes = wrappee.addClothing();
        clothes.add(new Sombrero());
        return clothes;
    }

    @Override
    public ShellType getShellType() {
        return ShellType.ENEMY;
    }
}