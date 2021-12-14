package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

/**
 * Spaceship class. this is the player.
 */
public class SpaceShip extends ImageView {
    private Game game; // game containing this sprite
    public double x;
    public double y;
    public int lives;

    /**
     * Construct an {@code SpaceShip} object.
     * @param game parent game
     * @param x is x coord
     * @param y is y coord
     */
    public SpaceShip(Game game, double x, double y) {
        super("file:resources/sprites/space_ship.png");
        this.setPreserveRatio(true);
        this.setFitWidth(50);
        this.game = game;
        setX(x);
        setY(y);
        lives = 4;
    }

    /**
     * takes life from player.
     */
    public void takeLife() {
        lives -= 1;
    }

    /**
     * returns lives left.
     * @return lives left int
     */
    public int getLives() {
        return lives;
    }
}
