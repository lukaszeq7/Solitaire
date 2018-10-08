package controllers;

import cards.CardsControl;
import games.MrsMop;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import utils.Dialogs;
import utils.Utilities;
import java.io.IOException;

public class MrsMopInfoPaneController {
    @FXML
    private VBox vboxMenu;
    @FXML
    private Label timerLabel;
    @FXML
    private Label numOfMovesLabel;
    @FXML
    private Label numHstackedLabel;
    @FXML
    private Label numSstackedLabel;
    @FXML
    private Label numDstackedLabel;
    @FXML
    private Label numCstackedLabel;

    public static Integer timer = 0;
    private boolean stopTimer = false;

    @FXML
    public void initialize(){
        vboxMenu.getStylesheets().add("stylesheet.css");
        Utilities.getGridPane().setOnMouseClicked(event -> {
            numOfMovesLabel.setText(Integer.toString(CardsControl.numOfMoves));
            numHstackedLabel.setText(Integer.toString(CardsControl.stacked[0]));
            numSstackedLabel.setText(Integer.toString(CardsControl.stacked[1]));
            numDstackedLabel.setText(Integer.toString(CardsControl.stacked[2]));
            numCstackedLabel.setText(Integer.toString(CardsControl.stacked[3]));
        });

        numOfMovesLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            if(observable.getValue().toString().matches("1")) startTimer();
        });
    }

    @FXML
    private void newGameOnAction() throws Exception {
        resetValues();

        MrsMop mrsmop = new MrsMop();
        mrsmop.start(Utilities.getStage());
    }

    @FXML
    private void menuOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Utilities.getStage().setScene(new Scene(root));
        Utilities.getStage().centerOnScreen();

        resetValues();
    }

    @FXML
    private void backOnAction( ) {
        Dialogs.betaVersionDialog();
    }

    @FXML
    private void quitOnAction() {
        Platform.exit();
    }

    private void startTimer(){
        Timeline timeline = new Timeline();

        KeyFrame frame= new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                timer++;
                timerLabel.setText(Utilities.timeConverter(timer));

                if(stopTimer == true){
                    stopTimer = false;
                    timeline.stop();
                    timer = 0;
                }

                if(CardsControl.numOfStacked == 8){
                    stopTimer = false;
                    timeline.stop();
                    Dialogs winDialog = new Dialogs();
                    try {
                        winDialog.winDialog().show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public static String getTimer(){
        String time = Utilities.timeConverter(timer);
        return time;
    }

    private void resetValues() {
        CardsControl.numOfStacked = 0;
        CardsControl.numOfMoves = 0;
        CardsControl.stacked[0] = 0;
        CardsControl.stacked[1] = 0;
        CardsControl.stacked[2] = 0;
        CardsControl.stacked[3] = 0;
        stopTimer = true;
        timer = 0;
    }
}
