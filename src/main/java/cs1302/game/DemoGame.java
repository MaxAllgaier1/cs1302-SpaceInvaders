package cs1302.game;

import javafx.geometry.Bounds;
import java.util.Random;
import java.util.logging.Level;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.lang.Math;
import java.lang.*;

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
    public ArrayList<Missile> missiles;
    public Missile mis;
    public int numMis;

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
        backg.setFitWidth(950);
        backg.setFitHeight(750);
        missiles = new ArrayList<Missile>();
    } // DemoGame

    /** {@inheritDoc} */
    @Override
    protected void init() {
        // setup subgraph for this component
        setInvaders();
        getChildren().addAll(backg, player);         // add to main container
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                getChildren().add(invaders.getAlien(i, j));
            }
        }
        player.setX(320);      // 50px in the x direction (right)
        player.setY(600);      // 50ps in the y direction (down)
        player.setOnMouseClicked(event -> handleClickPlayer(event));
        this.setOnKeyPressed(keycode -> handleMissile(keycode));
    } // init



    /** {@inheritDoc} */
    @Override
    protected void update() {
        isKeyPressed(KeyCode.LEFT, () -> player.setX(player.getX() - 10.0));
        isKeyPressed(KeyCode.RIGHT, () -> player.setX(player.getX() + 10.0));


        ArrayList<Bounds> shipMissiles = new ArrayList<Bounds>();
        ArrayList<Bounds> alienMissiles = new ArrayList<Bounds>();
        invaders.update();
        for (int i = 0; i < missiles.size(); i++) {
            missiles.get(i).update();
            for (int j = 0; j < 40; j++) {
                try {
                    Alien al = invaders.getAlien(j);
                    Bounds ab = al.getBoundsInParent();
                    if (missiles.get(i).goingUp()) {
                        Bounds mb = missiles.get(i).getBoundsInParent();
                        if (ab.intersects(mb)) {
                            missiles.get(i).setDead();
                            al.setDead();
                        }
                    }
                } catch(NullPointerException npe) {
                    //nothing
                }
                if (missiles.get(i).goingUp()) {
                    Bounds sb = this.player.getBoundsInParent();
                    Bounds mb = missiles.get(i).getBoundsInParent();
                    if (sb.intersects(mb)) {
                        missiles.get(i).setDead();
                        this.player.takeLife();
                    }
                }
            }
        }
        /* if (Math.random() <= .06) {
            int num = 1000;
            int numx;
            Alien a = invaders.getAlien(0,0);
            do {
                numx = (int)(Math.random() * 40);
                a = invaders.getAlien(numx / 10, numx % 10);

            } while (a != null);
            Missile newmis = new Missile(this, a.getX(), a.getY(), false);
            missiles.add(newmis);
            getChildren().add(newmis);
            }*/

    } // update

    public void handleMissile(KeyEvent kc) {
        if (kc.getCode().getCode() == (KeyCode.SPACE.getCode())) {
            Missile newmis = new Missile(this, player.getX(), 620, true);
            missiles.add(newmis);
            getChildren().add(newmis);
        }
    }

    public void setMissile(Missile m) {
        missiles.add(m);
    }

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
                Alien newalien = new Alien(this, j * 70.0, i * 45.0);
                this.invaders.setInvads(i, j, newalien);
            }
        }
    }
} // DemoGame
