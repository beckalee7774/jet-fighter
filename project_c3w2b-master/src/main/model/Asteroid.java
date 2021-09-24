package model;

import org.json.JSONObject;

import java.awt.*;

// Represents an Asteroid
// future implementation will make sure that
// if a Jet is hit by an Asteroid it will lose points
public class Asteroid extends GameObject {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    //Effects: Constructs an asteroid
    public Asteroid(int x, int y, int dx, int dy) {
        super(x,y,dx,dy);
    }

    // Determines if this Asteroid has collided with a jet
    // EFFECTS:  returns true if this Asteroid has collided with Jet j,
    //           false otherwise
    public boolean hasHitJet(Jet jet) {
        return super.hasHitJet(jet, WIDTH, HEIGHT);
    }

    //Effects: converts an Asteroid to a json object and then returns it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", objectX);
        json.put("y", objectY);
        json.put("dx", objectDx);
        json.put("dy", objectDy);
        return json;
    }
}
