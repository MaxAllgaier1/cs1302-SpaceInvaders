package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import java.util.*;

/**
 * A alien class.
 */
public class Alien extends ImageView {
    private Game thegame; // game containing this sprite
    private double dx; // change in x per update
    private double dy; // change in y per update
    public double x;
    public double y;
    public double rand;
    public ArrayList<Missile> almisses;
    public boolean dead;
    /**
     * Construct an {@code IdleCat} object.
     * @param game parent game
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

    public void setDead() {
        setY(1000);
        setX(400);
        dead = true;
    }

    /**
     * Update the position of the alien.
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
