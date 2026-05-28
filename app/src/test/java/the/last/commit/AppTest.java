package the.last.commit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import the.last.commit.models.Hero;
import the.last.commit.models.HeroFactory;
import the.last.commit.models.KatagiriHero;

class AppTest {
    @Test void testHeroInitialization() {
        Hero hero = HeroFactory.createHero(1, "Katagiri Rafly", "katagiri");
        assertEquals("Katagiri Rafly", hero.getName());
        assertEquals("katagiri", hero.getType());
        assertTrue(hero instanceof KatagiriHero, "Hero should be an instance of KatagiriHero");
        assertTrue(hero.getTotalMaxHp() > 0, "Hero max HP should be positive");
    }
}
