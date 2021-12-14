package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.image.*;
import java.util.*;
import cs1302.game.Alien;

/**
 * Boss class
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
     * Construct an {@code IdleCat} object.
    * @param game parent game
   */
    public Boss(Game game) {
        super(game, 300, 30);
        Image i = new Image("file:resources/sprites/boss.png");
        this.setImage(i);
        this.setPreserveRatio(true);
        this.setFitWidth(200);
        this.dx = 5; // each update, add 2 to x (to start)
        this.dy = 0; // each u
        my = 25;
        dead = false;
        lives = 30;
    }

    public void setDead() {
        lives -= 1;
        if (lives == 0) {
            setY(1200);
            setX(300);
            dead = true;
        }
    }

    public int getLives() {
	return lives;
    }

    public boolean isWon() {
        return dead;
    }

    public void update() {
        if (!dead) {
            if (my == 0) {
                setY(getY() + 15);
                my = 60;
            } else {
                my -= 1;
            }
	    if (this.getX() < 50 || this.getX() > 800) {
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
