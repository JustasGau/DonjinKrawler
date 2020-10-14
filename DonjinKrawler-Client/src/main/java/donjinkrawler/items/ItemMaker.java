package donjinkrawler.items;

import krawlercommon.items.*;

public class ItemMaker {

    public static BaseItem makeItem(ItemData itemData) {
        if (itemData instanceof ArmorData) {
            return new Armor((ArmorData) itemData);
        } else if (itemData instanceof SpeedPotionData) {
            return new SpeedPotion((SpeedPotionData) itemData);
        } else if (itemData instanceof HealthPotionData) {
            return new HealthPotion((HealthPotionData) itemData);
        } else if (itemData instanceof DamagePotionData) {
            return new DamagePotion((DamagePotionData) itemData);
        } else if (itemData instanceof WeaponData) {
            return new Weapon((WeaponData) itemData);
        }
        return null;
    }
}
