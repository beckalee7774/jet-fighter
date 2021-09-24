package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Represents a JetFighter game
public class JetFighterGame {
    protected Jet jet1;
    protected Jet jet2;
    protected List<Projectile> jet1Projectiles;
    protected List<Projectile> jet2Projectiles;
    public static final int ScreenSizeX = 800;
    public static final int ScreenSizeY = 700;
    protected List<GameObject> gameObjects;
    public static final int MAXGUNAMOUNT = 4;
    private boolean isGameOver;
    private boolean paused;
    public static final int POINTSTOWIN = 20;
    public List<Asteroid> asteroids;

    //Effects: constructs a JetFighterGame
    public JetFighterGame() {
        jet1 = new Jet(ScreenSizeX / 3, ScreenSizeY / 2,1,0);
        jet2 = new Jet(2 * ScreenSizeX / 3, ScreenSizeY / 2,-1,0);
        jet1Projectiles = new ArrayList<>();
        jet2Projectiles = new ArrayList<>();
        gameObjects = new ArrayList<>();
        gameObjects.add(jet1);
        gameObjects.add(jet2);
        asteroids = new ArrayList<>();
    }

    //getters
    public Jet getJet1() {
        return jet1;
    }

    public Jet getJet2() {
        return jet2;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isPaused() {
        return paused;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public List<Projectile>  getJet1Projectiles() {
        return jet1Projectiles;
    }

    public List<Projectile> getJet2Projectiles() {
        return jet2Projectiles;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    //setters
    public void setJet1(Jet jet) {
        jet1 = jet;
    }

    public void setJet2(Jet jet) {
        jet2 = jet;
    }

    public void setPaused() {
        paused = true;
    }

    public void setGameOver() {
        isGameOver = true;
    }

    //Modifies: this
    //Effects: adds to jet1Projectiles and gameObjects
    public void addToJet1Projectiles(Projectile p) {
        jet1Projectiles.add(p);
        gameObjects.add(p);
    }

    //Modifies: this
    //Effects: adds to jet2Projectiles and gameObjects
    public void addToJet2Projectiles(Projectile p) {
        jet2Projectiles.add(p);
        gameObjects.add(p);
    }

    //Modifies: this
    //Effects: adds to asteroids and gameObjects
    public void addAsteroid(Asteroid asteroid) {
        asteroids.add(asteroid);
        gameObjects.add(asteroid);
    }

    //Modifies: this
    //Effects: removes from asteroids and gameObjects
    public void removeAsteroid(Asteroid asteroid) {
        if (asteroids.contains(asteroid)) {
            asteroids.remove(asteroid);
            gameObjects.remove(asteroid);
        }
    }


    //Requires: if projectile is in jet1Projectiles or jet2Projectiles then it must be in gameObjects
    //Modifies: this
    //Effects: removes from jetProjectiles and gameObjects
    public void removeProjectile(Projectile p) {
        if (jet1Projectiles.contains(p)) {
            jet1Projectiles.remove(p);
            gameObjects.remove(p);
        } else if (jet2Projectiles.contains(p)) {
            jet2Projectiles.remove(p);
            gameObjects.remove(p);
        }
    }

    // Updates this game
    // Modifies: this
    // Effects: checks
    //          - if projectiles moved off screen
    //          - if jet1 or jet2 has been hit by an enemy projectile
    //          - keeps both jets within screen
    //          - moves projectiles from both jets
    public void update() {
        if (!paused) {
            checkGameObjectsMovedOffScreen();
            checkJetGotHitByProjectiles(jet1, jet2Projectiles, jet2);
            checkJetGotHitByProjectiles(jet2, jet1Projectiles, jet1);
            checkJetGotHitByAsteroids(jet1);
            checkJetGotHitByAsteroids(jet2);
            jet1.keepJetWithinScreen();
            jet2.keepJetWithinScreen();
            moveProjectiles();
            moveAsteroids();
            checkGameOver();
        }
    }

    //Moves Asteroid
    //Modifies: this
    //Effects: loops through asteroids and moves all of them
    public void moveAsteroids() {
        for (Asteroid asteroid: asteroids) {
            asteroid.move();
        }
    }


    //Moves Projectiles
    //Modifies: this
    //Effects: loops through jet1Projectiles and moves all of them
    //also, loops through jet2Projectiles and moves all of them
    public void moveProjectiles() {
        for (Projectile projectile: jet1Projectiles) {
            projectile.move();
        }

        for (Projectile projectile: jet2Projectiles) {
            projectile.move();
        }
    }

    //Checks if Jet got hit by a projectile
    //Modifies: this
    //Effects: Checks if jet has been hit by an enemy projectile
    // if so, adds points to enemy jet and removes projectiles from
    // objects to draw and enemy's jetProjectiles
    public void checkJetGotHitByProjectiles(Jet jet, List<Projectile> enemyProjectiles, Jet enemyJet) {
        List<Projectile> projectilesToRemove = new ArrayList<>();
        for (Projectile projectile: enemyProjectiles) {
            if (projectile.hasHitJet(jet)) {
                enemyJet.addPoints(projectile.getPointsWhenHit());
                projectilesToRemove.add(projectile);
            }
        }
        removeProjectiles(projectilesToRemove);
    }

    //Checks if Jet got hit by an asteroid
    //Modifies: this
    //Effects: Checks if jet has been hit by an asteroid
    // if so, deducts 1 point from jet and removes projectiles from
    // objects to draw and enemy's jetProjectiles
    public void checkJetGotHitByAsteroids(Jet jet) {
        List<Asteroid> asteroidsToRemove = new ArrayList<>();
        for (Asteroid asteroid: asteroids) {
            if (asteroid.hasHitJet(jet)) {
                jet.deductPoints(1);
                asteroidsToRemove.add(asteroid);
            }
        }
        removeAsteroids(asteroidsToRemove);
    }


    //Checks if a gameObject has moved off the screen
    //Modifies: this
    //Effects: checks if a gameObject
    // if so, removes gameObject from gameObjects and
    // also if a projectile: removes from projectiles from it's former jet
    // if an asteroid: removes from asteroids
    public void checkGameObjectsMovedOffScreen() {
        List<GameObject> gameObjectsMovedOffScreen = new ArrayList<>();
        for (GameObject gameObject: gameObjects) {
            if (gameObject.getX() > JetFighterGame.ScreenSizeX  - Projectile.WIDTH / 2
                    || gameObject.getX() < - Projectile.WIDTH / 2
                    || gameObject.getY() > JetFighterGame.ScreenSizeY - Projectile.HEIGHT / 2
                    || gameObject.getY() < - Projectile.HEIGHT / 2) {
                gameObjectsMovedOffScreen.add(gameObject);
            }
        }
        removeGameObjects(gameObjectsMovedOffScreen);
    }

    // Modifies: this
    // Effects: if the list of Game Objects is a list of Projectiles
    // then removes them from their corresponding projectile list
    // else if it is a list of Asteroids removes all the asteroids
    // in gameObjectsMovedOffScreen from asteroids list therefore removing from this game
    public void removeGameObjects(List<GameObject> gameObjectsMovedOffScreen) {
        for (GameObject gameObject: gameObjectsMovedOffScreen) {
            if (gameObject.getClass() == Projectile.class) {
                Projectile projectile = (Projectile) gameObject;
                removeProjectile(projectile);
            } else if (gameObject.getClass() == Asteroid.class) {
                Asteroid asteroid = (Asteroid) gameObject;
                removeAsteroid(asteroid);
            }
        }
    }

    // Modifies: this
    // Effects: removes all asteroids in asteroids parameter from this game
    public void removeAsteroids(List<Asteroid> asteroids) {
        for (Asteroid asteroid: asteroids) {
            removeAsteroid(asteroid);
        }
    }

    //Modifies: jetProjectiles
    //Effects: removes all elements in projectilesToRemoveFrom jetProjectiles
    public void removeProjectiles(List<Projectile> projectilesToRemove) {
        for (Projectile projectile: projectilesToRemove) {
            removeProjectile(projectile);
        }
    }


    // Responds to key press codes
    // MODIFIES: this
    // EFFECTS:  calls jetControl on jet1 and jet2
    public void keyPressed(int k) {
        if (!paused) {
            jet1Control(k);
            jet2Control(k);
        }
        if (k == 82 && isGameOver) { // r
            isGameOver = false;
        } else if (k == 80 && paused) { // p
            paused = false;
        } else if (k == 80 && !isGameOver) {
            paused = true;
        }
    }

    // Controls jet2
    // MODIFIES: this
    // EFFECTS: changes direction of jet in response to key code
    public void jet1Control(int k) {
        if (k == 65) { //"a"
            jet1.setDx(-1);
            jet1.setDy(0);
            jet1.move();
        } else if (k == 68) { //"d"
            jet1.setDx(1);
            jet1.setDy(0);
            jet1.move();
        }
        if (k == 87) { //"w"
            jet1.setDy(-1);
            jet1.setDx(0);
            jet1.move();
        } else if (k == 83) { //"s"
            jet1.setDy(1);
            jet1.setDx(0);
            jet1.move();
        } else if (k == 32) { //space bar
            addToJet1Projectiles(jet1.getCurrentGun().shootProjectile());
        }
    }

    // Controls jet1
    // MODIFIES: this
    // EFFECTS: changes direction of jet in response to key code
    public void jet2Control(int k) {
        if (k == 37) { // left
            jet2.setDx(-1);
            jet2.setDy(0);
            jet2.move();
        } else if (k == 39) { //right
            jet2.setDx(1);
            jet2.setDy(0);
            jet2.move();
        } else if (k == 38) { //up
            jet2.setDy(-1);
            jet2.setDx(0);
            jet2.move();
        } else if (k == 40) { //down
            jet2.setDy(1);
            jet2.setDx(0);
            jet2.move();
        } else if (k == 10) { //enter
            addToJet2Projectiles(jet2.getCurrentGun().shootProjectile());
        }
    }

    // Is game over?
    // modifies: this
    // effects:  if either jet gets 10 points, game is marked as
    //           over and re-initializes game
    public void checkGameOver() {
        if (jet1.getPoints() >= POINTSTOWIN || jet2.getPoints() >= POINTSTOWIN) {
            isGameOver = true;
        }

        if (isGameOver) {
            initializeGameObjects();
        }
    }

    //Modifies: this
    //Effects: Restarts game
    public void initializeGameObjects() {
        gameObjects.clear();
        jet1Projectiles.clear();
        jet2Projectiles.clear();
        jet1 = new Jet(ScreenSizeX / 3, ScreenSizeY / 2,1,0);
        jet2 = new Jet(2 * ScreenSizeX / 3, ScreenSizeY / 2,-1,0);
        gameObjects.add(jet1);
        gameObjects.add(jet2);
    }

    //Effects: converts a game to a json Object and returns it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("jet1", jet1.toJson());
        json.put("jet2", jet2.toJson());
        json.put("jet1 projectiles", projectilesToJson(jet1Projectiles));
        json.put("jet2 projectiles", projectilesToJson(jet2Projectiles));
        json.put("asteroids", asteroidsToJson(asteroids));
        return json;
    }

    //Effects: converts a list of projectiles into a Json Array
    private JSONArray asteroidsToJson(List<Asteroid> asteroids) {
        JSONArray jsonArray = new JSONArray();

        for (Asteroid asteroid: asteroids) {
            jsonArray.put(asteroid.toJson());
        }
        return jsonArray;
    }

    //Effects: converts a list of projectiles into a Json Array
    public JSONArray projectilesToJson(List<Projectile> projectiles) {
        JSONArray jsonArray = new JSONArray();

        for (Projectile projectile: projectiles) {
            jsonArray.put(projectile.toJson());
        }
        return jsonArray;
    }

}
