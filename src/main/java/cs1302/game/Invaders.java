package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import java.util.*;

/**
 * Invaders class is a list of aliens.
 */
public class Invaders {

    private Game game; // game containing this
    private double dx;
    private double dy;
    public Alien[][] invads;
    public int deaths;

/**
 * Construct an {@code Invaders} object.
 * @param game parent game
 */
    public Invaders(Game game) {
        this.game = game;
        invads = new Alien[4][10];
        deaths = 0;
    }

    /**
     * Returns amount of aliens left.
     * @return number of aliens left.
     */
    public int getSize() {
        return 40 - checkDeaths();
    }

    /**
     * Checks if the aliens are dead in order to update the array of aliens.
     * @return number of dead aliens.
     */
    public int checkDeaths() {
        deaths = 0;
        for (int i = 0; i < invads.length; i++) {
            for (int j = 0; j < invads[0].length; j++) {
                Alien newal = invads[i][j];
                if (newal == null || newal.getY() == 1000) {
                    deaths += 1;
                    invads[i][j] = null;
                }
            }
        }
        return deaths;
    }

    /**
     * boolean on if first level of invaders is won or not.
     * @return whether or not level is won and all invaders are dead.
     */
    public boolean isWon() {
        if (checkDeaths() == 40) {
            return true;
        }
        return false;
    }

    /**
     * removes alien from the array.
     * @param x is the row of the alien.
     * @param y is the column of the alien.
     */
    public void removeAlien(int x, int y) {
        invads[x][y] = null;
    }

    /**
     * Boolean to check life of singular alien.
     * @return boolen of if the specified alien is dead.
     * @param x is row of alien.
     * @param y is column.
     */
    public boolean checkDeath(int x, int y) {
        if (invads[x][y] == null) {
            return true;
        }
        return false;
    }

    /**
     * checks bounds of alien to see if one is on the side of the game.
     * @return whether or not an alien is at the edge of the game window.
     */
    public boolean checkBounds() {
        for (int i = 0; i < invads.length; i++) {
            for (int j = 0; j < invads[0].length; j++) {
                try {
                    Alien newal = invads[i][j];
                    Bounds alienBounds = newal.getBoundsInParent();
                    Bounds gameBounds = game.getGameBounds();
                    if (alienBounds.getMaxX() > gameBounds.getMaxX()) {
                        return true;
                    } else if (alienBounds.getMinX() < gameBounds.getMinX()) {
                        return true;
                    }
                } catch (NullPointerException npe) {
                    i = i;
                }
            }
        }
        return false;
    }

    /**
     * Updates each alien in the invaders array.
     */
    public void update() {
        boolean godown = checkBounds();
        double speed = 2.15 + Math.pow(checkDeaths(), 1.32) / 30.0;
        for (int i = 0; i < invads.length; i++) {
            for (int j = 0; j < invads[0].length; j++) {
                try {
                    invads[i][j].update(godown, speed);
                } catch (NullPointerException npe) {
                    speed = speed;
                }
            }
        }
    }

    /**
     * Gets alien from specified location, or if null/dead, the next location.
     * @return an alien from the invaders based on params inputted.
     * @param i is row
     * @param j is column
     */
    public Alien getAlien(int i, int j) {
        int ii = i;
        int jj = j;
        while (checkDeath(i, j)) {
            j += 1;
            if (j == 10) {
                j = 0;
                i += 1;
                if (i == 4) {
                    i = 0;
                }
            }
            if (ii == i && jj == j) {
                return null;
            }
        }
        return invads[i][j];
    }

    /**
     * Returns alien in invaders.
     * @return an alien based on param
     * @param z is the alien number in the array.
     */
    public Alien getAlien(int z) {
        return invads[z / 10][z % 10];
    }

    /**
     * Sets the invader array at certain spot to the alien param.
     * @param i is the row
     * @param j is the column
     * @param al is the alien placed in this spot
     */
    public void setInvads(int i, int j, Alien al) {
        invads[i][j] = al;
    }
}
