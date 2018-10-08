package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class Dialogs {

    public Stage winDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WinDialog.fxml"));
        VBox vbox = loader.load();
        Scene scene = new Scene(vbox);

        Stage newWindow = new Stage();
        newWindow.setScene(scene);
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initOwner(Utilities.getStage());
        newWindow.centerOnScreen();
        newWindow.initStyle(StageStyle.UNDECORATED);

        return newWindow;
    }

    public static void betaVersionDialog(){
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle("Beta Version Information");
        informationAlert.setHeaderText("Beta version!!! :D");
        informationAlert.setContentText("Please hire me to get full access. Contact:\n" +
                "Phone: 509 635 302 \n" +
                "Email: lukasz.kalinowski7@gmail.com");
        informationAlert.showAndWait();
    }

    public static void informationsDialog() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle("Informations");
        informationAlert.setHeaderText("Informations about application");
        informationAlert.setContentText("Click left mouse button to pick cards.\n" +
                "Click right mouse button to drop cards.\n" +
                "\n" +
                "My name is Lukasz Kalinowski and I'm still updating project.\n" +
                "Contact me via email: lukasz.kalinowski7@gmail.com or\n" +
                "phone: 509 635 302.");
        informationAlert.showAndWait();
    }
}
