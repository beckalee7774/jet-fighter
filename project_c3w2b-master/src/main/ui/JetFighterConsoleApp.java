package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Jet;
import model.Gun;
import model.JetFighterGame;
import model.exceptions.InvalidGunIndexException;
import persistence.JsonReader;
import persistence.JsonWriter;

// Represents a Jet Fighter Console App
public class JetFighterConsoleApp {

    private Scanner input;
    private static final String JSON_STORE = "./data/game.json";
    private JetFighterGame game;
    private Jet jet2;
    private Jet jet1;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    boolean keepGoing;

    //Represents a JetFighter Console App
    public JetFighterConsoleApp() {
        game = new JetFighterGame();
        jet1 = game.getJet1();
        jet2 = game.getJet2();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        keepGoing = true;
        runJetFighter();
    }

    //Effects: runs the console app
    public void runJetFighter() {
        String firstCommand;
        displayIntro();
        while (keepGoing) {
            displayFirstOptions();
            firstCommand = input.next();
            switch (firstCommand) {
                case "s":
                    saveGame();
                    break;
                case "l":
                    loadGame();
                    break;
                case "q":
                    keepGoing = false;
                    break;
                case "j":
                    processJetCommand();
                    break;
                default:
                    System.out.println("please enter a valid command");
                    break;
            }
        }
    }

    //Effects: processes the commands when the user chooses to edit a jet
    public void processJetCommand() {
        System.out.println("which jet do you want to access or edit: 1 or 2");
        Jet currentJet = choseJet();
        displaySecondOptions();
        String command = input.next();
        processCommand(command, currentJet);
    }

    //Effects: lets the user choose which jet to edit
    public Jet choseJet() {
        Jet currentJet;
        List<Integer> validNums = new ArrayList<>();
        validNums.add(1);
        validNums.add(2);
        int chosenJet = ensureInt("jet");
        while (!validNums.contains(chosenJet)) {
            System.out.println("please enter a valid jet");
            chosenJet = ensureInt("jet");
        }

        if (chosenJet == 1) {
            currentJet = jet1;
        } else {
            currentJet = jet2;
        }
        return currentJet;
    }

    //Effects: processes the commands to edit a jet
    public void processCommand(String command, Jet currentJet) {
        switch (command) {
            case "s":
                currentJet.getCurrentGun().shootProjectile();
                System.out.println("a projectile was shot");
                break;
            case "g":
                gunInput(currentJet);
                break;
            case "cg":
                chooseGunInput(currentJet);
                break;
            case "p":
                System.out.println("you have " + currentJet.getPoints() + " points");
                break;
            default:
                System.out.println("please enter a valid command");
                break;
        }
    }

    //Effects: Lets the user choose the currentGun
    public void chooseGunInput(Jet currentJet) {
        String str = "number of gun";
        List<Integer> validNums = new ArrayList<>();
        for (int i = 0; i < currentJet.getGuns().size(); i++) {
            validNums.add(i);
        }

        System.out.println("enter the index of the gun you want to use");
        System.out.println("0 is default, 1 is the first gun you made etc");
        int num = ensureInt(str);
        while (!validNums.contains(num)) {
            System.out.println("please enter a valid " + str);
            num = ensureInt(str);
        }
        try {
            currentJet.chooseGun(num);
        } catch (InvalidGunIndexException e) {
            System.out.println("gun chosen was not valid");
        }

        System.out.println("a gun was chosen");
    }

    //Lets the user add a new gun to the chosen jet
    public void gunInput(Jet currentJet) {
        System.out.println("enter speed of gun");
        int speed = ensureInt("speed");
        System.out.println("enter amount of points you get if you hit someone with this gun");
        int pointsWhenHit = ensureInt("points when hit");
        new Gun(currentJet, speed, pointsWhenHit);

        System.out.println("a gun was made");
    }

    // ensures that the next input is an int
    public int ensureInt(String s) {
        boolean valid = false;
        int num = 0;
        while (!valid) {
            if (!input.hasNextInt()) {
                System.out.println("please enter a valid " + s);
                valid = input.hasNextInt();
                String invalid = input.next();
            } else {
                num = input.nextInt();
                valid = true;
            }
        }
        return num;
    }


    //Effects: Displays second options
    public void displaySecondOptions() {
        System.out.println("shoot a projectile --> s");
        System.out.println("add a gun to --> g");
        System.out.println("choose a gun --> cg");
        System.out.println("get points --> p");
    }

    //Effects: Displays intro
    public void displayIntro() {
        System.out.println("Jet Fighter!!");
        System.out.println("====================================================");
        System.out.println("there are two jets: jet1 and jet2");
        System.out.println("each jet has a default gun of speed = 1 and pointsWhenHit = 1");
    }

    //Effects: displays first options
    public void displayFirstOptions() {
        System.out.println("edit or access a jet --> j");
        System.out.println("save game to file --> s");
        System.out.println("load game from file --> l");
        System.out.println("quit --> q");
    }

    // MODIFIES: this
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
            game = jsonReader.read();
            System.out.println("Game was loaded" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
