package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import java.util.*;

/**
 * A alien class. Constructs one of the invaders.
 */
public class Alien extends ImageView {
    private Game thegame; // game containing this sprite
    private double dx; // change in x per update
    private double dy; // change in y per update
    public double x;
    public double y;
    public double rand;
    public boolean dead;

/**
 * Construct an {@code Alien} object.
 * @param game parent game
 * @param x coordinate of alien on game.
 * @param y coordinate of alien on game.
 */
    public Alien(Game game, double x, double y) {
        super("file:resources/sprites/alienSprite.png"); // call parent constructor
        this.setPreserveRatio(true);
        this.setFitWidth(38);
        this.thegame = game;
        this.dx = 2.5; // each update, add 2 to x (to start)
        this.dy = 0; // each update, add 0 to y (to start)
        setX(x);
        setY(y);
        dead = false;
    }

    /**
     * sets the aliens dead boolean to true and moves the alien off the screen.
     */
    public void setDead() {
        setY(1000);
        setX(400);
        dead = true;
    }

    /**
     * Update the position of the alien.
     * @param godown is a boolean to tell the alien to go down.
     * @param speed is the speed added to the alien. It increments in this game as
     * aliens die so that the aliens are faster when there are less aliens left.
     */
    public void update(boolean godown, double speed) {
        if (!dead) {
            if (dx < 0) {
                dx = -1.0 * speed;
            } else {
                dx = 1.0 * speed;
            }
            dy = 0;
            if (godown) {
                dy = 28 + Math.pow(speed, 2);
            }
            if (godown) {
                dx *= -1.0;      // change x direction
            }
            setX(getX() + dx);
            setY(getY() + dy);
        }
    }

} // update
