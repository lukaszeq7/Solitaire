package games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cards.Card;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.Utilities;


/**
 * @author lukaszeqqq
 */
public class Spider extends Application {
    private static final int NUM_OF_CARDS = 13;
    private static final int NUM_OF_COLUMNS = 10;

    int clickedLeftColumn;
    int clickedLeftRow;
    private int clickedRightColumn;
    private int numOfMoves = 0;
    private int numOfStacked = 0;

    int numOfRowsTo;
    int indexOfLastCard;
    int numOfExtraCards = 5;
    int counter = 0;

    List<Card> pickedCards = new ArrayList<>();
    List<Card> toColumnList = new ArrayList<>();
    List<Card> columnCards = new ArrayList<>();
    List<Card> cards = new ArrayList<>();
    List<Card> extraCardsStack = new ArrayList<>();
    List<CardControl> cardControlList = new ArrayList<>();

    GridPane gpane = new GridPane();
    StackPane infoPane = new StackPane();


    Card cardSelected;

    private Parent createContent() {
        gpane.setPrefSize(1270, 800);
        gpane.setStyle("-fx-background-color: green;");
        gpane.setHgap(10);
        gpane.setVgap(-110);

        setInfoPane();
        setExtraCardsStack();

        gpane.getChildren().add(infoPane);


        List<Card> freeStackCards = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 1; j <= NUM_OF_CARDS; j++) {
                cards.add(new Card().heartCard(j));

                //cards.add(new Card().spadeCard(j));
            }

        }



        Collections.shuffle(cards);
        //Collections.shuffle(cards);

        for (int i = 0; i < NUM_OF_COLUMNS; i++) {
            freeStackCards.add(new Card().freeStack());
            gpane.getChildren().add(freeStackCards.get(i));

            gpane.getColumnConstraints().add(new ColumnConstraints(100));
            GridPane.setConstraints(freeStackCards.get(i), i, 0);
        }

        int stackSize = 5;
        Card card = new Card();
        for (int i = 0; i < NUM_OF_COLUMNS; i++) {
            for (int j = 1; j <= stackSize; j++) {
                if (i > 3) {
                    stackSize = 4;
                }
                GridPane.setConstraints(cards.get(counter), i, j);
                //card.disableCard(cards.get(counter));

                gpane.getChildren().add(cards.get(counter));

                counter++;
            }
            GridPane.setConstraints(cards.get(counter), i, stackSize + 1);
            gpane.getChildren().add(cards.get(counter));
            counter++;
        }


        Utilities utility = new Utilities();
        utility.setGridPane(gpane);
        return gpane;
    }




    public class CardControl extends StackPane {
        public CardControl(Card card) {
            card.setOnMouseClicked(event -> {
                System.out.println(card.getId());
                System.out.println(GridPane.getColumnIndex(this));

            });

        }
    }


    /*
        private void control() {
            setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    clearLists();

                    clickedLeftColumn = GridPane.getColumnIndex(this);
                    clickedLeftRow = GridPane.getRowIndex(this);

                    columnCards = getNodesFromColumn(clickedLeftColumn);
                    pickedCards = pickableCardsList(columnCards, clickedLeftRow);

                    indexOfLastCard = columnCards.size() - pickedCards.size() - 1;
                }

                if (e.getButton() == MouseButton.SECONDARY) {
                    clickedRightColumn = GridPane.getColumnIndex(this);

                    toColumnList = getNodesFromColumn(clickedRightColumn);
                    numOfRowsTo = toColumnList.size();
                    if (missClick()) {

                    } else {
                        int trueCardsInRow;

                        if (isDropable()) {
                            toColumnList = newDropedCardsList(clickedRightColumn, pickedCards);
                            trueCardsInRow = numOfCardsInARow(toColumnList);
                            numOfMoves++;

    //                            if(startTimer == false){
    //                                doTime();
    //                                startTimer = true;
    //                            }

                            if (isFullStackInRow(trueCardsInRow)) {
                                removeFullStackInRow(toColumnList);
                                numOfStacked++;

                                if (getIdColor(toColumnList.get(13)) == 'x') {
                                    Card card = new Card().enableCard(toColumnList.get(13));
                                }

    //                                if(numOfStacked == numToStacked){
    //                                    showResultWindow();
    //                                }
                            }
                            if (getIdColor(columnCards.get(indexOfLastCard)) == 'x') {
                                Card card = new Card().enableCard(columnCards.get(indexOfLastCard));
                            }
                        }
                    }
                }
            });
        }
    */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void setInfoPane() {
        infoPane.setPrefSize(170, 800);
        infoPane.setStyle("-fx-background-color: blue;");
        GridPane.setConstraints(infoPane, NUM_OF_COLUMNS, 0, 1, 200);
    }

    private void setExtraCardsStack() {
        for (int i = 0; i < numOfExtraCards; i++) {
            Card extraCard = new Card().extraCardImg();
            extraCard.setTranslateY(-300);
            extraCard.setTranslateX(-30 + (i * 15));

            extraCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    infoPane.getChildren().remove(extraCardsStack.get(numOfExtraCards - 1));
                    numOfExtraCards--;

       //             extraCard.extraCardControl();
                }
            });
            extraCardsStack.add(extraCard);
            infoPane.getChildren().add(extraCard);
        }
    }


    /*
     private class Card extends StackPane  {
         private Card heartCard(int n){
             setImage(n, 'h');
             setCardId(n, 'h');
             control();

             return Card.this;
         }

         private Card spadeCard(int n){
             setImage(n, 's');
             setCardId(n, 's');
             control();

             return Card.this;
         }

         private Card diamondCard(int n){
             setImage(n, 'd');
             setCardId(n, 'd');
             control();

             return Card.this;
         }

         private Card clubCard(int n){
             setImage(n, 'c');
             setCardId(n, 'c');
             control();

             return Card.this;
         }

         private void setImage(int numCard, char colorCardId){
             ImageView imageCard;

             switch (colorCardId) {
                 case 'h':
                     imageCard = new ImageView("cards/imageCards/h" + numCard + ".png");
                     getChildren().add(imageCard);
                     break;

                 case 's':
                     imageCard = new ImageView("cards/imageCards/s" + numCard + ".png");
                     getChildren().add(imageCard);
                     break;

                 case 'd':
                     imageCard = new ImageView("cards/imageCards/d" + numCard + ".png");
                     getChildren().add(imageCard);
                     break;

                 case 'c':
                     imageCard = new ImageView("cards/imageCards/c" + numCard + ".png");
                     getChildren().add(imageCard);
                     break;
             }
         }

         private void setCardId(int numCard, char colorCardId){
             switch (colorCardId){
                 case 'h':
                     Card.this.setId("h"+numCard);
                     break;

                 case 's':
                     Card.this.setId("s"+numCard);
                     break;

                 case 'd':
                     Card.this.setId("d"+numCard);
                     break;

                 case 'c':
                     Card.this.setId("c"+numCard);
                     break;
             }
         }

    public class SpiderControl extends StackPane {




        private void clearLists() {
            toColumnList.clear();
            pickedCards.clear();
            columnCards.clear();
        }

        private List getNodesFromColumn(int column) {
            List<Card> columnList = new ArrayList<>();
            for (Node cardNode : gpane.getChildren()) {
                if (gpane.getColumnIndex(cardNode) == column) {
                    columnList.add((Card) cardNode);
                }
            }
            return columnList;
        }


 /*
        private void freeStackControl() {
            setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.SECONDARY) {
                    if (!pickedCards.isEmpty()) {
                        int column = GridPane.getColumnIndex(this);
                        for (int i = 0; i < pickedCards.size(); i++) {
                            GridPane.setConstraints(pickedCards.get(i), column, i + 1);
                        }

                        if (getIdColor(columnCards.get(indexOfLastCard)) == 'x') {
                            Card card = new Card().enableCard(columnCards.get(indexOfLastCard));
                        }
                        numOfMoves++;
                        clearLists();
                    }
                }
            });
        }



        ////////////////////////////////////////////////////////////////////////////////////////

        private void extraCardControl() {
            for (int i = 0; i < NUM_OF_COLUMNS; i++) {
                List<Card> columnList = new ArrayList<>();
                columnList = getNodesFromColumn(i);
                //Collections.reverse(columnList);
                GridPane.setConstraints(cards.get(counter), i, columnList.size());
                gpane.getChildren().add(cards.get(counter));
                columnList.add(cards.get(counter));
                counter++;

                if (isFullStackInRow(numOfCardsInARow(columnList))) {
                    removeFullStackInRow(columnList);
                    numOfStacked++;

                    if (getIdColor(columnList.get(13)) == 'x') {
                        Card card = new Card().enableCard(columnList.get(13));
                    }
                }
            }
        }

        ////////////////////////////////////////// ///////////////////////////////////////////////


        private List pickableCardsList(List<Card> columnCardsList, int Row) {
            List<Node> pickableCardsList = new ArrayList<>();

            pickableCardsList.add(columnCardsList.get(columnCardsList.size() - 1));
            for (int i = columnCardsList.size() - 2; i >= Row; i--) {
                if (getIdColor(columnCardsList.get(i)) == getIdColor(columnCardsList.get(i + 1)) && getIdNum(columnCardsList.get(i)) - getIdNum(columnCardsList.get(i + 1)) == 1) {
                    pickableCardsList.add(columnCardsList.get(i));
                } else break;
            }
            Collections.reverse(pickableCardsList);

            return pickableCardsList;
        }

        private int getIdNum(Node selected) {
            int idNum = Integer.parseInt(selected.getId().replaceAll("\\D+", ""));

            return idNum;
        }

        private char getIdColor(Node selected) {
            char idColor = selected.getId().charAt(0);
            return idColor;
        }

        private boolean missClick() {
            return clickedLeftColumn == clickedRightColumn || pickedCards.isEmpty();
        }

        private boolean isDropable() {
            return getIdNum(toColumnList.get(toColumnList.size() - 1)) - getIdNum(pickedCards.get(0)) == 1 && getIdColor(pickedCards.get(0)) != 'x';
        }

        private List newDropedCardsList(int Column, List<Card> pickedCardList) {
            for (int i = 0; i < pickedCardList.size(); i++) {
                GridPane.setConstraints(pickedCardList.get(i), Column, numOfRowsTo + i);
                pickedCardList.get(i).toFront();
                toColumnList.add(pickedCardList.get(i));
            }
            return toColumnList;
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

        private boolean isFullStackInRow(int numOfCardsInARow) {
            return numOfCardsInARow == 12;
        }

        private void removeFullStackInRow(List<Card> ColumnList) {
            Collections.reverse(ColumnList);
            for (int i = 0; i <= 12; i++) {
                gpane.getChildren().remove(ColumnList.get(i));
            }
        }

        private Card disableCard(Card card) {
            ImageView imageCard = new ImageView("cards/imageCards/disabledCard.png");
            card.setId("x" + card.getIdColor(card) + card.getIdNum(card));
            card.getChildren().add(imageCard);
            //card.setDisable(true);

            return card;
        }

        private Card enableCard(Card card) {
            card.setId(card.getId().substring(1));
            card.setImage(card.getIdNum(card), card.getIdColor(card));
            return card;
        }




 */
    public static void main(String[] args) {
        launch(args);
    }

}