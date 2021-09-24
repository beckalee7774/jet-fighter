package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Represents a Gun Test
public class GunTest {
    Jet jet;
    Gun gun;

    @BeforeEach
    public void runBefore() {
        jet = new Jet(6,4,2,1);
        gun = new Gun(jet,2,3);
    }

    @Test
    public void testGetSpeed() {
        assertEquals(2,gun.getSpeed());
    }

    @Test
    public void testGetPointsWhenHit() {
        assertEquals(3, gun.getPointsWhenHit());
    }

    @Test
    public void testShootProjectile() {
        assertEquals(6, gun.shootProjectile().getX());
        assertEquals(4, gun.shootProjectile().getY());
        assertEquals(2, gun.shootProjectile().getDx());
        assertEquals(1, gun.shootProjectile().getDy());
        assertEquals(2, gun.shootProjectile().getSpeed());
        assertEquals(3, gun.shootProjectile().getPointsWhenHit());
    }


}
