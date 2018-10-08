package games;

import cards.Card;
import controllers.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MrsMop {
    private static final int NUM_OF_CARDS = 13;
    private static final int NUM_OF_COLUMNS = 13;
    private static final int NUM_OF_ROWS = 8;

    private HBox hbox = new HBox();
    private GridPane gPane = new GridPane();

    private List<Card> cards = new ArrayList<>();
    private List<Card> freeStackCards;

    private Parent createContent() throws IOException {
        gPane.setAlignment(Pos.TOP_CENTER);
        gPane.setPrefSize(1440, 800);
        gPane.setStyle("-fx-background-color: green;");
        gPane.setHgap(10);
        gPane.setVgap(-110);

        cards = fillCardList();
        freeStackCards = fillFreeStackList();

        Collections.shuffle(cards);
        Collections.shuffle(cards);

        setTable();

        Utilities utility = new Utilities();
        utility.setGridPane(gPane);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MrsMopInfoPane.fxml"));
        VBox mrsMopInfoPane = loader.load();

        hbox.getChildren().addAll(gPane, mrsMopInfoPane);
        hbox.getStylesheets().add("stylesheet.css");

        return hbox;
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private List<Card> fillCardList() {
        switch (MenuController.levelId) {
            case "oneColorRadioBtn":
                for (int i = 0; i < 8; i++) {
                    for (int j = 1; j <= NUM_OF_CARDS; j++) {
                        cards.add(new Card().heartCard(j));
                    }
                }
                break;

            case "twoColorRadioBtn":
                for (int i = 0; i < 4; i++) {
                    for (int j = 1; j <= NUM_OF_CARDS; j++) {
                        cards.add(new Card().heartCard(j));
                        cards.add(new Card().spadeCard(j));
                    }
                }
                break;

            case "fourColorRadioBtn":
                for (int i = 0; i < 2; i++) {
                    for (int j = 1; j <= NUM_OF_CARDS; j++) {
                        cards.add(new Card().heartCard(j));
                        cards.add(new Card().spadeCard(j));
                        cards.add(new Card().diamondCard(j));
                        cards.add(new Card().clubCard(j));
                    }
                }
                break;
        }
        return cards;
    }

    private List<Card> fillFreeStackList() {
        List<Card> freeStackCards = new ArrayList<>();
        for (int i = 0; i < NUM_OF_COLUMNS; i++) {
            freeStackCards.add(new Card().freeStack());
        }
        return freeStackCards;
    }

    private void setTable() {
        int counter = 0;
        for (int i = 0; i < NUM_OF_COLUMNS; i++) {
            gPane.getColumnConstraints().add(new ColumnConstraints(100));
            GridPane.setConstraints(freeStackCards.get(i), i, 0);

            for (int j = 1; j <= NUM_OF_ROWS; j++) {
                GridPane.setConstraints(cards.get(counter), i, j);
                counter++;
            }
        }
        gPane.getChildren().addAll(freeStackCards);
        gPane.getChildren().addAll(cards);
    }
}
