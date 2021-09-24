package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Represents a JsonReader Test
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            JetFighterGame game = reader.read();
            fail("IOExpectation expected");
        } catch (IOException e) {
            //pass
        }
    }


    @Test
    public void testReaderDefaultJetFighterGame() {
        JsonReader reader = new JsonReader("./data/testReaderDefaultGame.json");
        try {
            JetFighterGame game = reader.read();
            Jet jet1 = game.getJet1();
            Jet jet2 = game.getJet2();
            assertEquals(0,jet1.getPoints());
            assertEquals(0, jet1.getIndexOfCurrentGun());
            assertEquals(1, jet1.getGuns().size());
            assertEquals(0, jet2.getPoints());
            assertEquals(0, jet2.getIndexOfCurrentGun());
            assertEquals(1, jet2.getGuns().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderInvalidIndexJetFighterGame() {
        JsonReader reader = new JsonReader("./data/testReaderInvalidIndexGame.json");
        try {
            JetFighterGame game = reader.read();
            Jet jet1 = game.getJet1();
            Jet jet2 = game.getJet2();
            assertEquals(1,jet1.getPoints());
            assertEquals(0, jet1.getIndexOfCurrentGun());
            assertEquals(1, jet1.getGuns().size());
            assertEquals(2, jet2.getPoints());
            assertEquals(0, jet2.getIndexOfCurrentGun());
            assertEquals(1, jet2.getGuns().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralJetFighterGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
        try {
            JetFighterGame game = reader.read();
            List<Projectile> jet1Projectiles = game.getJet1Projectiles();
            List<Projectile> jet2Projectiles = game.getJet2Projectiles();
            List<Asteroid> asteroids = game.getAsteroids();
            Jet jet1 = game.getJet1();
            Jet jet2 = game.getJet2();
            assertEquals(3, jet1.getGuns().size());
            List<Gun> guns1 = jet1.getGuns();
            checkGun(1,1, guns1.get(0));
            checkGun(3,5, guns1.get(1));
            checkGun(5,8, guns1.get(2));
            checkJet(2, 5, 200,150,0,0, jet1);
            assertEquals(2, jet2.getGuns().size());
            List<Gun> guns2 = jet2.getGuns();
            checkGun(1,1, guns2.get(0));
            checkGun(4,6, guns2.get(1));
            checkJet(1, 3, 100, 150,0,0, jet2);
            checkProjectile(20,30,1,1,2,5, jet1Projectiles.get(0));
            checkProjectile(50,50,1,1,5,8, jet1Projectiles.get(1));
            checkProjectile(0,0,1,1,4,6, jet2Projectiles.get(0));
            checkAsteroid(10,10,-1,1,asteroids.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
