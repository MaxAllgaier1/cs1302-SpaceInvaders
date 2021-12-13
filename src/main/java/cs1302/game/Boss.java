package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import java.util.*;
import cs1302.game.Alien;

/**
 * Boss class
 */
public class Boss extends Alien {
    private Game game;
    private double dx
    private double dx; // change in x per update
    private double dy; // change in y per update
    public double x;
    public double y;
    public double rand;
    public ArrayList<Missile> almisses;
    public boolean dead;
    int my;


/**
     * Construct an {@code IdleCat} object.
    * @param game parent game
   */
    public Boss(Game game, double x, double y) {
        super(game, 30, 30);
        this.setImage("file:resources/sprites/boss.png");
        this.setPreserveRatio(true);
        this.setFitWidth(200);
        this.thegame = game;
        this.dx = 5; // each update, add 2 to x (to start)
        this.dy = 0; // each u
        my = 25;
        setX(x);
        setY(y);
        dead = false;
    }

    public void setDead() {
        setY(1200);
        setX(300);
        dead = true;
    }

    public void update() {
        if (!dead) {
            if (Math.random() < .05 || dx < 0) {
                dx *= -1.02;
            } else {
                dx *= 1.0;
            }
            if (my == 0) {
                SetY(getY() + 15);
                my = 60;
            } else {
                my -= 1;
            }
            setX(getX() + dx);
            setY(getY() + dy);
        }
    }



}
