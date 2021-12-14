package cs1302.game;

import javafx.geometry.Bounds;
import java.util.Random;
import java.util.logging.Level;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.lang.Math;
import java.lang.*;

/**
 * Space Invaders game. has one round of normal space invaders and then a boss round.
 */
public class MyGame extends Game {

    private Random rng;
    private SpaceShip player;
    private Alien alien;
    private Invaders invaders;
    public ImageView backg;
    public ArrayList<Missile> missiles;
    public Missile mis;
    public int level;
    public Bounds lower;
    public EndWindow ew;
    public EndWindow winew;
    public Bar thebar;
    public Image ewImage;
    public ImageView ewIView;
    public Image winewImage;
    public ImageView winewIview;
    public int numba;
    public ImageView scoreV;
    public ImageView levelV;
    public ImageView livesV;
    public Image leImage;
    public Image scImage;
    public Image liImage;
    public Boss boss;
    ArrayList<Bounds> shipMissiles;
    ArrayList<Bounds> alienMissiles;
    ArrayList<Bounds> bossMissiles;


    /**
     * Construct a {@code MyGame} object.
     * @param width scene width
     * @param height scene height
     */
    public MyGame(int width, int height) {
        super(width, height, 30);            // call parent constructor. 30 fps
        setLogLevel(Level.INFO);
        this.player = new SpaceShip(this, 320, 550);
        this.invaders = new Invaders(this);
        Image background = new Image("file:resources/sprites/Background.png");
        backg = new ImageView(background);
        backg.setPreserveRatio(false);
        backg.setFitWidth(950);
        backg.setFitHeight(750);
        missiles = new ArrayList<Missile>();
        ew = new EndWindow(this, false);
        ewImage = ew.snapshot(null, null);
        ewIView = new ImageView(ewImage);
        boss = new Boss(this);
        winew = new EndWindow(this, true);
        winewImage = winew.snapshot(null, null);
        winewIview = new ImageView(winewImage);
    } // MyGame

    /** {@inheritDoc} */
    @Override
    protected void init() {
        // setup subgraph for this component
        setInvaders();
        getChildren().addAll(backg, player);         // add to main container
        numba = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                getChildren().add(invaders.getAlien(i, j));
            }
        }
        player.setX(320);
        player.setY(600);
        this.setOnKeyPressed(keycode -> handleMissile(keycode));

        Rectangle rect = new Rectangle(0, 625, 1000, 5);
        lower = rect.getBoundsInParent();
        level = 0;
        Text lev = new Text("Level: " + level);
        leImage = lev.snapshot(null, null);
        levelV = new ImageView(leImage);
        Text sco = new Text("Score: " + getScore());
        scImage = sco.snapshot(null, null);
        scoreV = new ImageView(scImage);
        Text liv = new Text("Lives: " + this.player.getLives());
        liImage = liv.snapshot(null, null);
        livesV = new ImageView(liImage);
        this.getChildren().addAll(levelV, scoreV, livesV);
        levelV.setX(750);
        levelV.setY(50);
        scoreV.setX(750);
        scoreV.setY(100);
        livesV.setX(750);
        livesV.setY(150);
    } // init



    /** {@inheritDoc} */
    @Override
    protected void update() {
        if (player.getX() > 0) {
            isKeyPressed(KeyCode.LEFT, () -> player.setX(player.getX() - 10.0));
        }
        if (player.getX() < 870) {
            isKeyPressed(KeyCode.RIGHT, () -> player.setX(player.getX() + 10.0));
        }
        if (level == 0) {
            //aliens and missiles are put in array list so they can easily be referenced later
            shipMissiles = new ArrayList<Bounds>();
            alienMissiles = new ArrayList<Bounds>();
            invaders.update();
            invaders.checkDeaths();
            checkTheAliens();
            //this add variable slowly decreases as aliens die so that the final aliens arent
            //shooting as much as the original 40 aliens combined.
            double add = (40 - invaders.checkDeaths()) * .00175;
            //shoots alien missiles
            if (Math.random() <= .033 + add) {
                int num = 1000;
                int numx;
                Alien a;
                do {
                    numx = (int)(Math.random() * 40);
                    a = invaders.getAlien(numx / 10, numx % 10);
                } while (false);
                Missile newmis = new Missile(this, a.getX(), a.getY(), false, 0);
                missiles.add(newmis);
                getChildren().add(newmis);
            }
            if (invaders.isWon()) {
                level = 1;
                this.getChildren().add(boss);
            }
        }
        //this counter is used to stop spaceship from always shooting.
        //spaceship can only shoot after a certain number of frames has passed since last shot
        if (numba > 0) {
            numba -= 1;
        }
        setTexts();
        //level 2 boss level.(variable is 1)
        if (level == 1) {
            boss.update();
            shipMissiles = new ArrayList<Bounds>();
            bossMissiles = new ArrayList<Bounds>();
            checkBossMissiles();
            if (Math.random() <= .1) {
                double thex = boss.getX() * Math.random() * 2;
                double they = boss.getY();
                Missile newmis = new Missile(this, thex, they, false, 1);
                missiles.add(newmis);
                getChildren().add(newmis);
            }
            if (boss.isWon()) {
                win();
            }
        }
    }  //update


    /**
     * Compares missiles to boss and ship and takes neccessary actions.
     */
    public void checkBossMissiles() {
        for (int i = 0; i < missiles.size(); i++) {
            missiles.get(i).update();
            try {
                if (boss.intersects(lower) || this.player.getLives() == 0) {
                    System.out.println("bossloss");
                    lose();
                }
                Bounds bb = boss.getBoundsInParent();
                if (missiles.get(i).goingUp()) {
                    Bounds mb = missiles.get(i).getBoundsInParent();
                    if (bb.intersects(mb)) {
                        System.out.println(i);
                        missiles.get(i).setDead();
                        boss.setDead();
                    }
                }
            } catch (NullPointerException npe) {
                i = i;
            }
            if (!missiles.get(i).goingUp()) {
                Bounds sb = this.player.getBoundsInParent();
                Bounds mb = missiles.get(i).getBoundsInParent();
                if (sb.intersects(mb)) {
                    missiles.get(i).setDead();
                    this.player.takeLife();
                    missiles.remove(i);
                    i -= 1;
                }
            }
        }
    }

    /**
     * Sets texts for level, score, and lives.
     */
    public void setTexts() {
        Text lev = getLevelText();
        leImage = lev.snapshot(null, null);
        Text sco = new Text("Score: " + getScore());
        scImage = sco.snapshot(null, null);
        Text liv = new Text("Lives: " + this.player.getLives());
        liImage = liv.snapshot(null, null);
        scoreV.setImage(scImage);
        levelV.setImage(leImage);
        livesV.setImage(liImage);
    }

    /**
     * Checks and compares aliens/ship and missiles and takes neccessary actions.
     */
    public void checkTheAliens() {
        for (int i = 0; i < missiles.size(); i++) { //checks each missile and alien for colision
            missiles.get(i).update();
            for (int j = 0; j < 40; j++) {
                try {
                    Alien al = invaders.getAlien(j);
                    if (al.intersects(lower) || this.player.getLives() == 0) {
                        if (level == 0) {
                            lose();
                        }
                    }
                    Bounds ab = al.getBoundsInParent();
                    if (missiles.get(i).goingUp()) {
                        Bounds mb = missiles.get(i).getBoundsInParent();
                        if (ab.intersects(mb)) {
                            System.out.println(i);
                            missiles.get(i).setDead();
                            al.setDead();
                            j = invaders.getSize();
                        }
                    }
                } catch (NullPointerException npe) {
                    i = i;
                }
            }
            if (!missiles.get(i).goingUp()) { //checks if missiles hit spaceship
                Bounds sb = this.player.getBoundsInParent();
                Bounds mb = missiles.get(i).getBoundsInParent();
                if (sb.intersects(mb)) {
                    missiles.get(i).setDead();
                    this.player.takeLife();
                    missiles.remove(i);
                    i -= 1;
                }
            }

        }
    }



/**
 * Resets all neccessary variables once game is restarted so that init can be called again.
 */
    public void reset() {
        this.getChildren().clear();
        this.player = new SpaceShip(this, 320, 550);
        this.invaders = new Invaders(this);
        Image background = new Image("file:resources/sprites/Background.png");
        backg = new ImageView(background);
        backg.setPreserveRatio(false);
        backg.setFitWidth(950);
        backg.setFitHeight(750);
        missiles = new ArrayList<Missile>();
        ew = new EndWindow(this, false);
        ewImage = ew.snapshot(null, null);
        ewIView = new ImageView(ewImage);
        boss = new Boss(this);
        winew = new EndWindow(this, true);
        winewImage = winew.snapshot(null, null);
        winewIview = new ImageView(winewImage);
    }


/**
 * Handles spacebar being pressed and shooting spaceship missiles.
 * @param kc is the keyevent from spacebar
 */
    public void handleMissile(KeyEvent kc) {
        if (kc.getCode().getCode() == (KeyCode.SPACE.getCode()) && numba == 0) {
            Missile newmis = new Missile(this, player.getX(), 620, true, 0);
            missiles.add(newmis);
            getChildren().add(newmis);
            numba = 7;
        }
    }

    /**
     * Adds a new missile to the missile array.
     * @param m is missile being added
     */
    public void setMissile(Missile m) {
        missiles.add(m);
    }

    /**
     * Initializes the invaders by creating each alien and adding it.
     */
    public void setInvaders() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                Alien newalien = new Alien(this, j * 70.0, i * 45.0);
                this.invaders.setInvads(i, j, newalien);
            }
        }
    }

    /**
     * Called when the game is won. adds a window saying you won.
     */
    public void win() {
        level = 3;
        this.getChildren().add(winewIview);
        winewIview.setX(100);
        winewIview.setY(200);
    }

    /**
     * Called when the game is lost. adds losing message to game.
     */
    public void lose() {
        level = 2;
        this.getChildren().add(ewIView);
        ewIView.setX(100);
        ewIView.setY(200);
        System.out.println("adsf");
    }

    /**
     * Returns score of game. score increases exponentially as aliens are killed and as boss
     * is shot and then killed.
     * @return game score int
     */
    public int getScore() {
        double x = 0;
        double n = 1.5;
        x += Math.pow(this.invaders.checkDeaths(), n) * 3 + ((4.0 - this.player.getLives()) * -20);
        double y = (30 - this.boss.getLives()) * 15;
        if (boss.isWon()) {
            y += 650;
        }
        return (int) (x + y);

    }


    /**
     * Returns a text telling what level it is or if the game is over.
     * @return text with the level number.
     */
    public Text getLevelText() {
        if (level <= 1) {
            Text t = new Text("Level: " + (level + 1));
            return t;
        }
        if (level == 2) {
            Text t = new Text("GAME OVER");
            return t;
        }
        if (level == 3) {
            Text t = new Text("YOU WIN");
            return t;
        }
        Text r = new Text("....");
        return r;
    }

} // MyGame
