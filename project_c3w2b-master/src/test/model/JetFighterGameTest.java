package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Represents a Jet Fighter Game Test
public class JetFighterGameTest {
    JetFighterGame game;
    Projectile projectile;
    List<Projectile> jet1Projectiles;
    List<Projectile> jet2Projectiles;
    List<GameObject> gameObjects;
    List<Asteroid> asteroids;
    Asteroid asteroid;

    @BeforeEach
    public void runBefore() {
        game = new JetFighterGame();
        projectile = new Projectile(0,0,0,1, 1, 1);
        jet1Projectiles = game.getJet1Projectiles();
        jet2Projectiles = game.getJet2Projectiles();
        gameObjects = game.getGameObjects();
        asteroids = game.getAsteroids();
        asteroid = new Asteroid(100,100,1,1);
    }

    @Test
    public void testAddToJet1Projectiles() {
        game.addToJet1Projectiles(projectile);
        assertTrue(jet1Projectiles.contains(projectile));
        assertTrue(gameObjects.contains(projectile));
    }

    @Test
    public void testAddToJet2Projectiles() {
        game.addToJet2Projectiles(projectile);
        assertTrue(jet2Projectiles.contains(projectile));
        assertTrue(gameObjects.contains(projectile));
    }

    @Test
    public void testAddAsteroid() {
        game.addAsteroid(asteroid);
        assertTrue(asteroids.contains(asteroid));
        assertTrue(gameObjects.contains(asteroid));
    }

    @Test
    public void testRemoveAsteroidAsteroidIsInAsteroids() {
        game.addAsteroid(asteroid);
        game.removeAsteroid(asteroid);
        assertFalse(asteroids.contains(asteroid));
        assertFalse(gameObjects.contains(asteroid));
    }

    @Test
    public void testRemoveAsteroidAsteroidIsNotInAsteroids() {
        game.removeAsteroid(asteroid);
        assertFalse(asteroids.contains(asteroid));
        assertFalse(gameObjects.contains(asteroid));
    }

    @Test
    public void testRemoveProjectileProjectileIsInJet1Projectiles() {
        game.addToJet1Projectiles(projectile);
        game.removeProjectile(projectile);
        assertFalse(jet1Projectiles.contains(projectile));
        assertFalse(gameObjects.contains(projectile));
    }

    @Test
    public void testRemoveProjectileProjectileIsInJet2Projectiles() {
        game.addToJet2Projectiles(projectile);
        game.removeProjectile(projectile);
        assertFalse(jet2Projectiles.contains(projectile));
        assertFalse(gameObjects.contains(projectile));
    }

    @Test
    public void testRemoveProjectileProjectileIsNotInGame() {
        game.removeProjectile(projectile);
        assertFalse(jet2Projectiles.contains(projectile));
        assertFalse(gameObjects.contains(projectile));
    }


    @Test
    public void testUpdateGameIsNotPaused() {
        Projectile projectile2 = new Projectile( -50,10,1,0, 2, 2);
        Projectile projectile3 = new Projectile( 50,10,1,0, 2, 2);
        Projectile projectile4 = new Projectile( 20,20,1,0, 2, 2);
        Asteroid asteroid2 = new Asteroid(-50,-50,1,1);
        game.addAsteroid(asteroid);
        game.addAsteroid(asteroid2);
        game.addToJet1Projectiles(projectile2);
        game.addToJet1Projectiles(projectile3);
        game.addToJet2Projectiles(projectile4);
        game.getJet2().setX(50);
        game.getJet2().setY(10);
        game.getJet1().setX(-100);
        game.update();
        assertFalse(jet1Projectiles.contains(projectile2));
        assertFalse(gameObjects.contains(projectile2));
        assertFalse(jet1Projectiles.contains(projectile3));
        assertFalse(gameObjects.contains(projectile3));
        assertFalse(asteroids.contains(asteroid2));
        assertFalse(gameObjects.contains(asteroid2));
        assertEquals(Jet.WIDTH / 2, game.getJet1().getX());
        assertEquals(22, projectile4.getX());
        assertEquals(101,asteroid.getX());
        assertEquals(101,asteroid.getY());
    }

    @Test
    public void testUpdateGameIsPaused() {
        Projectile projectile2 = new Projectile( -50,10,1,0, 2, 2);
        Projectile projectile3 = new Projectile( 50,10,1,0, 2, 2);
        Projectile projectile4 = new Projectile( 20,20,1,0, 2, 2);
        game.addToJet1Projectiles(projectile2);
        game.addToJet1Projectiles(projectile3);
        game.addToJet2Projectiles(projectile4);
        game.getJet2().setX(50);
        game.getJet2().setY(10);
        game.getJet1().setX(-100);
        game.setPaused();
        game.update();
        assertTrue(jet1Projectiles.contains(projectile2));
        assertTrue(gameObjects.contains(projectile2));
        assertTrue(jet1Projectiles.contains(projectile3));
        assertTrue(gameObjects.contains(projectile3));
        assertEquals(-100, game.getJet1().getX());
        assertEquals(20, projectile4.getX());
    }

    @Test
    public void testMoveProjectiles() {
        Projectile projectile2 = new Projectile( 10,10,-1,0, 2, 2);
        game.addToJet1Projectiles(projectile);
        game.addToJet2Projectiles(projectile2);
        game.moveProjectiles();
        assertEquals(1, projectile.getY());
        assertEquals(0, projectile.getX());
        assertEquals(10, projectile2.getY());
        assertEquals(8, projectile2.getX());
    }

    @Test
    public void testMoveAsteroid() {
        game.addAsteroid(asteroid);
        game.moveAsteroids();
        assertEquals(101,asteroid.getX());
        assertEquals(101,asteroid.getY());
    }

    @Test
    public void testCheckJetGotHitByProjectileJetHasBeenHit() {
        Jet jet1 = game.getJet1();
        Jet jet2 = game.getJet2();
        jet1.setX(20);
        jet1.setY(20);
        Projectile projectile2 = new Projectile(20,20,1,1,1,1);
        game.addToJet2Projectiles(projectile2);
        game.checkJetGotHitByProjectiles(jet1, jet2Projectiles, jet2);
        assertEquals(1, jet2.getPoints());
        assertFalse(jet2Projectiles.contains(projectile2));
        assertFalse(gameObjects.contains(projectile2));
    }

    @Test
    public void testCheckJetGotHitByProjectileJetHasNotBeenHit() {
        Jet jet1 = game.getJet1();
        Jet jet2 = game.getJet2();
        jet1.setX(20);
        jet1.setY(20);
        Projectile projectile2 = new Projectile(50,50,1,1,1,1);
        game.addToJet2Projectiles(projectile2);
        game.checkJetGotHitByProjectiles(jet1, jet2Projectiles, jet2);
        assertEquals(0, jet2.getPoints());
        assertTrue(jet2Projectiles.contains(projectile2));
        assertTrue(gameObjects.contains(projectile2));
    }

    @Test
    public void testCheckJetGotHitByAsteroidJetHasBeenHit() {
        Jet jet1 = game.getJet1();
        jet1.setX(100);
        jet1.setY(100);
        game.addAsteroid(asteroid);
        game.checkJetGotHitByAsteroids(jet1);
        assertEquals(-1,jet1.getPoints());

    }

    @Test
    public void testCheckJetGotHitByAsteroidJetHasNotBeenHit() {
        Jet jet1 = game.getJet1();
        jet1.setX(300);
        jet1.setY(300);
        game.addAsteroid(asteroid);
        game.checkJetGotHitByAsteroids(jet1);
        assertEquals(0,jet1.getPoints());
    }

    @Test
    public void testCheckGameObjectsMovedOffScreenGameObjectsHaveMovedOffInXDirection() {
        Projectile projectile2 = new Projectile(- Projectile.WIDTH / 2 - 1, 0, 0, 0,1,1);
        Projectile projectile3 = new Projectile(JetFighterGame.ScreenSizeX - Projectile.WIDTH / 2 + 1, 0,
                0, 0,1,1);
        Asteroid asteroid = new Asteroid(-50,-50,1,1);

        game.addToJet1Projectiles(projectile2);
        game.addToJet1Projectiles(projectile3);
        game.addAsteroid(asteroid);
        game.checkGameObjectsMovedOffScreen();

        assertFalse(asteroids.contains(asteroid));
        assertFalse(jet1Projectiles.contains(projectile2));
        assertFalse(jet1Projectiles.contains(projectile3));

        assertFalse(gameObjects.contains(asteroid));
        assertFalse(gameObjects.contains(projectile2));
        assertFalse(gameObjects.contains(projectile3));
    }

    @Test
    public void testCheckGameObjectsMovedOffScreenGameObjectsHaveNotMovedOffInXDirection() {
        Projectile projectile2 = new Projectile(- Projectile.WIDTH / 2, 0,
                0, 0,1,1);
        Projectile projectile3 = new Projectile(JetFighterGame.ScreenSizeX - Projectile.WIDTH / 2, 0,
                0, 0,1,1);

        game.addToJet1Projectiles(projectile);
        game.addToJet1Projectiles(projectile2);
        game.addToJet1Projectiles(projectile3);
        game.checkGameObjectsMovedOffScreen();

        assertTrue(jet1Projectiles.contains(projectile));
        assertTrue(jet1Projectiles.contains(projectile2));
        assertTrue(jet1Projectiles.contains(projectile3));

        assertTrue(gameObjects.contains(projectile));
        assertTrue(gameObjects.contains(projectile2));
        assertTrue(gameObjects.contains(projectile3));
    }

    @Test
    public void testCheckGameObjectsMovedOffScreenGameObjectsHaveMovedOffInYDirection() {
        Projectile projectile2 = new Projectile(0, - Projectile.HEIGHT / 2 - 1, 0, 0,1,1);
        Projectile projectile3 = new Projectile(0, JetFighterGame.ScreenSizeY - Projectile.HEIGHT / 2 + 1,
                0, 0,1,1);

        game.addToJet2Projectiles(projectile2);
        game.addToJet2Projectiles(projectile3);
        game.checkGameObjectsMovedOffScreen();

        assertFalse(jet2Projectiles.contains(projectile2));
        assertFalse(jet2Projectiles.contains(projectile3));


        assertFalse(gameObjects.contains(projectile2));
        assertFalse(gameObjects.contains(projectile3));
    }

    @Test
    public void testCheckGameObjectsMovedOffScreenGameObjectsHaveNotMovedOffInYDirection() {
        Projectile projectile2 = new Projectile(0, - Projectile.HEIGHT / 2,
                0, 0,1,1);
        Projectile projectile3 = new Projectile(0, JetFighterGame.ScreenSizeY - Projectile.HEIGHT / 2,
                0, 0,1,1);

        game.addToJet2Projectiles(projectile2);
        game.addToJet2Projectiles(projectile3);
        game.checkGameObjectsMovedOffScreen();

        assertTrue(jet2Projectiles.contains(projectile2));
        assertTrue(jet2Projectiles.contains(projectile3));


        assertTrue(gameObjects.contains(projectile2));
        assertTrue(gameObjects.contains(projectile3));
    }

    @Test
    public void testRemoveProjectilesOneProjectileNotInGame() {
        Projectile projectile2 = new Projectile(0,0,0,0,0,0);
        Projectile projectile3 = new Projectile(0,0,0,0,0,0);
        Projectile projectile4 = new Projectile(0,0,0,0,0,0);
        Projectile projectile5 = new Projectile(0,0,0,0,0,0);
        Projectile projectile6 = new Projectile(0,0,0,0,0,0);
        List<Projectile> projectilesList = new ArrayList<>();
        projectilesList.add(projectile2);
        projectilesList.add(projectile3);
        projectilesList.add(projectile4);
        projectilesList.add(projectile5);
        game.addToJet1Projectiles(projectile2);
        game.addToJet1Projectiles(projectile4);
        game.addToJet1Projectiles(projectile5);
        game.addToJet1Projectiles(projectile6);
        game.removeProjectiles(projectilesList);

        assertFalse(game.getJet1Projectiles().contains(projectile2));
        assertFalse(game.getJet1Projectiles().contains(projectile3));
        assertFalse(game.getJet1Projectiles().contains(projectile4));
        assertFalse(game.getJet1Projectiles().contains(projectile5));
        assertTrue(game.getJet1Projectiles().contains(projectile6));
    }

    @Test
    public void testRemoveProjectilesAllProjectilesInGame() {
        Projectile projectile2 = new Projectile(0,0,0,0,0,0);
        Projectile projectile3 = new Projectile(0,0,0,0,0,0);
        Projectile projectile4 = new Projectile(0,0,0,0,0,0);
        Projectile projectile5 = new Projectile(0,0,0,0,0,0);
        List<Projectile> projectilesList = new ArrayList<>();
        projectilesList.add(projectile2);
        projectilesList.add(projectile4);
        projectilesList.add(projectile5);
        game.addToJet1Projectiles(projectile2);
        game.addToJet1Projectiles(projectile3);
        game.addToJet1Projectiles(projectile4);
        game.addToJet1Projectiles(projectile5);
        game.removeProjectiles(projectilesList);

        assertTrue(game.getJet1Projectiles().contains(projectile3));
        assertFalse(game.getJet1Projectiles().contains(projectile2));
        assertFalse(game.getJet1Projectiles().contains(projectile4));
        assertFalse(game.getJet1Projectiles().contains(projectile5));
    }

    @Test
    public void testRemoveAsteroids() {
        Asteroid asteroid2 = new Asteroid(20,20,1,1);
        List<Asteroid> asteroidList = new ArrayList<>();
        asteroidList.add(asteroid);
        game.addAsteroid(asteroid2);
        game.addAsteroid(asteroid);
        game.removeAsteroids(asteroidList);

        assertTrue(asteroids.contains(asteroid2));
        assertTrue(game.getGameObjects().contains(asteroid2));
        assertFalse(asteroids.contains(asteroid));
        assertFalse(game.getGameObjects().contains(asteroid));
    }

    @Test
    public void testKeyPressedGameIsNotPausedOrOver() {
        Jet jet1 = game.getJet1();
        jet1.setX(40);
        jet1.setSpeed(10);
        Jet jet2 = game.getJet2();
        jet2.setX(40);
        jet2.setSpeed(10);
        game.keyPressed(65);//a
        game.keyPressed(37);//left
        assertEquals(30, jet1.getX());
        assertEquals(30, jet2.getX());
    }

    @Test
    public void testKeyPressedGameIsPaused() {
        game.keyPressed(80);//p
        assertTrue(game.isPaused());
        game.keyPressed(80);//p
        assertFalse(game.isPaused());
    }

    @Test
    public void testKeyPressedGameIsOver() {
        game.setGameOver();
        game.keyPressed(80);//p
        assertFalse(game.isPaused());
        assertTrue(game.isGameOver());
        game.keyPressed(82);//r
        assertFalse(game.isGameOver());
    }

    @Test
    public void testKeyPressedRandomKey() {
        game.keyPressed(2);//p
        assertFalse(game.isPaused());
        assertEquals(0, jet1Projectiles.size());
        assertEquals(0, jet2Projectiles.size());
    }

    @Test
    public void testKeyPressedPressedRWhenGameIsNotOver() {
        assertFalse(game.isGameOver());
        game.keyPressed(82);//p
        assertFalse(game.isPaused());
        assertFalse(game.isGameOver());
        assertEquals(0, jet1Projectiles.size());
        assertEquals(0, jet2Projectiles.size());
    }

    @Test
    public void testJet1ControlMoveLeft() {
        Jet jet1 = game.getJet1();
        jet1.setX(50);
        jet1.setY(50);
        jet1.setSpeed(10);
        game.jet1Control(65);//a
        assertEquals(40, jet1.getX());
        assertEquals(50, jet1.getY());
    }

    @Test
    public void testJet1ControlMoveRight() {
        Jet jet1 = game.getJet1();
        jet1.setX(50);
        jet1.setY(50);
        jet1.setSpeed(10);
        game.jet1Control(68);//d
        assertEquals(60, jet1.getX());
        assertEquals(50, jet1.getY());
    }

    @Test
    public void testJet1ControlMoveUp() {
        Jet jet1 = game.getJet1();
        jet1.setX(50);
        jet1.setY(50);
        jet1.setSpeed(10);
        game.jet1Control(87);//w
        assertEquals(50, jet1.getX());
        assertEquals(40, jet1.getY());
    }

    @Test
    public void testJet1ControlMoveDown() {
        Jet jet1 = game.getJet1();
        jet1.setX(50);
        jet1.setY(50);
        jet1.setSpeed(10);
        game.jet1Control(83);//s
        assertEquals(50, jet1.getX());
        assertEquals(60, jet1.getY());
    }

    @Test
    public void testJet1ControlInvalidInput() {
        Jet jet1 = game.getJet1();
        jet1.setX(50);
        jet1.setY(50);
        jet1.setSpeed(10);
        game.jet1Control(47);//slash
        assertEquals(50, jet1.getX());
        assertEquals(50, jet1.getY());
    }

    @Test
    public void testJet1ControlShootProjectile() {
        assertEquals(0, game.getJet1Projectiles().size());
        game.jet1Control(32);//space
        assertEquals(1, game.getJet1Projectiles().size());
    }

    @Test
    public void testJet2ControlMoveLeft() {
        Jet jet2 = game.getJet2();
        jet2.setX(50);
        jet2.setY(50);
        jet2.setSpeed(10);
        game.jet2Control(37);//left
        assertEquals(40, jet2.getX());
        assertEquals(50, jet2.getY());
    }

    @Test
    public void testJet2ControlMoveRight() {
        Jet jet2 = game.getJet2();
        jet2.setX(50);
        jet2.setY(50);
        jet2.setSpeed(10);
        game.jet2Control(39);//right
        assertEquals(60, jet2.getX());
        assertEquals(50, jet2.getY());
    }

    @Test
    public void testJet2ControlMoveUp() {
        Jet jet2 = game.getJet2();
        jet2.setX(50);
        jet2.setY(50);
        jet2.setSpeed(10);
        game.jet2Control(38);//up
        assertEquals(50, jet2.getX());
        assertEquals(40, jet2.getY());
    }

    @Test
    public void testJet2ControlMoveDown() {
        Jet jet2 = game.getJet2();
        jet2.setX(50);
        jet2.setY(50);
        jet2.setSpeed(10);
        game.jet2Control(40);//down
        assertEquals(50, jet2.getX());
        assertEquals(60, jet2.getY());
    }

    @Test
    public void testJet2ControlInvalidInput() {
        Jet jet2 = game.getJet2();
        jet2.setX(50);
        jet2.setY(50);
        jet2.setSpeed(10);
        game.jet2Control(47);//slash
        assertEquals(50, jet2.getX());
        assertEquals(50, jet2.getY());
    }

    @Test
    public void testJet2ControlShootProjectile() {
        assertEquals(0, game.getJet2Projectiles().size());
        game.jet2Control(10);//enter
        assertEquals(1, game.getJet2Projectiles().size());
    }

    @Test
    public void testCheckGameOverGameIsNotOver() {
        Jet jet1 = game.getJet1();
        Jet jet2 = game.getJet2();
        jet1.setPoints(JetFighterGame.POINTSTOWIN - 1);
        jet2.setPoints(JetFighterGame.POINTSTOWIN - 1);
        game.checkGameOver();
        assertFalse(game.isGameOver());
    }

    @Test
    public void testCheckGameOverGameIsOverDueToJet1Points() {
        Jet jet1 = game.getJet1();
        Jet jet2 = game.getJet2();
        jet1.setPoints(JetFighterGame.POINTSTOWIN);
        jet2.setPoints(JetFighterGame.POINTSTOWIN - 1);
        game.checkGameOver();
        assertTrue(game.isGameOver());
    }

    @Test
    public void testCheckGameOverGameIsOverDueToJet2Points() {
        Jet jet1 = game.getJet1();
        Jet jet2 = game.getJet2();
        jet1.setPoints(JetFighterGame.POINTSTOWIN - 1);
        jet2.setPoints(JetFighterGame.POINTSTOWIN);
        game.checkGameOver();
        assertTrue(game.isGameOver());
    }

    @Test
    public void testCheckGameOverGameIsOver() {
        Jet jet1 = game.getJet1();
        Jet jet2 = game.getJet2();
        jet1.setPoints(JetFighterGame.POINTSTOWIN);
        jet2.setPoints(JetFighterGame.POINTSTOWIN - 1);
        jet1.setX(100);
        jet2.setX(100);
        game.checkGameOver();
        jet1 = game.getJet1();
        jet2 = game.getJet2();
        assertTrue(game.isGameOver());
        assertEquals(2,game.getGameObjects().size());
        assertEquals(0,game.getJet1Projectiles().size());
        assertEquals(0,game.getJet2Projectiles().size());
        assertEquals(JetFighterGame.ScreenSizeX / 3, jet1.getX());
        assertEquals(JetFighterGame.ScreenSizeY / 2, jet1.getY());
        assertEquals(1, jet1.getDx());
        assertEquals(0, jet1.getDy());
        assertEquals(2 * JetFighterGame.ScreenSizeX / 3, jet2.getX());
        assertEquals(JetFighterGame.ScreenSizeY / 2, jet2.getY());
        assertEquals(-1, jet2.getDx());
        assertEquals(0, jet2.getDy());
        assertEquals(game.getGameObjects().get(0), jet1);
        assertEquals(game.getGameObjects().get(1), jet2);
        assertEquals(0, jet1.getPoints());
        assertEquals(0, jet2.getPoints());
        assertEquals(1, jet1.getGuns().size());
        assertEquals(1, jet2.getGuns().size());
    }

    @Test
    public void testInitializeGameObjects() {
        Jet jet1 = game.getJet1();
        Jet jet2 = game.getJet2();
        jet1.setX(100);
        jet2.setX(100);
        jet1.setPoints(5);
        jet2.setPoints(5);
        jet1.setDx(0);
        jet2.setDx(0);
        jet1.setDx(1);
        jet2.setDx(1);
        game.checkGameOver();
        game.initializeGameObjects();
        assertEquals(2,game.getGameObjects().size());
        assertEquals(0,game.getJet1Projectiles().size());
        assertEquals(0,game.getJet2Projectiles().size());
        assertEquals(JetFighterGame.ScreenSizeX / 3, game.getJet1().getX());
        assertEquals(JetFighterGame.ScreenSizeY / 2, game.getJet1().getY());
        assertEquals(1, game.getJet1().getDx());
        assertEquals(0, game.getJet1().getDy());
        assertEquals(2 * JetFighterGame.ScreenSizeX / 3, game.getJet2().getX());
        assertEquals(JetFighterGame.ScreenSizeY / 2, game.getJet2().getY());
        assertEquals(-1, game.getJet2().getDx());
        assertEquals(0, game.getJet2().getDy());
        assertEquals(game.getGameObjects().get(0), game.getJet1());
        assertEquals(game.getGameObjects().get(1), game.getJet2());
        assertEquals(0, game.getJet1().getPoints());
        assertEquals(0, game.getJet2().getPoints());
        assertEquals(1, game.getJet1().getGuns().size());
        assertEquals(1, game.getJet2().getGuns().size());
    }

}
