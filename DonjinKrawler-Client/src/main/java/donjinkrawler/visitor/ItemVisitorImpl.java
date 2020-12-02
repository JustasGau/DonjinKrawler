package donjinkrawler.visitor;

import donjinkrawler.Inventory;
import donjinkrawler.items.*;
import krawlercommon.PlayerData;
import krawlercommon.composite.FinalBonus;
import krawlercommon.composite.RawBonus;

public class ItemVisitorImpl implements ItemVisitor {

    private PlayerData data;
    private Inventory inventory;
    private RawBonus armorBonus;
    private RawBonus weaponBonus;

    public ItemVisitorImpl(PlayerData playerData, Inventory inventory, RawBonus armorBonus, RawBonus weaponBonus) {
        this.data = playerData;
        this.inventory = inventory;
        this.armorBonus = armorBonus;
        this.weaponBonus = weaponBonus;
    }

    @Override
    public void visit(Armor armor) {
        if (armorBonus != null) {
            data.getMaxHealth().removeRawBonus(armorBonus);
        }
        armorBonus = new RawBonus(armor.getHp(), 0);
        data.getMaxHealth().addRawBonus(armorBonus);
        data.adjustHealthWithMaxHealth();
        inventory.addArmor(armor);
    }

    @Override
    public void visit(DamagePotion damagePotion) {
        FinalBonus finalBonus = new FinalBonus(0, damagePotion.getMultiplier(), 10);
        data.getDamage().addFinalBonus(finalBonus);
        finalBonus.startTimer(data.getDamage());
    }

    @Override
    public void visit(HealthPotion healthPotion) {
        data.setHealth(Math.min(data.getHealth() + healthPotion.getValue(), data.getMaxHealth().getFinalValue()));
    }

    @Override
    public void visit(SpeedPotion speedPotion) {
        FinalBonus finalBonus = new FinalBonus(0, speedPotion.getMultiplier(), 5);
        data.getSpeed().addFinalBonus(finalBonus);
        finalBonus.startTimer(data.getSpeed());
    }

    @Override
    public void visit(Weapon weapon) {
        if (weaponBonus != null) {
            data.getDamage().removeRawBonus(weaponBonus);
        }
        weaponBonus = new RawBonus(weapon.getDamage(), 0);
        data.getDamage().addRawBonus(weaponBonus);
        inventory.addWeapon(weapon);
    }
}
