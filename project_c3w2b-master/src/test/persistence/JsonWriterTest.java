package persistence;

import model.*;
import model.exceptions.InvalidGunIndexException;
import persistence.JsonTest;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Represents a JsonWriter Test
public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try{
            JetFighterGame game = new JetFighterGame();
            JsonWriter writer = new JsonWriter("./data/my\0illigalFileName.json");
            writer.open();
            fail("IOException was expected and not caught");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void testWriterDefaultGame() {
        try{
            JetFighterGame game = new JetFighterGame();
            JsonWriter writer = new JsonWriter("./data/testWriterDefaultGame.json");
            writer.open();
            writer.writeFile(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDefaultGame.json");
            game = reader.read();
            Jet jet1 = game.getJet1();
            Jet jet2 = game.getJet2();
            assertEquals(0,jet1.getPoints());
            assertEquals(0, jet1.getIndexOfCurrentGun());
            assertEquals(1, jet1.getGuns().size());
            assertEquals(0, jet2.getPoints());
            assertEquals(0, jet2.getIndexOfCurrentGun());
            assertEquals(1, jet2.getGuns().size());
        } catch (IOException e) {
            fail("Exception should not haven been thrown but was");
        }
    }

    @Test
    public void testWriterGeneralGame() {
        try {
            JetFighterGame game = new JetFighterGame();
            Projectile p = new Projectile(20,20,1,0,10,2);
            Projectile p2 = new Projectile(20,20,0,1,5,4);
            Asteroid a = new Asteroid(15,10,1,1);
            game.addAsteroid(a);
            game.addToJet1Projectiles(p);
            game.addToJet2Projectiles(p2);
            Jet jet1 = new Jet(200,150,0,0);
            new Gun(jet1, 8,1);
            new Gun(jet1, 6,3);
            try {
                jet1.chooseGun(2);
            } catch (InvalidGunIndexException e) {
                System.out.println("Gun index was not valid");
            }
            jet1.setPoints(10);

            Jet jet2 = new Jet(100,150,0,0);
            new Gun(jet2, 2,4);
            try {
                jet2.chooseGun(1);
            } catch (InvalidGunIndexException e) {
                System.out.println("Gun index was not valid");
            }
            jet2.setPoints(5);
            game.setJet1(jet1);
            game.setJet2(jet2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            writer.open();
            writer.writeFile(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            game = reader.read();
            Jet jet3 = game.getJet1();
            Jet jet4 = game.getJet2();
            Projectile p3 = game.getJet1Projectiles().get(0);
            Projectile p4 = game.getJet2Projectiles().get(0);

            assertEquals(3, jet3.getGuns().size());
            List<Gun> guns1 = jet3.getGuns();
            checkGun(1,1, guns1.get(0));
            checkGun(8,1, guns1.get(1));
            checkGun(6,3, guns1.get(2));
            checkJet(2, 10, 200,150,0,0, jet3);
            assertEquals(2, jet4.getGuns().size());
            List<Gun> guns2 = jet4.getGuns();
            checkGun(1,1, guns2.get(0));
            checkGun(2,4, guns2.get(1));
            checkJet(1, 5, 100, 150,0,0, jet4);
            checkProjectile(20,20,1,0,10,2, p3);
            checkProjectile(20,20,0,1,5,4, p4);
            checkAsteroid(15,10,1,1, a);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
