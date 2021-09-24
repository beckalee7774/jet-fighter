package ui;

import model.*;

import javax.swing.*;
import java.awt.*;

//Represents a Game Panel
public class GamePanel extends JPanel {
    private static final String OVER = "Game Over!";
    private static final String REPLAY = "Press R to replay";
    private JetFighterGame game;

    // Constructs a game panel
    // effects:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(JetFighterGame game) {
        setPreferredSize(new Dimension(JetFighterGame.ScreenSizeX, JetFighterGame.ScreenSizeY));
        setBackground(Color.black);
        this.game = game;
    }

    @Override
    // draws game onto game panel
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (game.isGameOver()) {
            gameOver(g);
        } else if (game.isPaused()) {
            paused(g);
        }
    }

    // Draws the "game over" message and replay instructions
    // modifies: g
    // effects:  draws "game over" and replay instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(236, 18, 62));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, JetFighterGame.ScreenSizeX / 2);
        centreString(REPLAY, g, fm, JetFighterGame.ScreenSizeY / 2 + 50);
        g.setColor(saved);
    }

    // Draws the "paused" message and play instructions
    // modifies: g
    // effects:  draws "paused" and replay instructions onto g
    private void paused(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(236, 18, 62));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Paused", g, fm, JetFighterGame.ScreenSizeX / 2);
        centreString("Press P to Play", g, fm, JetFighterGame.ScreenSizeY / 2 + 50);
        g.setColor(saved);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string str horizontally onto g at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (JetFighterGame.ScreenSizeX - width) / 2, y);
    }

    // Draws the game
    // modifies: g
    // effects:  the game is drawn onto the Graphics object g
    private void drawGame(Graphics g) {
        for (GameObject gameObject: game.getGameObjects()) {
            if (gameObject.getClass() == Jet.class) {
                drawJet(g, gameObject.getX(), gameObject.getY());
            } else if (gameObject.getClass() == Projectile.class) {
                drawProjectile(g, gameObject.getX(), gameObject.getY());
            } else if (gameObject.getClass() == Asteroid.class) {
                drawAsteroid(g, gameObject.getX(), gameObject.getY());
            }
        }
    }

    //Modifies: g
    //Effects: draws jet onto Graphics g
    public void drawJet(Graphics g, int x, int y) {
        Color color = new Color(90, 12, 217);
        Color savedCol = g.getColor();
        g.setColor(color);
        g.fillRect(x - Jet.WIDTH / 2, y - Jet.HEIGHT / 2, Jet.WIDTH, Jet.HEIGHT);
        g.setColor(savedCol);
    }

    //Modifies: g
    //Effects: Draws projectile on Graphics g
    public void drawProjectile(Graphics g, int x, int y) {
        Color color = new Color(7, 219, 11);
        Color savedCol = g.getColor();
        g.setColor(color);
        g.fillOval(x - Projectile.WIDTH / 2, y - Projectile.HEIGHT / 2, Projectile.WIDTH, Projectile.HEIGHT);
        g.setColor(savedCol);
    }

    //Modifies: g
    //Effects: Draws an asteroid
    public void drawAsteroid(Graphics g, int x, int y) {
        Color color = new Color(102, 123, 194);
        Color savedCol = g.getColor();
        g.setColor(color);
        g.fillRect(x - Asteroid.WIDTH / 2, y - Asteroid.HEIGHT / 2, Asteroid.WIDTH, Asteroid.HEIGHT);
        g.setColor(savedCol);
    }
}

