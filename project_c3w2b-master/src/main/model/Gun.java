package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Represents a Gun which is always connected to a jet
public class Gun {
    public Jet jet;
    public int speed;
    public int pointsWhenHit;

    //Effects: constructs a Gun
    public Gun(Jet jet, int speed, int pointsWhenHit) {
        this.jet = jet;
        this.speed = speed;
        this.pointsWhenHit = pointsWhenHit;
        jet.addGun(this);
    }

    //Getters
    public int getSpeed() {
        return speed;
    }

    public int getPointsWhenHit() {
        return pointsWhenHit;
    }


    //Shoots projectile
    //Effects: makes a new projectile at jet coordinates and with
    // same speed and pointsWhenHit as the gun
    public Projectile shootProjectile() {
        return new Projectile(jet.getX(), jet.getY(),
                jet.getDx(), jet.getDy(), speed, pointsWhenHit);
    }

    //Effects: converts a Gun to a json object and then returns it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gun speed", speed);
        json.put("gun number of points when hit", pointsWhenHit);
        return json;
    }
}
