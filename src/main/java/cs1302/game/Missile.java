package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


/**
 * Missile class for missiles being shot. it is a rectangle
 */
public class Missile extends Rectangle {
    private Game game; // game containing this sprite
    private double dy; // change in y per update
    public double x;
    public double y;
    public boolean ship;

    /**
     * Construct an {@code Missile} object.
     * @param game parent game
     * @param x is the x coord
     * @param y is the y coord
     * @param ship is the boolean telling whether the missile is from the ship or alien
     * @param level is the current level of the game.
     */
    public Missile(Game game, double x, double y, boolean ship, int level) {
        super(x, y, 5, 15);
        this.game = game;
        this.ship = ship;
        this.dy = 0;
        setX(x);
        setY(y);
        this.setFill(Color.WHITE);
        if (!ship) {
            this.setFill(Color.RED);
            if (level == 1) {
                this.setFill(Color.GREEN);
                this.setWidth(10);
                this.setHeight(35);
            }
        }
    }

    /**
     * Kills the missile by making it dissapear to the side.
     */
    public void setDead() {
        setX(900);
    }

    /**
     * Update the position of the missile.
     */
    public void update() {
        dy = -10;
        if (!ship) {
            dy = 9;
        }
        setY(getY() + dy);
    } // update

    /**
     * boolean tellin which direction the missile is going.
     * @return whether missile is going up or not.
     */
    public boolean goingUp() {
        return ship;
    }
}
