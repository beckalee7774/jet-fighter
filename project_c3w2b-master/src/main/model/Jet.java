package model;

import model.exceptions.InvalidGunIndexException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//Represents a Jet
public class Jet extends GameObject {
    private int points;
    protected List<Gun> guns;
    protected Gun currentGun;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    //Effects: constructs a Jet
    public Jet(int x, int y, int dx, int dy) {
        super(x,y,dx,dy);
        points = 0;
        guns = new ArrayList<>();
        currentGun = new Gun(this,1,1);
        speed = 20;
    }

    //Getters
    public Gun getCurrentGun() {
        return currentGun;
    }

    public int getPoints() {
        return points;
    }

    public List<Gun> getGuns() {
        return guns;
    }

    public int getSpeed() {
        return speed;
    }

    //setters
    public void setPoints(int points) {
        this.points = points;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //Gets index of currentGun
    //Effects: returns index of currentGun in guns
    public int getIndexOfCurrentGun() {
        int index = 0;
        for (int i = 0; i < guns.size(); i++) {
            Gun gun = guns.get(i);
            if (gun.equals(currentGun)) {
                index = i;
            }
        }
        return index;
    }

    //Add Gun to Jet
    //Modifies: this
    //Effects: adds gun to guns
    public void addGun(Gun gun) {
        guns.add(gun);
    }

    //Choose Gun
    //Modifies: this
    //Effects: sets currentGun to the element is guns corresponding to
    // the index given by the parameter i
    public void chooseGun(int i) throws InvalidGunIndexException {
        if (i >= guns.size() || i < 0) {
            throw new InvalidGunIndexException();
        }
        currentGun = guns.get(i);
    }

    //Add point to Jet
    //Modifies: this
    //Effects: increments points by 1
    public void addPoints(int i) {
        points += i;
    }

    //Deducts a point from Jet
    //Modifies: this
    //Effects: deducts 1 from points
    public void deductPoints(int i) {
        points -= i;
    }

    // Constrains Jet so that it doesn't travel off sides of screen
    // MODIFIES: this
    // EFFECTS: Jet is constrained to remain within vertical boundaries of game
    public void keepJetWithinScreen() {
        if (objectX - WIDTH / 2 < 0) {
            objectX = WIDTH / 2;
        } else if (objectX + WIDTH / 2 > JetFighterGame.ScreenSizeX) {
            objectX = JetFighterGame.ScreenSizeX - WIDTH / 2;
        }
        if (objectY < HEIGHT / 2) {
            objectY = HEIGHT / 2;
        } else if (objectY + HEIGHT / 2 > JetFighterGame.ScreenSizeY) {
            objectY = JetFighterGame.ScreenSizeY - HEIGHT / 2;
        }
    }

    // Effects: converts list of guns to a String and returns it
    public String gunsToString() {
        String gunsStr = "";
        for (Gun gun: guns) {
            gunsStr += "[s:" + gun.getSpeed() + " p:" + gun.getPointsWhenHit() + "] ";
        }

        return gunsStr;
    }


    //Effects: converts Jet to a Json object and then returns it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("current gun index", getIndexOfCurrentGun());
        json.put("points", points);
        json.put("guns", gunsToJson());
        json.put("x", objectX);
        json.put("y", objectY);
        json.put("dx", objectDx);
        json.put("dy", objectDy);
        return json;
    }

    //Effects: converts a list of guns to a Json array and then returns it
    public JSONArray gunsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 1; i < guns.size(); i++) {
            Gun gun = guns.get(i);
            jsonArray.put(gun.toJson());
        }

        return jsonArray;
    }
}
