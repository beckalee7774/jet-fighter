package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Represents a GameObject Test
public class GameObjectTest {

    GameObject gameObject;

    @BeforeEach
    public void runBefore() {
        gameObject = new Jet(0,0,4,6);
    }

    @Test
    public void testSetDx() {
        gameObject.setDx(-1);
        assertEquals(-1, gameObject.getDx());
    }

    @Test
    public void testSetDy() {
        gameObject.setDy(-7);
        assertEquals(-7, gameObject.getDy());
    }

}
