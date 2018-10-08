package cards;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Card extends StackPane {

    public Card heartCard(int n){
        setImage(n, 'h');
        setCardId(n, 'h');
        CardsControl cardControl = new CardsControl(Card.this);

        return Card.this;
    }

    public Card spadeCard(int n){
        setImage(n, 's');
        setCardId(n, 's');
        CardsControl cardControl = new CardsControl(Card.this);

        return Card.this;
    }

    public Card diamondCard(int n){
        setImage(n, 'd');
        setCardId(n, 'd');
        CardsControl cardControl = new CardsControl(Card.this);

        return Card.this;
    }

    public Card clubCard(int n){
        setImage(n, 'c');
        setCardId(n, 'c');
        CardsControl cardControl = new CardsControl(Card.this);

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

    public Card freeStack(){
        ImageView imageFreeStack = new ImageView("cards/imageCards/freeStack.png");
        Card.this.setId("00");

        CardsControl cardControl = new CardsControl(Card.this);
        cardControl.freeStackClickHandle();

        getChildren().add(imageFreeStack);
        return Card.this;
    }

    public Card extraCardImg(){
        ImageView imageExtraCards = new ImageView("cards/imageCards/disabledCard.png");
        getChildren().add(imageExtraCards);

        return Card.this;
    }
}

