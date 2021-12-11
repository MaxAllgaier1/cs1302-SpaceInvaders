package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import java.util.*;

public class Invaders {

    private Game game; // game containing this sprite
    private double dx; // change in x per update
    private double dy; // change in y per update
    public Alien[][] invads;
    /**
     * Construct an {@code IdleCat} object.
     * @param game parent game
     */

    public Invaders(Game game) {
        this.game = game;
        invads = new Alien[4][10];
    }
    public boolean checkBounds() {
        for (int i = 0; i < invads.length; i++) {
            for (int j = 0; j < invads[0].length; j++) {
                Alien newal = invads[i][j];
                Bounds alienBounds = newal.getBoundsInParent();
                Bounds gameBounds = game.getGameBounds();
                if (alienBounds.getMaxX() > gameBounds.getMaxX()) {
                    return true;
                } else if (alienBounds.getMinX() < gameBounds.getMinX()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void update() {
        boolean godown = checkBounds();
        for (int i = 0; i < invads.length; i++) {
            for (int j = 0; j < invads[0].length; j++) {
                invads[i][j].update(godown);
            }
        }
    }

    public Alien getAlien(int i, int j) {
        return invads[i][j];
    }

    public void setInvads(int i, int j, Alien al) {
        invads[i][j] = al;
    }
}
