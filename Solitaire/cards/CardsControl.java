package cards;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import utils.Utilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardsControl extends StackPane {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    private Card card;

    private static List<Card> pickedCards = new ArrayList<>();
    private static List<Card> toColumnList = new ArrayList<>();
    private static List<Card> columnCards = new ArrayList<>();

    private int clickedLeftColumn;

    public static int numOfMoves = 0;
    public static int numOfStacked = 0;
    public static int[] stacked = new int[] {0, 0, 0, 0};

    public CardsControl(Card card) {
        this.card = card;
        clickHandle();
    }

    private void clickHandle() {
       // this.card.setOnMousePressed(event -> pressedd(event));
       // this.card.setOnMouseDragged(event -> drag(event));
        //this.card.setOnMouseReleased(event -> dragReleased(event));
        this.card.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) primaryClickButton();
            if (event.getButton() == MouseButton.SECONDARY) secondaryClickButton();
        });
    }

    public void pressedd(MouseEvent event){
        primaryClickButton();
        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();
        orgTranslateX = ((Card)(event.getSource())).getTranslateX();
        orgTranslateY = ((Card)(event.getSource())).getTranslateY();
    }

    public void drag(MouseEvent event){
        this.card.toFront();
        double offsetX = event.getSceneX() - orgSceneX;
        double offsetY = event.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        ((Card)(event.getSource())).setTranslateX(newTranslateX);
        ((Card)(event.getSource())).setTranslateY(newTranslateY);
    }

    public void dragReleased(MouseEvent event){
        System.out.println("released");
    }

    private void primaryClickButton() {
        clearLists();

        int clickedLeftRow = GridPane.getRowIndex(this.card);
        this.clickedLeftColumn = GridPane.getColumnIndex(this.card);

        this.columnCards = getNodesFromColumn(this.clickedLeftColumn);
        this.pickedCards = pickableCardsList(this.columnCards, clickedLeftRow);
    }

    private void secondaryClickButton() {
        int clickedRightColumn = GridPane.getColumnIndex(this.card);

        this.toColumnList = getNodesFromColumn(clickedRightColumn);

        if (!this.pickedCards.isEmpty() && isDroppable()) {
            this.toColumnList = newDroppedCardsList(clickedRightColumn, this.pickedCards);

            if (numOfCardsInARow(this.toColumnList) == 12) {
                removeFullStackInRow(this.toColumnList);
                numOfStacked++;
            }
            numOfMoves++;
        }
    }

    private void clearLists() {
        this.toColumnList.clear();
        this.pickedCards.clear();
        this.columnCards.clear();
    }

    private List getNodesFromColumn(int column) {
        List<Card> columnList = new ArrayList<>();

        for (Node card : Utilities.getGridPane().getChildren()) {
            if (Utilities.getGridPane().getColumnIndex(card) == column) {
                columnList.add((Card) card);
            }
        }
        return columnList;
    }

    private List pickableCardsList(List<Card> columnCardsList, int Row) {
        List<Card> pickableCardsList = new ArrayList<>();

        pickableCardsList.add(columnCardsList.get(columnCardsList.size() - 1));
        for (int i = columnCardsList.size() - 2; i >= Row; i--) {
            if (getIdColor(columnCardsList.get(i)) == getIdColor(columnCardsList.get(i + 1)) && getIdNum(columnCardsList.get(i)) - getIdNum(columnCardsList.get(i + 1)) == 1) {
                pickableCardsList.add(columnCardsList.get(i));
            } else break;
        }
        Collections.reverse(pickableCardsList);

        return pickableCardsList;
    }

    private char getIdColor(Node selected) {
        char idColor = selected.getId().charAt(0);

        return idColor;
    }

    private int getIdNum(Node selected) {
        int idNum = Integer.parseInt(selected.getId().substring(1));

        return idNum;
    }

    private boolean isDroppable() {
        return getIdNum(this.toColumnList.get(this.toColumnList.size() - 1)) - getIdNum(this.pickedCards.get(0)) == 1;
    }

    private List newDroppedCardsList(int Column, List<Card> pickedCardList) {
        for (int i = 0; i < pickedCardList.size(); i++) {
            GridPane.setConstraints(pickedCardList.get(i), Column, this.toColumnList.size());
            pickedCardList.get(i).toFront();
            this.toColumnList.add(pickedCardList.get(i));
        }
        return this.toColumnList;
    }

    private int numOfCardsInARow(List<Card> ColumnNodeList) {
        int numOfCardsInARow = 0;
        for (int i = ColumnNodeList.size() - 2; i >= 0; i--) {
            if (getIdNum(ColumnNodeList.get(i)) - getIdNum(ColumnNodeList.get(i + 1)) == 1 && getIdColor(ColumnNodeList.get(i)) == getIdColor(ColumnNodeList.get(i + 1))) {
                numOfCardsInARow++;
            } else break;
        }
        return numOfCardsInARow;
    }

    private void removeFullStackInRow(List<Card> ColumnList) {
        Collections.reverse(ColumnList);

        fillStackedTable(getIdColor(ColumnList.get(0)));

        for (int i = 0; i <= 12; i++) {
            Utilities.getGridPane().getChildren().remove(ColumnList.get(i));
        }
    }

    private static void fillStackedTable(char stackedId){
        switch (stackedId) {
            case 'h':
                stacked[0]++;
                break;
            case 's':
                stacked[1]++;
                break;
            case 'd':
                stacked[2]++;
                break;
            case 'c':
                stacked[3]++;
                break;
        }
    }

    public void freeStackClickHandle() {
        this.card.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                int column = GridPane.getColumnIndex(this.card);
                this.toColumnList = getNodesFromColumn(column);

                if (!this.pickedCards.isEmpty() && toColumnList.size() == 1) {
                    for (int i = 0; i < this.pickedCards.size(); i++) {
                        GridPane.setConstraints(this.pickedCards.get(i), column, i + 1);
                    }
                    numOfMoves++;
                    clearLists();
                }
            }
        });
    }
}