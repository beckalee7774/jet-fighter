package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Represents a Projectile Test
public class ProjectileTest {
    Jet jet;
    Projectile projectile;
    int jetX = 50;
    int jetY = 50;

    @BeforeEach
    public void runBefore() {
        jet = new Jet(jetX, jetY, 1, 1);
    }

    @Test
    public void testHitJetProjectileHasHitJetDueToXCoordinate() {
        projectile = new Projectile(jetX + Jet.WIDTH / 2 - 1,jetY,1,1,3,2);
        assertTrue(projectile.hasHitJet(jet));
    }

    @Test
    public void testHitJetProjectileHasHitJetDueToYCoordinate() {
        projectile = new Projectile(jetX, jetY + Jet.HEIGHT / 2 - 1, 1, 1, 1, 1);
        assertTrue(projectile.hasHitJet(jet));
    }

    @Test
    public void testHitJetProjectileHasNotHitJetDueToX() {
        projectile = new Projectile(jetX + Jet.WIDTH, jetY,1,1,3,2);
        assertFalse(projectile.hasHitJet(jet));
    }

    @Test
    public void testHitJetProjectileHasNotHitJetDueToY() {
        projectile = new Projectile(jetX, jetY + Jet.HEIGHT,1,1,3,2);
        assertFalse(projectile.hasHitJet(jet));
    }



}
