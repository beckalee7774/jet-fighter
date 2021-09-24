package ui;

import model.JetFighterGame;

import javax.swing.*;
import java.awt.*;

//Represents a Score Panel
public class ScorePanel extends JPanel {
    public JetFighterGame game;
    private static final String JET1_TXT = "Jet 1 score: ";
    private static final String JET2_TXT = "Jet 2 score: ";
    private static final int WIDTH = 200;
    private static final int HEIGHT = 30;
    private JLabel jet1Label;
    private JLabel jet2Label;
    private JLabel pauseInstructionsLabel;

    // Constructs a score panel
    // effects: sets the background colour and draws the initial labels;
    //          updates this with the game whose scores are to be displayed
    public ScorePanel(JetFighterGame game) {
        this.game = game;
        setBackground(new Color(17, 47, 232, 255));
        jet1Label = new JLabel(JET1_TXT + game.getJet1().getPoints());
        jet2Label = new JLabel(JET2_TXT + game.getJet2().getPoints());
        pauseInstructionsLabel = new JLabel("Press P to pause game");
        jet1Label.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        jet2Label.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(jet1Label);
        add(Box.createHorizontalStrut(10));
        add(jet2Label);
        add(pauseInstructionsLabel);
    }

    // Updates the score panel
    // modifies: this
    // effects:  updates number of score that jet1 and jet2 have
    //           to reflect current state of game
    public void update() {
        jet1Label.setText(JET1_TXT + game.getJet1().getPoints());
        jet2Label.setText(JET2_TXT + game.getJet2().getPoints());
        repaint();
    }
}
