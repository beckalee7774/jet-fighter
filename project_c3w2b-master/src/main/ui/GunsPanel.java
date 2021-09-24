package ui;

import model.Gun;
import model.JetFighterGame;

import javax.swing.*;
import java.awt.*;

//Represents a Gun Panel
public class GunsPanel extends JPanel {
    private JetFighterGame game;
    private JLabel jet1Label;
    private JLabel jet2Label;
    private static final String JET1_TXT = "Jet 1 guns: ";
    private static final String JET2_TXT = "Jet 2 guns: ";

    // Constructs a Guns panel
    // effects: sets the background colour and draws the initial labels;
    //          updates this with the game whose guns are to be displayed
    public GunsPanel(JetFighterGame game) {
        this.game = game;
        setBackground(Color.gray);
        jet1Label = new JLabel(JET1_TXT + game.getJet1().getPoints());
        jet2Label = new JLabel(JET2_TXT + game.getJet2().getPoints());
        add(jet1Label);
        add(Box.createHorizontalStrut(10));
        add(jet2Label);
    }

    // Updates the score panel
    // modifies: this
    // effects:  updates number of score that jet1 and jet2 have
    //           to reflect current state of game
    public void update() {
        Gun currentGun1 = game.getJet1().getCurrentGun();
        Gun currentGun2 = game.getJet2().getCurrentGun();
        jet1Label.setText(JET1_TXT + game.getJet1().gunsToString()
                + "Current Gun: " + "[s:" + currentGun1.getSpeed()
                + " p:" + currentGun1.getPointsWhenHit() + "]");
        jet2Label.setText(JET2_TXT + game.getJet2().gunsToString()
                + "Current Gun: " + "[s:" + currentGun2.getSpeed()
                + " p:" + currentGun2.getPointsWhenHit() + "]");
        repaint();
    }
}
