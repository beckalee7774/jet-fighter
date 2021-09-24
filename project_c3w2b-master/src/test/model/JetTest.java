package model;

import model.exceptions.InvalidGunIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Represents a Jet Test
public class JetTest {

    Jet jet;

    @BeforeEach
    public void runBefore() {
        jet = new Jet(0,0,1,1);
    }

    @Test
    public void testSetSpeed() {
        jet.setSpeed(5);
        assertEquals(5, jet.getSpeed());
    }

    @Test
    public void testSetPoints() {
        jet.setPoints(11);
        assertEquals(11, jet.getPoints());
    }

    @Test
    public void testAddGun() {
        new Gun(jet, 3, 2);
        assertEquals(2,jet.getGuns().size());
    }

    @Test
    public void testGetIndexOfCurrentGun() {
        new Gun(jet, 3,2);
        new Gun(jet,3,4);
        assertEquals(0, jet.getIndexOfCurrentGun());
        try {
            jet.chooseGun(1);
        } catch (InvalidGunIndexException e) {
            fail();
        }
        assertEquals(1, jet.getIndexOfCurrentGun());
        try {
            jet.chooseGun(2);
        } catch (InvalidGunIndexException e) {
            fail();
        }
        assertEquals(2, jet.getIndexOfCurrentGun());

    }


    @Test
    public void testChooseGunIndexInBoundsExpectNoExceptionThrown() {
        Gun gun1 = new Gun(jet, 3,2);
        Gun gun2 = new Gun(jet,3,4);
        try {
            jet.chooseGun(2);
        } catch (InvalidGunIndexException e) {
            fail();
        }
        assertEquals(gun2, jet.getCurrentGun());
        try {
            jet.chooseGun(1);
        } catch (InvalidGunIndexException e) {
            fail();
        }
        assertEquals(gun1, jet.getCurrentGun());
    }

    @Test
    public void testChooseGunIndexOutOfBoundsExpectExceptionThrown() {
        Gun gun = new Gun(jet, 5,4);
        try {
            jet.chooseGun(1);
        } catch (InvalidGunIndexException e) {
            fail();
        }
        assertEquals(gun, jet.getCurrentGun());
        try {
            jet.chooseGun(2);
            fail();
        } catch (InvalidGunIndexException e) {
            //expected
        }
        assertEquals(gun, jet.getCurrentGun());
        try {
            jet.chooseGun(-1);
            fail();
        } catch (InvalidGunIndexException e) {
            //expected
        }
        assertEquals(gun, jet.getCurrentGun());
        try {
            jet.chooseGun(5);
            fail();
        } catch (InvalidGunIndexException e) {
            //expected
        }
        assertEquals(gun, jet.getCurrentGun());

    }

    @Test
    public void testAddPoint() {
        jet.addPoints(1);
        jet.addPoints(1);
        assertEquals(2, jet.getPoints());
        jet.addPoints(3);
        assertEquals(5, jet.getPoints());
    }

    @Test
    public void testDeductPoint() {
        jet.addPoints(10);
        assertEquals(10, jet.getPoints());
        jet.deductPoints(3);
        assertEquals(7, jet.getPoints());
        jet.deductPoints(5);
        assertEquals(2, jet.getPoints());
    }

    @Test
    public void testKeepJetWithinScreenJetOffScreenTooFarRight() {
        jet.setX(JetFighterGame.ScreenSizeX - Jet.WIDTH / 2 + 1);
        jet.keepJetWithinScreen();
        assertEquals(JetFighterGame.ScreenSizeX  - Jet.WIDTH / 2 , jet.getX());
    }

    @Test
    public void testKeepJetWithinScreenJetOffScreenTooFarLeft() {
        jet.setX(Jet.WIDTH / 2 - 1);
        jet.keepJetWithinScreen();
        assertEquals(Jet.WIDTH / 2, jet.getX());
    }

    @Test
    public void testKeepJetWithinScreenJetOffScreenTooFarUp() {
        jet.setY(Jet.HEIGHT / 2 - 1);
        jet.keepJetWithinScreen();
        assertEquals(Jet.HEIGHT / 2, jet.getY());
    }

    @Test
    public void testKeepJetWithinScreenJetOffScreenTooFarDown() {
        jet.setY(JetFighterGame.ScreenSizeY - Jet.HEIGHT / 2 + 1);
        jet.keepJetWithinScreen();
        assertEquals(JetFighterGame.ScreenSizeY  - Jet.HEIGHT / 2, jet.getY());
    }

    @Test
    public void testKeepJetWithinScreenJetIsInScreen() {
        jet.keepJetWithinScreen();
        jet.setX(50);
        jet.setY(50);
        assertEquals(50, jet.getX());
        assertEquals(50, jet.getY());
    }

    @Test
    public void gunsToString() {
        new Gun(jet,5,2);
        new Gun(jet,4,3);
        String gunsStr = jet.gunsToString();
        assertEquals("[s:1 p:1] [s:5 p:2] [s:4 p:3] ", gunsStr);
    }
}
