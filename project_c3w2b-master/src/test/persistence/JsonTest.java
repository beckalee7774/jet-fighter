package persistence;

import model.Asteroid;
import model.Gun;
import model.Jet;
import model.Projectile;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Represents a Json Test
public class JsonTest {
    protected void checkJet(int currentGunIndex, int points, int x, int y, int dx, int dy, Jet jet) {
        assertEquals(currentGunIndex, jet.getIndexOfCurrentGun());
        assertEquals(points, jet.getPoints());
        assertEquals(x, jet.getX());
        assertEquals(y, jet.getY());
        assertEquals(dx, jet.getDx());
        assertEquals(dy, jet.getDy());
    }
    protected void checkGun(int speed, int pointsWhenHit, Gun gun) {
        assertEquals(speed, gun.getSpeed());
        assertEquals(pointsWhenHit, gun.getPointsWhenHit());
    }

    protected void checkProjectile(int x, int y, int dx, int dy, int speed, int pointsWhenHit, Projectile projectile) {
        assertEquals(x, projectile.getX());
        assertEquals(y, projectile.getY());
        assertEquals(dx, projectile.getDx());
        assertEquals(dy, projectile.getDy());
        assertEquals(speed, projectile.getSpeed());
        assertEquals(pointsWhenHit, projectile.getPointsWhenHit());
    }

    protected void checkAsteroid(int x, int y, int dx, int dy, Asteroid asteroid) {
        assertEquals(x, asteroid.getX());
        assertEquals(y, asteroid.getY());
        assertEquals(dx, asteroid.getDx());
        assertEquals(dy, asteroid.getDy());
    }

}

