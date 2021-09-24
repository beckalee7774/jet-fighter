package model;

import org.json.JSONObject;

import java.awt.*;

//Represents a Projectile which has been shot from a Gun
public class Projectile extends GameObject {
    public int pointsWhenHit;
    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;

    //Effects: constructs a Projectile
    public Projectile(int x, int y, int dx, int dy, int speed, int pointsWhenHit) {
        super(x,y,dx,dy);
        this.speed = speed;
        this.pointsWhenHit = pointsWhenHit;
    }

    //Getters
    public int getPointsWhenHit() {
        return pointsWhenHit;
    }

    public int getSpeed() {
        return speed;
    }

    // Determines if this projectile has collided with a jet
    // EFFECTS:  returns true if this projectile has collided with Jet j,
    //           false otherwise
    public boolean hasHitJet(Jet j) {
        return super.hasHitJet(j, WIDTH, HEIGHT);
    }
    

    //Effects: converts a Gun to a json object and then returns it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", objectX);
        json.put("y", objectY);
        json.put("dx", objectDx);
        json.put("dy", objectDy);
        json.put("speed", speed);
        json.put("points", pointsWhenHit);
        return json;
    }

}
