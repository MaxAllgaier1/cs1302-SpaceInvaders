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
    public int level;
    public Bounds lower;
    public endWindow ew;
    public endWindow winew;
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
        ew = new endWindow(this, false);
        ewImage = ew.snapshot(null, null);
        ewIView = new ImageView(ewImage);
        boss = new Boss(this);
        winew = new endWindow(this, true);
        winewImage = winew.snapshot(null, null);
        winewIview = new ImageView(winewImage);
    } // DemoGame

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
        player.setX(320);      // 50px in the x direction (right)
        player.setY(600);      // 50ps in the y direction (down)
        player.setOnMouseClicked(event -> handleClickPlayer(event));
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
        isKeyPressed(KeyCode.LEFT, () -> player.setX(player.getX() - 10.0));
        isKeyPressed(KeyCode.RIGHT, () -> player.setX(player.getX() + 10.0));

        if (level == 0) {
            ArrayList<Bounds> shipMissiles = new ArrayList<Bounds>();
            ArrayList<Bounds> alienMissiles = new ArrayList<Bounds>();
            invaders.update();
            invaders.checkDeaths();
            for (int i = 0; i < missiles.size(); i++) {
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
                    } catch(NullPointerException npe) {
                        //nothing
                    }
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
	    double add = (40 - invaders.checkDeaths()) * .00175;
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
        if (numba > 0) {
            numba -= 1;
        }
        Text lev = getLevelText();
        leImage = lev.snapshot(null, null);
        Text sco = new Text("Score: " + getScore());
        scImage = sco.snapshot(null, null);
        Text liv = new Text("Lives: " + this.player.getLives());
        liImage = liv.snapshot(null, null);
        scoreV.setImage(scImage);
        levelV.setImage(leImage);
        livesV.setImage(liImage);
        if (level == 1) {
            boss.update();
            ArrayList<Bounds> shipMissiles = new ArrayList<Bounds>();
            ArrayList<Bounds> bossMissiles = new ArrayList<Bounds>();
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
                } catch(NullPointerException npe) {
                    //nothing
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


    public void reset() {
        this.getChildren().clear();
        this.rng = new Random();             // random number generator
        this.player = new SpaceShip(this, 320, 550); // some rectangle to represent the player
        this.invaders = new Invaders(this);
        Image background = new Image("file:resources/sprites/Background.png");
        backg = new ImageView(background);
        backg.setPreserveRatio(false);
        backg.setFitWidth(950);
        backg.setFitHeight(750);
        missiles = new ArrayList<Missile>();
        ew = new endWindow(this, false);
        ewImage = ew.snapshot(null, null);
        ewIView = new ImageView(ewImage);
	boss = new Boss(this);
        winew = new endWindow(this, true);
        winewImage = winew.snapshot(null, null);
        winewIview = new ImageView(winewImage);
    }

    public void handleMissile(KeyEvent kc) {
        if (kc.getCode().getCode() == (KeyCode.SPACE.getCode()) && numba == 0) {
            Missile newmis = new Missile(this, player.getX(), 620, true, 0);
            missiles.add(newmis);
            getChildren().add(newmis);
            numba = 7;
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

    public void win() {
        level = 2;
	System.out.println("You win nsdjfdksflksd");
        this.getChildren().add(winewIview);
        winewIview.setX(100);
        winewIview.setY(200);
    }

    public void lose() {
        level = 2;
        System.out.println("game over asflsdlkjfdshfkjsadkjf");
        this.getChildren().add(ewIView);
        ewIView.setX(100);
        ewIView.setY(200);
        System.out.println("adsf");
    }

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

} // DemoGame
