package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


/**
 * A simple "sprite" of an idle cat.
 */
public class Missile extends Rectangle {
    private Game game; // game containing this sprite
    private double dy; // change in y per update
    public double x;
    public double y;
    public boolean ship;

    /**
     * Construct an {@code IdleCat} object.
     * @param game parent game
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
    } // IdleCat

    public void setDead() {
        setX(900);
    }

    /**
     * Update the position of the cat.
     */
    public void update() {
        dy = -10;
        if (!ship) {
            dy = 9;
        }
        setY(getY() + dy);
    } // update

    public boolean goingUp() {
        return ship;
    }
}
