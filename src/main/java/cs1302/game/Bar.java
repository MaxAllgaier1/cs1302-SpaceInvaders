package cs1302.game;

import javafx.geometry.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;



public class Bar extends HBox {
    public HBox hbox;
    public Game game;
    public int x;
    public int y;
    private Button pause;
    private final Timeline loop = new Timeline();
    Text text;
    Text levelText;
    int tx;

/**
     * Construct an {@code IdleCat} object.
     * @param game parent game
     */
    public Bar(Game game) {
        super();
        this.game = game;
        Rectangle bre = new Rectangle(0, 0, 20, 100);
        bre.setFill(Color.BLACK);
        pause = new Button("Start");
        pause.setMinWidth(150);
        pause.setMinHeight(75);
        Button stop = new Button("Restart Game");
        stop.setMinWidth(150);
        stop.setMinHeight(75);
        this.getChildren().addAll(pause, bre, stop);
        pause.setOnAction(this::pause);
        stop.setOnAction(this::stop);
        x = 0;
    }

    public void pause(ActionEvent p) {
        if (pause.getText().equals("Pause")) {
            pause.setText("Play");
            this.game.pause();
        } else {
            pause.setText("Pause");
            this.game.play();
       	}
    }

    public void stop(ActionEvent s) {
        this.game.stop();
        pause.setText("Play");
    }
}
