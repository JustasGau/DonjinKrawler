package donjinkrawler.decorator;

import donjinkrawler.AbstractShellInterface;
import donjinkrawler.ShellType;
import donjinkrawler.decorator.clothes.Clothing;
import donjinkrawler.decorator.clothes.Poncho;

import java.util.List;

public class PonchosEnemy extends EnemyClothingDecorator {

    public PonchosEnemy(AbstractShellInterface source) {
        super(source);
    }

    @Override
    public List<Clothing> addClothing() {
        List<Clothing> clothes = wrappee.addClothing();
        clothes.add(new Poncho());
        return clothes;
    }

    @Override
    public ShellType getShellType() {
        return ShellType.ENEMY;
    }

}