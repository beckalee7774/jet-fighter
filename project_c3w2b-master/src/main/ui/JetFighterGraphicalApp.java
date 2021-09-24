package ui;

import model.Asteroid;
import model.JetFighterGame;
import org.junit.jupiter.api.MethodOrderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

//Represents a Jet Fighter Graphical App
public class JetFighterGraphicalApp extends JFrame {

    private final JetFighterGame game;
    boolean keepGoing;
    private static final int INTERVAL = 20;
    private final GamePanel gamePanel;
    private final ScorePanel scorePanel;
    private final GunsPanel gunsPanel;
    private String soundEffectString;
    private SoundEffect soundEffect;
    private GameOptionsPanel gameOptionsPanel;

    //constructs a Jet Fighter Graphical App
    public JetFighterGraphicalApp() {
        super("Jet Fighter");
        game = new JetFighterGame();
        keepGoing = true;
        soundEffectString = "./data/sound.wav";
        soundEffect = new SoundEffect();
        soundEffect.setFile(soundEffectString);
        gamePanel = new GamePanel(game);
        scorePanel = new ScorePanel(game);
        gunsPanel = new GunsPanel(game);
        gameOptionsPanel = new GameOptionsPanel(game);
        gameOptionsPanel.setLayout(new GridLayout(23,1));
        add(gamePanel);
        add(scorePanel, BorderLayout.NORTH);
        add(gunsPanel, BorderLayout.SOUTH);
        add(gameOptionsPanel, BorderLayout.WEST);
        addKeyListener(new KeyHandler());
        pack();
        setVisible(true);
        addTimer();
        addAsteroidTimer();
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.keyPressed(e.getKeyCode());
            if (e.getKeyCode() == 10 || e.getKeyCode() == 32) {
                soundEffect.play();
            }
        }
    }

    // Set up timer
    // MODIFIES: none
    // EFFECTS: initializes a timer that updates game each
    // INTERVAL milliseconds
    private void addTimer() {
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.update();
                gamePanel.repaint();
                scorePanel.update();
                gunsPanel.update();
            }
        });
        timer.start();
    }

    // Set up timer for asteroids
    // MODIFIES: none
    // EFFECTS: initializes a timer that updates game each
    // 100 milliseconds
    private void addAsteroidTimer() {
        Timer timer2 = new Timer(2000, ae -> {
            Random random = new Random();
            int positionX1 = random.nextInt(JetFighterGame.ScreenSizeX);
            int positionY1 = random.nextInt(2);
            int randomDx = random.nextInt(3);
            if (positionY1 == 1) {
                game.addAsteroid(new Asteroid(positionX1,Asteroid.HEIGHT / 2,randomDx - 2,1));
            } else {
                game.addAsteroid(new Asteroid(positionX1,
                        JetFighterGame.ScreenSizeY - Asteroid.HEIGHT / 2, randomDx - 2, -1));
            }

            int positionX2 = random.nextInt(2);
            int positionY2 = random.nextInt(JetFighterGame.ScreenSizeY);
            int randomDy = random.nextInt(3);
            if (positionX2 == 1) {
                game.addAsteroid(new Asteroid(Asteroid.WIDTH / 2, positionY2,1,randomDy - 2));
            } else {
                game.addAsteroid(new Asteroid(JetFighterGame.ScreenSizeX - Asteroid.HEIGHT / 2, positionY2,-1,
                        randomDy - 2));
            }
        });
        timer2.start();
    }


}
