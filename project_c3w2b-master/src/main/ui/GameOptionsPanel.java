package ui;

import model.*;
import model.exceptions.InvalidGunIndexException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//Represents a Game Options Panel
public class GameOptionsPanel extends JPanel {
    private JButton jet1GunButton;
    private JButton jet2GunButton;
    private JLabel speedLabel;
    private JLabel pointsWhenHitLabel;
    private JButton chooseJet1CurrentGun;
    private JButton chooseJet2CurrentGun;
    private JetFighterGame game;
    private int speed;
    private int pointsWhenHit;
    private JButton speed1Button;
    private JButton speed2Button;
    private JButton speed5Button;
    private JButton speed10Button;
    private JButton speed15Button;
    private JButton pointsWhenHit1Button;
    private JButton pointsWhenHit2Button;
    private JButton pointsWhenHit3Button;
    private JButton pointsWhenHit4Button;
    private JButton pointsWhenHit5Button;
    private JButton currentGun1Button;
    private JButton currentGun2Button;
    private JButton currentGun3Button;
    private JButton currentGun4Button;
    private static final String JSON_STORE = "./data/game.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JButton saveButton;
    private JButton loadButton;
    private int currentJet1GunAmount = 1;
    private int currentJet2GunAmount = 1;
    private static final int MAXGUNAMOUNT = JetFighterGame.MAXGUNAMOUNT;
    private int currentGunIndex = 0;


    //Effects: constructs a Game Options Panel
    public GameOptionsPanel(JetFighterGame game) {
        this.game = game;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        speed = 1;
        pointsWhenHit = 1;
        createJet1GunButton();
        createJet2GunButton();
        speedLabel = new JLabel("Speed");
        createSpeedButtons();
        pointsWhenHitLabel = new JLabel("PointsWhenHit");
        createPointWhenHitButtons();
        createSaveButton();
        createLoadButton();
        createCurrentGunButtons();
        setFocusableFalseToAllButtons();
        addJetGunButtons();
        add(speedLabel);
        addSpeedButtons();
        add(pointsWhenHitLabel);
        addPointsWhenHitButtons();
        addPersistenceButtons();
        addCurrentGunButtons();
    }

    //Modifies: this
    //Effects: Creates buttons which determine current gun
    private void createCurrentGunButtons() {
        chooseJet1CurrentGun = new JButton("Choose Jet 1 Current Gun");
        initializeChooseJetCurrentGun(chooseJet1CurrentGun, game.getJet1());
        chooseJet2CurrentGun = new JButton("Choose Jet 2 Current Gun");
        initializeChooseJetCurrentGun(chooseJet2CurrentGun, game.getJet2());
        currentGun1Button = new JButton("1");
        initializeCurrentGunButton(currentGun1Button, 1);
        currentGun2Button = new JButton("2");
        initializeCurrentGunButton(currentGun2Button, 2);
        currentGun3Button = new JButton("3");
        initializeCurrentGunButton(currentGun3Button, 3);
        currentGun4Button = new JButton("4");
        initializeCurrentGunButton(currentGun4Button, 4);
    }

    //Modifies: this
    //Effects: Creates buttons which determine pointsWhenHit
    private void createPointWhenHitButtons() {
        pointsWhenHit1Button = new JButton("1");
        initializePointsButton(pointsWhenHit1Button, 1);
        pointsWhenHit2Button = new JButton("2");
        initializePointsButton(pointsWhenHit2Button, 2);
        pointsWhenHit3Button = new JButton("3");
        initializePointsButton(pointsWhenHit3Button, 3);
        pointsWhenHit4Button = new JButton("4");
        initializePointsButton(pointsWhenHit4Button, 4);
        pointsWhenHit5Button = new JButton("5");
        initializePointsButton(pointsWhenHit5Button, 5);
    }

    //Modifies: this
    //Effects: Creates buttons which determine speed
    private void createSpeedButtons() {
        speed1Button = new JButton("1");
        initializeSpeedButton(speed1Button, 1);
        speed2Button = new JButton("2");
        initializeSpeedButton(speed2Button, 2);
        speed5Button = new JButton("5");
        initializeSpeedButton(speed5Button, 5);
        speed10Button = new JButton("10");
        initializeSpeedButton(speed10Button, 10);
        speed15Button = new JButton("15");
        initializeSpeedButton(speed15Button, 15);
    }

    //Modifies: this
    //Effects: Creates load button
    private void createLoadButton() {
        loadButton = new JButton("load from file");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });
    }

    //Modifies: this
    //Effects: Creates save button
    private void createSaveButton() {
        saveButton = new JButton("save to file");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });
    }


    //Modifies: this
    //Effects: Creates buttons which tells us to add a new gun to jet2
    private void createJet2GunButton() {
        jet2GunButton = new JButton("Add to jet2");
        jet2GunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentJet2GunAmount < MAXGUNAMOUNT) {
                    new Gun(game.getJet2(), speed, pointsWhenHit);
                    currentJet2GunAmount++;
                }
            }
        });
    }

    //Modifies: this
    //Effects: Creates buttons which tells us to add a new gun to jet1
    private void createJet1GunButton() {
        jet1GunButton = new JButton("Add to jet1");
        jet1GunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentJet1GunAmount < MAXGUNAMOUNT) {
                    new Gun(game.getJet1(), speed, pointsWhenHit);
                    currentJet1GunAmount++;
                }
            }
        });
    }

    //Modifies: this
    //Effects: Initializes Speed buttons so that when it is pressed speed = i
    private void initializeSpeedButton(JButton speedButton, int i) {
        speedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed = i;
            }
        });
    }

    //Modifies: this
    //Effects: Initializes choose jet current gun buttons so that when it is pressed
    // the current gun of that jet is chosen
    private void initializeChooseJetCurrentGun(JButton chooseJetCurrentGun, Jet jet) {
        chooseJetCurrentGun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jet.chooseGun(currentGunIndex - 1);
                } catch (InvalidGunIndexException ex) {
                    System.out.println("Gun chosen was invalid");
                }
            }
        });
    }

    //Modifies: this
    //Effects: Initializes current gun buttons buttons so that when it is currentGunIndex = i
    private void initializeCurrentGunButton(JButton currentGunButton, int i) {
        currentGunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGunIndex = i;
            }
        });
    }

    //Modifies: this
    //Effects: Initializes pointsWhenHit buttons buttons so that when it is pointsWhenHit = i
    private void initializePointsButton(JButton pointsWhenHitButton, int i) {
        pointsWhenHitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pointsWhenHit = i;
            }
        });
    }

    //Modifies: this
    //Effects: adds jet buttons to this panel
    private void addJetGunButtons() {
        add(jet1GunButton);
        add(jet2GunButton);
    }

    //Modifies: this
    //Effects: adds save and load buttons to this panel
    private void addPersistenceButtons() {
        add(saveButton);
        add(loadButton);
    }

    //Modifies: this
    //Effects: adds speed buttons to this panel
    private void addSpeedButtons() {
        add(speed1Button);
        add(speed2Button);
        add(speed5Button);
        add(speed10Button);
        add(speed15Button);
    }

    //Modifies: this
    //Effects: adds currentGun buttons to this panel
    private void addCurrentGunButtons() {
        add(chooseJet1CurrentGun);
        add(chooseJet2CurrentGun);
        add(currentGun1Button);
        add(currentGun2Button);
        add(currentGun3Button);
        add(currentGun4Button);
    }

    //Modifies: this
    //Effects: adds pointsWhenHit buttons to this panel
    private void addPointsWhenHitButtons() {
        add(pointsWhenHit1Button);
        add(pointsWhenHit2Button);
        add(pointsWhenHit3Button);
        add(pointsWhenHit4Button);
        add(pointsWhenHit5Button);
    }

    //Effects: sets focusable to false for all buttons
    public void setFocusableFalseToAllButtons() {
        jet1GunButton.setFocusable(false);
        jet2GunButton.setFocusable(false);
        saveButton.setFocusable(false);
        loadButton.setFocusable(false);
        speed1Button.setFocusable(false);
        speed2Button.setFocusable(false);
        speed5Button.setFocusable(false);
        speed10Button.setFocusable(false);
        speed15Button.setFocusable(false);
        chooseJet1CurrentGun.setFocusable(false);
        chooseJet2CurrentGun.setFocusable(false);
        currentGun1Button.setFocusable(false);
        currentGun2Button.setFocusable(false);
        currentGun3Button.setFocusable(false);
        currentGun4Button.setFocusable(false);
        pointsWhenHit1Button.setFocusable(false);
        pointsWhenHit2Button.setFocusable(false);
        pointsWhenHit3Button.setFocusable(false);
        pointsWhenHit4Button.setFocusable(false);
        pointsWhenHit5Button.setFocusable(false);
    }

    //Modifies: this
    // EFFECTS: saves game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.writeFile(game);
            jsonWriter.close();
            System.out.println("This game was saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            JetFighterGame gameReadFromFile = jsonReader.read();
            game.getJet1Projectiles().clear();
            game.getJet2Projectiles().clear();
            game.getGameObjects().clear();
            setJets(gameReadFromFile);
            setGuns(gameReadFromFile);
            setProjectiles(gameReadFromFile);
            setAsteroids(gameReadFromFile);
            game.getGameObjects().add(game.getJet1());
            game.getGameObjects().add(game.getJet2());
            System.out.println("Game was loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Modifies: this
    // Effects: sets all the projectiles for the loaded game
    public void setProjectiles(JetFighterGame gameReadFromFile) {
        for (Projectile projectile: gameReadFromFile.getJet1Projectiles()) {
            game.addToJet1Projectiles(projectile);
        }
        for (Projectile projectile: gameReadFromFile.getJet2Projectiles()) {
            game.addToJet2Projectiles(projectile);
        }
    }

    // Modifies: this
    // Effects: sets all the asteroids for the loaded game
    public void setAsteroids(JetFighterGame gameReadFromFile) {
        for (Asteroid asteroid: gameReadFromFile.getAsteroids()) {
            game.addAsteroid(asteroid);
        }
    }


    // Modifies: this
    // Effects: sets the jets position and points for the loaded game
    private void setJets(JetFighterGame gameReadFromFile) {
        game.getJet1().setPoints(gameReadFromFile.getJet1().getPoints());
        game.getJet2().setPoints(gameReadFromFile.getJet2().getPoints());
        game.getJet1().setX(gameReadFromFile.getJet1().getX());
        game.getJet1().setY(gameReadFromFile.getJet1().getY());
        game.getJet1().setDx(gameReadFromFile.getJet1().getDx());
        game.getJet1().setDy(gameReadFromFile.getJet1().getDy());
        game.getJet2().setX(gameReadFromFile.getJet2().getX());
        game.getJet2().setY(gameReadFromFile.getJet2().getY());
        game.getJet2().setDx(gameReadFromFile.getJet2().getDx());
        game.getJet2().setDy(gameReadFromFile.getJet2().getDy());
    }

    // Modifies: this
    // Effects: sets the jets guns for the loaded game
    private void setGuns(JetFighterGame gameReadFromFile) {
        game.getJet1().getGuns().clear();
        game.getJet2().getGuns().clear();
        for (Gun gun: gameReadFromFile.getJet1().getGuns()) {
            new Gun(game.getJet1(), gun.getSpeed(), gun.getPointsWhenHit());
        }
        for (Gun gun: gameReadFromFile.getJet2().getGuns()) {
            new Gun(game.getJet2(), gun.getSpeed(), gun.getPointsWhenHit());
        }
        try {
            game.getJet1().chooseGun(gameReadFromFile.getJet1().getIndexOfCurrentGun());
            game.getJet2().chooseGun(gameReadFromFile.getJet2().getIndexOfCurrentGun());
        } catch (InvalidGunIndexException e) {
            System.out.println("Gun chosen was invalid");
        }
    }

}
