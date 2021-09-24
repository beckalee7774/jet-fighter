package model;

import org.json.JSONObject;

import java.awt.*;

//Represents a Game object that has its own
// x,y coordinates and direction
public abstract class GameObject {
    protected int objectX;
    protected int objectY;
    protected int objectDx;
    protected int objectDy;
    protected int speed;

    //Effects: constructs a GameObject
    public GameObject(int x, int y, int dx, int dy) {
        objectX = x;
        objectY = y;
        objectDx = dx;
        objectDy = dy;
        speed = 1;
    }

    //Getters
    public int getX() {
        return objectX;
    }

    public int getY() {
        return objectY;
    }

    public int getDx() {
        return objectDx;
    }

    public int getDy() {
        return objectDy;
    }

    //Setters
    public void setDx(int dx) {
        objectDx = dx;
    }

    public void setDy(int dy) {
        objectDy = dy;
    }

    public void setX(int x) {
        objectX = x;
    }

    public void setY(int y) {
        objectY = y;
    }

    //Moves x and y coordinate of this Object
    //Modifies: this
    //Effects: Adds objectDx * speed to objectX and adds object Dy * speed to objectDy
    public void move() {
        objectX += objectDx * speed;
        objectY += objectDy * speed;
    }

    // Determines if this GameObject has collided with a jet
    // EFFECTS:  returns true if this GameObject has collided with Jet j,
    //           false otherwise
    public boolean hasHitJet(Jet j, int width, int height) {
        Rectangle gameObjectRect = new Rectangle(getX() - width / 2,
                getY() - height / 2, width, height);
        Rectangle jetRect = new Rectangle(j.getX() - Jet.WIDTH / 2, j.getY() - Jet.HEIGHT / 2,
                Jet.WIDTH, Jet.HEIGHT);
        return gameObjectRect.intersects(jetRect);
    }

    //Effects: converts GameObject to a Json object and then returns it
    public abstract JSONObject toJson();
}
