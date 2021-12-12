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
        System.out.println(deaths);
        return deaths;
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

/*    public ArrayList<Missile> getAllAlMisses() {
        allalmisses = new ArrayList<Missile>();
        for (int i = 0; i < invads.length; i++) {
            for (int j = 0; j < invads[0].length; j++) {
                allalmisses.addAll(invads[i][j].getAlMisses());
            }
        }
        return allalmisses;
    }
*/
    public Alien getAlien(int i, int j) {
        return invads[i][j];
    }

    public Alien getAlien(int z) {
        return invads[z/10][z%10];
    }

    public void setInvads(int i, int j, Alien al) {
        invads[i][j] = al;
    }
}
