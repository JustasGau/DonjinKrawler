package donjinkrawler.items.tiers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratedLegendaryTierTest {

    @Test
    public void testGetName() {
        assertEquals("Legendary", (new LegendaryTier()).getName());
    }

    @Test
    public void testGetLastDigitOfTime() {
        assertEquals((int) ((System.currentTimeMillis() / 1000L) % 10), (new LegendaryTier()).getLastDigitOfTime());
    }

    @Test
    public void testGetDamageBonus() {
        LegendaryTier legendaryTier = new LegendaryTier();
        assertTrue(legendaryTier.getDamageBonus() > 0);
    }

    @Test
    public void testGetSpeedBonus() {
        LegendaryTier legendaryTier = new LegendaryTier();
        assertTrue(legendaryTier.getSpeedBonus() > 0);
    }

    @Test
    public void testGetDefenseBonus() {
        LegendaryTier legendaryTier = new LegendaryTier();
        assertTrue(legendaryTier.getDefenseBonus() > 0);
    }

    @Test
    public void testGetHpBonus() {
        LegendaryTier legendaryTier = new LegendaryTier();
        assertTrue(legendaryTier.getHpBonus() > 0);
    }

    @Test
    public void testGetManaBonus() {
        LegendaryTier legendaryTier = new LegendaryTier();
        assertTrue(legendaryTier.getManaBonus() > 0);
    }
}

