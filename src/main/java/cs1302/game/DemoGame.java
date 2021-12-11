package cs1302.game;

import java.util.Random;
import java.util.logging.Level;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * An example of a simple game in JavaFX. The play can move the rectangle left/right
 * with the arrow keys or teleport the rectangle by clicking it!
 */
public class DemoGame extends Game {

    private Random rng;       // random number generator
    private SpaceShip player; // some rectangle to represent the player
    private Alien alien;      // the not so idle cat (see IdleCat.java)
    private Invaders invaders;
    public ImageView backg;

    /**
     * Construct a {@code DemoGame} object.
     * @param width scene width
     * @param height scene height
     */
    public DemoGame(int width, int height) {
        super(width, height, 30);            // call parent constructor. change to 60 later
        setLogLevel(Level.INFO);             // enable logging
        this.rng = new Random();             // random number generator
        this.player = new SpaceShip(this, 320, 550); // some rectangle to represent the player
        this.invaders = new Invaders(this);
        Image background = new Image("file:resources/sprites/Background.png");
        backg = new ImageView(background);
        backg.setPreserveRatio(false);
        backg.setFitWidth(650);
        backg.setFitHeight(750);
    } // DemoGame

    /** {@inheritDoc} */
    @Override
    protected void init() {
        // setup subgraph for this component
        setInvaders();
        getChildren().addAll(backg, player);         // add to main container
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                getChildren().add(invaders.getAlien(i, j));
            }
        }

// setup player
        player.setX(320);      // 50px in the x direction (right)
        player.setY(600);      // 50ps in the y direction (down)
        player.setOnMouseClicked(event -> handleClickPlayer(event));
    } // init

    /** {@inheritDoc} */
    @Override
    protected void update() {

        // (x, y)         In computer graphics, coordinates along an x-axis and
        // (0, 0) -x--->  y-axis are used. When compared to the standard
        // |              Cartesian plane that most students are familiar with,
        // y              the x-axis behaves the same, but the y-axis increases
        // |              in the downward direction! Keep this in mind when
        // v              adjusting the x and y positions of child nodes.

        // update player position
        isKeyPressed(KeyCode.LEFT, () -> player.setX(player.getX() - 10.0));
        isKeyPressed(KeyCode.RIGHT, () -> player.setX(player.getX() + 10.0));

        invaders.update();

    } // update

    /**
     * Move the player rectangle to a random position.
     * @param event associated mouse event
     */
    private void handleClickPlayer(MouseEvent event) {
        logger.info(event.toString());
        player.setX(rng.nextDouble() * (getWidth() - 40));
        player.setY(rng.nextDouble() * (getHeight() - 40));
    } // handleClickPlayer

    public void setInvaders() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                Alien newalien = new Alien(this, j * 50.0, i * 45.0);
                this.invaders.setInvads(i, j, newalien);
            }
        }
    }
} // DemoGame
