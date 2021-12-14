package cs1302.game;

import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import cs1302.game.Game;
import javafx.scene.paint.*;

/**
 * End window is a region that shows the end game message.
 */
public class EndWindow extends Region {

    private VBox root;
    private HBox hbox;
    public Region r;
    private Game game;
    Text t;

    /**
     * Constructs end game window.
     * @param game is the current game.
     * @param won is the boolean dictating which message is shown.
     */
    public EndWindow(Game game, boolean won) {
        super();
        this.game = game;
        this.setWidth(400);
        this.setHeight(150);
        t = new Text("YOU LOST");
        if (won) {
            t = new Text("YOU WON");
        }
        t.setFont(Font.font(144));
        this.getChildren().add(t);
        //setX(150);
        //setY(500);
    }

/**
 * Sets variables of the end window.
 */
    public void setCords() {
        setCenterShape(true);
        BackgroundFill bFill = new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(10));
        Background background = new Background(bFill);
        this.setBackground(background);
    }
}
