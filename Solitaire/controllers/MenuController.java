package controllers;

import games.MrsMop;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import utils.Dialogs;
import utils.Utilities;

public class MenuController {
    @FXML
    private ToggleGroup difficulty;

    public static String levelId;

    @FXML
    private void infoBtnOnAction() {
        Dialogs.informationsDialog();
    }

    @FXML
    private void quitOnAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    private void mrsMopOnAction() throws Exception {
        levelId = setLevelId();

        MrsMop mrsmop = new MrsMop();
        mrsmop.start(Utilities.getStage());
    }

    @FXML
    private void statsOnAction() {
        Dialogs.betaVersionDialog();
    }

    private String setLevelId(){
        RadioButton selectedRadioButton = (RadioButton) difficulty.getSelectedToggle();
        return selectedRadioButton.getId().toString();
    }
}
