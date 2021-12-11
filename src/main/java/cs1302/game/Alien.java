package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

/**
 * A simple "sprite" of an idle cat.
 */
public class Alien extends ImageView {

    private Game game; // game containing this sprite
    private double dx; // change in x per update
    private double dy; // change in y per update
    public double x;
    public double y;

    /**
     * Construct an {@code IdleCat} object.
     * @param game parent game
     */
    public Alien(Game game, double x, double y) {
        super("file:resources/sprites/alienSprite.png"); // call parent constructor
        this.setPreserveRatio(true);
        this.setFitWidth(35);
        this.game = game;
        this.dx = 1.5; // each update, add 2 to x (to start)
        this.dy = 0; // each update, add 0 to y (to start)
        setX(x);
        setY(y);
    } // IdleCat

    /**
     * Update the position of the cat.
     */
    public void update(boolean godown) {
        dy = 0;
        if (godown) {
            dy = 30;
        }
        if (godown) {
            dx *= -1.0;      // change x direction
        }
        setX(getX() + dx);   // move this cat!
        setY(getY() + dy);
    } // update
}
