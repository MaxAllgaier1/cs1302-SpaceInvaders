package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.image.*;
import java.util.*;
import cs1302.game.Alien;

/**
 * Boss class. creates the final boss with 30 lives
 * extends alien class.
 */
public class Boss extends Alien {
    private Game game;
    private double dx; // change in x per update
    private double dy; // change in y per update
    public double x;
    public double y;
    public double rand;
    public ArrayList<Missile> almisses;
    public boolean dead;
    int my;
    int lives;


/**
 * Construct an {@code Boss} object.
 * @param game parent game
 */
    public Boss(Game game) {
        super(game, 300, 30);
        Image i = new Image("file:resources/sprites/boss.png");
        this.setImage(i);
        this.setPreserveRatio(true);
        this.setFitWidth(200);
        this.dx = 5; // each update, add 5 to x (to start)
        this.dy = 0;
        my = 25;
        dead = false;
        lives = 30;
    }

    /**
     * Named setDead, but it only kills the boss once it has 0 lives.
     * until then, a life is taken when the method is called.
     */
    public void setDead() {
        lives -= 1;
        if (lives == 0) {
            setY(1200);
            setX(300);
            dead = true;
        }
    }

    /**
     * returns boss lives.
     * @return the lives left of the boss.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Boolean method that returns if the boss is dead or not.
     * @return boolean of boss's death boolean. if the boss is dead. the game is won.
     */
    public boolean isWon() {
        return dead;
    }

    /**
     * Updates boss's movement. made so that it is sporadic.
     */
    public void update() {
        if (!dead) {
            if (my == 0) {
                setY(getY() + 15);
                my = 60;
            } else {
                my -= 1;
            }
            if (this.getX() < 50 || this.getX() > 700) {
                dx *= -1;
            } else {
                if (Math.random() <= .02 ) {
                    dx *= -1.02;
                }
            }
            setX(getX() + dx);
        }
    }
}
