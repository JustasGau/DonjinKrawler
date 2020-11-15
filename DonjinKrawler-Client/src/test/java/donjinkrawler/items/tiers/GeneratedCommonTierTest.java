package donjinkrawler.items.tiers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneratedCommonTierTest {

    @Test
    public void testGetName() {
        assertEquals("Common", (new CommonTier()).getName());
    }

    @Test
    public void testGetDamageBonus() {
        CommonTier commonTier = new CommonTier();
        assertTrue(commonTier.getDamageBonus() > 0);
    }

    @Test
    public void testGetSpeedBonus() {
        CommonTier commonTier = new CommonTier();
        assertTrue(commonTier.getSpeedBonus() > 0);
    }

    @Test
    public void testGetDefenseBonus() {
        CommonTier commonTier = new CommonTier();
        assertTrue(commonTier.getDefenseBonus() > 0);
    }

    @Test
    public void testGetHpBonus() {
        CommonTier commonTier = new CommonTier();
        assertTrue(commonTier.getHpBonus() > 0);
    }

    @Test
    public void testGetManaBonus() {
        CommonTier commonTier = new CommonTier();
        assertTrue(commonTier.getManaBonus() > 0);
    }
}

