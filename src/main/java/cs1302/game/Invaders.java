package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import java.util.*;

public class Invaders {

    private Game game; // game containing this sprite
    private double dx; // change in x per update
    private double dy; // change in y per update
    public Alien[][] invads;
    public int deaths;

/**
     * Construct an {@code IdleCat} object.
     * @param game parent game
     */

    public Invaders(Game game) {
        this.game = game;
        invads = new Alien[4][10];
        deaths = 0;
    }

    public int getSize() {
        return 40 - checkDeaths();
    }

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

    public boolean isWon() {
        if (checkDeaths() == 40) {
            return true;
        }
        return false;

    }

    public void removeAlien(int x, int y) {
        invads[x][y] = null;
    }

    public boolean checkDeath(int x, int y) {
        if (invads[x][y] == null) {
            return true;
        }
        return false;
    }


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
                } catch(NullPointerException npe) {
                    //nothing
                }
            }
        }
        return false;
    }

    public void update() {
        boolean godown = checkBounds();
        double speed = 2.15 + Math.pow(checkDeaths(), 1.32) / 30.0;
        for (int i = 0; i < invads.length; i++) {
            for (int j = 0; j < invads[0].length; j++) {
                try {
                    invads[i][j].update(godown, speed);
                } catch (NullPointerException npe) {
                    //nothing
                }
            }
        }
    }

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

    public Alien getAlien(int z) {
        return invads[z/10][z%10];
    }

    public void setInvads(int i, int j, Alien al) {
        invads[i][j] = al;
    }
}
