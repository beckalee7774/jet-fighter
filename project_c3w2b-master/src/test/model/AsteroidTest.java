package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Represents an Asteroid Test
public class AsteroidTest {

    Asteroid asteroid;
    Asteroid asteroid2;
    Jet jet;
    int jetX = 50;
    int jetY = 50;

    @BeforeEach
    public void runBefore(){
        asteroid = new Asteroid(0,0,1,1);
        jet = new Jet(jetX, jetY, 1, 1);
    }

    @Test
    public void testMove() {
        asteroid.move();
        assertEquals(1, asteroid.getX());
        assertEquals(1, asteroid.getY());
    }

    @Test
    public void testHitJetAsteroidHasHitJetDueToXCoordinate() {
        asteroid2 = new Asteroid(jetX + Jet.WIDTH / 2 - 1,jetY,1,1);
        assertTrue(asteroid2.hasHitJet(jet));
    }

    @Test
    public void testHitJetAsteroidHasHitJetDueToYCoordinate() {
        asteroid2 = new Asteroid(jetX, jetY + Jet.HEIGHT / 2 - 1, 1, 1);
        assertTrue(asteroid2.hasHitJet(jet));
    }

    @Test
    public void testHitJetAsteroidHasNotHitJetDueToX() {
        asteroid2 = new Asteroid(jetX + Jet.WIDTH, jetY,1,1);
        assertFalse(asteroid2.hasHitJet(jet));
    }

    @Test
    public void testHitJetAsteroidHasNotHitJetDueToY() {
        asteroid2 = new Asteroid(jetX, jetY + Jet.HEIGHT,1,1);
        assertFalse(asteroid2.hasHitJet(jet));
    }


}
