package controllers;

import cards.CardsControl;
import games.MrsMop;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Utilities;

import java.io.IOException;

public class WinDialogController {
    @FXML
    private Label winLabel;
    @FXML
    private VBox vBoxWinDialog;
    @FXML
    private Label timeLabel;
    @FXML
    private Label movesLabel;

    @FXML
    private void initialize(){
        timeLabel.setText(MrsMopInfoPaneController.getTimer());
        movesLabel.setText(Integer.toString(CardsControl.numOfMoves));
        splashScreenOut();
    }

    @FXML
    private void newGameOnAction() throws Exception {
        MrsMop mrsmop = new MrsMop();
        mrsmop.start(Utilities.getStage());

        closeWindowAndResetValues();
    }

    @FXML
    private void quitOnAction() {
        Platform.exit();
    }

    @FXML
    private void mainMenuOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Utilities.getStage().setScene(new Scene(root));
        Utilities.getStage().centerOnScreen();

        closeWindowAndResetValues();
    }

    private void closeWindowAndResetValues() {
        Stage stage = (Stage) vBoxWinDialog.getScene().getWindow();
        stage.close();

        CardsControl.numOfStacked = 0;
        CardsControl.numOfMoves = 0;
        CardsControl.stacked[0] = 0;
        CardsControl.stacked[1] = 0;
        CardsControl.stacked[2] = 0;
        CardsControl.stacked[3] = 0;
        MrsMopInfoPaneController.timer = 0;
    }

    private void splashScreenIn(){
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(1000));
        ft.setNode(winLabel);
        ft.setFromValue(0.3);
        ft.setToValue(1);
        ft.setOnFinished(event -> splashScreenOut());
        ft.play();
    }

    private void splashScreenOut(){
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(1000));
        ft.setNode(winLabel);
        ft.setFromValue(1);
        ft.setToValue(0.3);
        ft.setOnFinished(event -> splashScreenIn());
        ft.play();
    }
}
