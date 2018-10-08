package controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Utilities;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable{
    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //stackPane.getStylesheets().add("stylesheet");
        stackPane.setOpacity(0);
        splashScreenIn();
    }

    private void splashScreenIn(){
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(2000));
        ft.setNode(stackPane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setOnFinished(event -> splashScreenOut());
        ft.play();
    }

    private void splashScreenOut(){
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(1000));
        ft.setNode(stackPane);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(event -> {
            try {
                stackPane.getScene().getWindow().hide();
                menuStart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ft.play();
    }

    private void menuStart() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Solitaire: Global Offensive");
        stage.show();
        Utilities stageUtility = new Utilities();
        stageUtility.setStage(stage);
    }
}
