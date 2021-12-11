package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;


public class SpaceShip extends ImageView {
    private Game game; // game containing this sprite
    public double x;
    public double y;
    /**
     * Construct an {@code IdleCat} object.
     * @param game parent game
     */
    public SpaceShip(Game game, double x, double y) {
        super("file:resources/sprites/space_ship.png");
        this.setPreserveRatio(true);
        this.setFitWidth(50);
        this.game = game;
        setX(x);
        setY(y);
    } // IdleCat
}
