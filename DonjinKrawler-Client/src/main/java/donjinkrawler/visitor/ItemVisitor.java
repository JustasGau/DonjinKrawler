package donjinkrawler.visitor;

import donjinkrawler.items.*;

public interface ItemVisitor {
    void visit(Armor armor);

    void visit(DamagePotion damagePotion);

    void visit(HealthPotion healthPotion);

    void visit(SpeedPotion speedPotion);

    void visit(Weapon weapon);
}
