package donjinkrawler.items;

import donjinkrawler.items.tiers.CommonTier;
import donjinkrawler.items.tiers.LegendaryTier;
import donjinkrawler.items.tiers.RareTier;
import donjinkrawler.items.tiers.Tier;
import krawlercommon.items.*;

import java.util.Random;

public class ItemMaker {
    private static final Random random = new Random();

    public static BaseItem makeItem(ItemLocationData itemData) {
        if (itemData instanceof ArmorData) {
            return new Armor((ArmorData) itemData, getTier(), 10, 5, 5);
        } else if (itemData instanceof SpeedPotionData) {
            return new SpeedPotion((SpeedPotionData) itemData);
        } else if (itemData instanceof HealthPotionData) {
            return new HealthPotion((HealthPotionData) itemData);
        } else if (itemData instanceof DamagePotionData) {
            return new DamagePotion((DamagePotionData) itemData);
        } else if (itemData instanceof WeaponData) {
            return new Weapon((WeaponData) itemData, getTier(), 10, 5);
        }
        return null;
    }

    private static Tier getTier() {
        int luckyNumber = random.nextInt(1000);

        if (luckyNumber >= 800 && luckyNumber <= 950) {
            return new RareTier();
        }

        if (luckyNumber > 950) {
            return new LegendaryTier();
        }

        return new CommonTier();
    }
}
