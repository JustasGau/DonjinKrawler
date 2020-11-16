package donjinkrawler.items.tiers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedRareTierTest {

    @Test
    public void testGetName() {
        assertEquals("Rare", (new RareTier()).getName());
    }

    @Test
    public void testGetDamageBonus() {
        RareTier rareTier = new RareTier();
        double dmg = (rareTier.getLastDigitOfTime() + 1) * 2;
        assertEquals(dmg, rareTier.getDamageBonus());
    }

    @Test
    public void testGetSpeedBonus() {
        RareTier rareTier = new RareTier();
        double spd = (rareTier.getLastDigitOfTime() + 1) * 1.5;
        assertEquals(spd, rareTier.getSpeedBonus());
    }

    @Test
    public void testGetDefenseBonus() {
        RareTier rareTier = new RareTier();
        double def = (rareTier.getLastDigitOfTime() + 1) * 2;
        assertEquals(def, rareTier.getDefenseBonus());
    }

    @Test
    public void testGetHpBonus() {
        RareTier rareTier = new RareTier();
        double hp = (rareTier.getLastDigitOfTime() + 1) * 2.5;
        assertEquals(hp, rareTier.getHpBonus());
    }

    @Test
    public void testGetManaBonus() {
        RareTier rareTier = new RareTier();
        double mna = (rareTier.getLastDigitOfTime() + 1) * 1.2;
        assertEquals(mna, rareTier.getManaBonus());
    }

    @Test
    public void testGetLastDigitOfTime() {
        assertEquals((int) ((System.currentTimeMillis() / 1000L) % 10), (new RareTier()).getLastDigitOfTime());
    }
}

