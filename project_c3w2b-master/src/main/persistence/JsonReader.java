package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import model.exceptions.InvalidGunIndexException;
import org.json.*;

// Represents a reader that reads game from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public JetFighterGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: parses game from JSON object and returns it
    private JetFighterGame parseGame(JSONObject jsonObject) {
        JetFighterGame game = new JetFighterGame();
        addJets(game, jsonObject);
        addJetsProjectiles(game, jsonObject);
        addAsteroids(game, jsonObject);
        return game;
    }

    //Modifies: game
    //Effects adds asteroids to game using data from jsonObject
    private void addAsteroids(JetFighterGame game, JSONObject jsonObject) {
        JSONArray asteroids = jsonObject.getJSONArray("asteroids");
        for (Object json : asteroids) {
            addAsteroidToAsteroids(json, game.getAsteroids());
        }
    }

    // Modifies: game
    // adds a new asteroid to asteroids given the data from obj
    private void addAsteroidToAsteroids(Object obj, List<Asteroid> asteroids) {
        JSONObject json = (JSONObject) obj;
        int x = json.getInt("x");
        int y = json.getInt("y");
        int dx = json.getInt("dx");
        int dy = json.getInt("dy");
        Asteroid asteroid = new Asteroid(x, y, dx, dy);
        asteroids.add(asteroid);
    }

    //Modifies: game
    //Effects adds jet1 and jet2 projectiles to game using data from jsonObject
    private void addJetsProjectiles(JetFighterGame game, JSONObject jsonObject) {
        createJet1Projectiles(game, jsonObject);
        createJet2Projectiles(game, jsonObject);
    }

    //Modifies: game
    //Effects adds jets to game using data from jsonObject
    private void addJets(JetFighterGame game, JSONObject jsonObject) {
        createJet1(game, jsonObject);
        createJet2(game, jsonObject);
    }

    //Modifies: game
    //Effects: creates jet1Projectile and sets jet1Projectiles in game to it
    private void createJet1Projectiles(JetFighterGame game, JSONObject jsonObject) {
        JSONArray jet1Projectiles = jsonObject.getJSONArray("jet1 projectiles");
        for (Object json : jet1Projectiles) {
            addProjectileToJetProjectiles(json, game.getJet1Projectiles());
        }
    }

    //Modifies: game
    //Effects: creates jet2Projectile and sets jet2Projectiles in game to it
    private void createJet2Projectiles(JetFighterGame game, JSONObject jsonObject) {
        JSONArray jet2Projectiles = jsonObject.getJSONArray("jet2 projectiles");
        for (Object json : jet2Projectiles) {
            addProjectileToJetProjectiles(json, game.getJet2Projectiles());
        }
    }

    //Modifies: game
    //Effects: constructs a projectile given obj then adds to jetProjectiles
    private void addProjectileToJetProjectiles(Object obj, List<Projectile> jetProjectiles) {
        JSONObject json = (JSONObject) obj;
        int x = json.getInt("x");
        int y = json.getInt("y");
        int dx = json.getInt("dx");
        int dy = json.getInt("dy");
        int speed = json.getInt("speed");
        int pointsWhenHit = json.getInt("points");
        Projectile projectile = new Projectile(x, y, dx, dy, speed, pointsWhenHit);
        jetProjectiles.add(projectile);
    }

    //Modifies: game
    //Effects: creates jet2 and sets jet2 in game to it
    private void createJet2(JetFighterGame game, JSONObject jsonObject) {
        Object jet2 = jsonObject.get("jet2");
        JSONObject jsonJet2 = (JSONObject) jet2;
        int objectX = jsonJet2.getInt("x");
        int objectY = jsonJet2.getInt("y");
        int objectDx = jsonJet2.getInt("dx");
        int objectDy = jsonJet2.getInt("dy");
        Jet actualJet2 = new Jet(objectX, objectY, objectDx, objectDy);

        int points = jsonJet2.getInt("points");
        actualJet2.setPoints(points);

        JSONArray jsonGunArray = jsonJet2.getJSONArray("guns");
        for (Object json : jsonGunArray) {
            addGunToJet(json, actualJet2);
        }

        int indexOfCurrentGun2 = jsonJet2.getInt("current gun index");
        try {
            actualJet2.chooseGun(indexOfCurrentGun2);
        } catch (InvalidGunIndexException e) {
            System.out.println("Gun index of Jet 2 read from file was not valid");
        }
        game.setJet2(actualJet2);
    }

    //Modifies: game
    //Effects: creates jet1 and sets jet1 in game to it
    private void createJet1(JetFighterGame game, JSONObject jsonObject) {
        Object jet1 = jsonObject.get("jet1");
        JSONObject jsonJet1 = (JSONObject) jet1;
        int objectX = jsonJet1.getInt("x");
        int objectY = jsonJet1.getInt("y");
        int objectDx = jsonJet1.getInt("dx");
        int objectDy = jsonJet1.getInt("dy");
        Jet actualJet1 = new Jet(objectX, objectY, objectDx, objectDy);

        int points = jsonJet1.getInt("points");
        actualJet1.setPoints(points);

        JSONArray jsonGunArray = jsonJet1.getJSONArray("guns");
        for (Object json : jsonGunArray) {
            addGunToJet(json, actualJet1);
        }

        int indexOfCurrentGun1 = jsonJet1.getInt("current gun index");
        try {
            actualJet1.chooseGun(indexOfCurrentGun1);
        } catch (InvalidGunIndexException e) {
            System.out.println("Gun index of Jet 1 read from file was not valid");
        }
        game.setJet1(actualJet1);
    }

    //Modifies jet
    //Effects: gets gun components from jsonObject, creates a new gun with those components
    //         then adds them to the jet
    private void addGunToJet(Object obj, Jet jet) {
        JSONObject json = (JSONObject) obj;
        int gunSpeed = json.getInt("gun speed");
        int pointsWhenHit = json.getInt("gun number of points when hit");
        new Gun(jet, gunSpeed, pointsWhenHit);
    }


    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

}
