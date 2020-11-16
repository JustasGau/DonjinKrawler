package donjinkrawler.items;

import donjinkrawler.items.tiers.CommonTier;
import krawlercommon.items.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArmorTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        ArmorData data = new ArmorData(1, 2, 15);
        Armor original = new Armor(data, new CommonTier(), 15, 20, 25);
        Armor clone = original.deepCopy();
        assertEquals(original.getData(), clone.getData());
        assertEquals(original.getDefense(), clone.getDefense());
        assertEquals(original.getHp(), clone.getHp());
        assertEquals(original.getMana(), clone.getMana());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        ArmorData data = new ArmorData(1, 2, 15);
        Armor original = new Armor(data, new CommonTier(), 15, 20, 25);
        Armor clone = original.clone();
        assertEquals(original.getData(), clone.getData());
        assertEquals(original.getDefense(), clone.getDefense());
        assertEquals(original.getHp(), clone.getHp());
        assertEquals(original.getMana(), clone.getMana());
    }

    @Test
    public void testDescription() {
        ArmorData data = new ArmorData(1, 2, 15);
        Armor original = new Armor(data, new CommonTier(), 15, 20, 25);
        assertEquals("Tier: " + original.tier.getName() + "\n"
                + "Defense: " + original.getDefense() + "\n"
                + "HP: " + original.getHp() + "\n"
                + "Mana: " + original.getMana(), original.getDescription());
    }
}
