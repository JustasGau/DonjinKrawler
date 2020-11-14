package donjinkrawler.items.tiers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratedRareTierTest {
    @Test
    @Disabled
    public void testGetDamageBonus() {
        assertEquals(14.0, (new RareTier()).getDamageBonus());
    }

    @Test
    @Disabled
    public void testGetSpeedBonus() {
        assertEquals(10.5, (new RareTier()).getSpeedBonus());
    }

    @Test
    @Disabled
    public void testGetDefenseBonus() {
        assertEquals(14.0, (new RareTier()).getDefenseBonus());
    }

    @Test
    @Disabled
    public void testGetHpBonus() {
        assertEquals(17.5, (new RareTier()).getHpBonus());
    }

    @Test
    @Disabled
    public void testGetManaBonus() {
        assertEquals(8.4, (new RareTier()).getManaBonus());
    }

    @Test
    @Disabled
    public void testGetLastDigitOfTime() {
        assertEquals(6, (new RareTier()).getLastDigitOfTime());
    }
}

