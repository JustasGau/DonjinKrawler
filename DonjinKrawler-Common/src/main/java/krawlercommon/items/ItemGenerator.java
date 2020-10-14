package krawlercommon.items;

import java.util.Random;

public class ItemGenerator {
    private enum ItemType {
        ARMOR,
        DMGPOT,
        HPPOT,
        SPDPOT,
        WEAPON
    }

    public ItemGenerator() {

    }

    public static ItemLocationData generateRandomItem(int id) {
        Random rand = new Random();
        return generateRandomItem(id, rand.nextInt(300) + 30, rand.nextInt(300) + 30);
    }

    public static ItemLocationData generateRandomItem(int id, int x, int y) {

        ItemType itemType = ItemType.values()[new Random().nextInt(ItemType.values().length)];
        if (itemType == ItemType.ARMOR) {
            return new ArmorData(id, x, y);
        } else if (itemType == ItemType.DMGPOT) {
            return new DamagePotionData(id, x, y);
        } else if (itemType == ItemType.HPPOT) {
            return new HealthPotionData(id, x, y);
        } else if (itemType == ItemType.SPDPOT) {
            return new SpeedPotionData(id, x, y);
        } else if (itemType == ItemType.WEAPON) {
            return new WeaponData(id, x, y);
        }
        return null;
    }

    public static ItemLocationData generateRandomPotion(int id) {
        Random rand = new Random();
        return generateRandomPotion(id, rand.nextInt(300) + 30, rand.nextInt(300) + 30);
    }

    public static ItemLocationData generateRandomPotion(int id, int x, int y) {
        Random rand = new Random();
        int num = rand.nextInt(3) + 1;
        if (num == 1) {
            return new HealthPotionData(id, x, y);
        } else if (num == 2) {
            return new DamagePotionData(id, x, y);
        } else {
            return new SpeedPotionData(id, x, y);
        }
    }
}
