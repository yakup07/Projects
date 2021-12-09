package sample;

import javafx.scene.control.Label;

public class Card {
    private Cards card;
    private int available;
    private Label cardLabel;

    public Card(Cards card, int available,Label cardLabel) {
        this.card = card;
        this.available = available;
        this.cardLabel = cardLabel;
        setAvailable(available);
    }

    public Label getCardLabel() {
        return cardLabel;
    }

    public void setCardLabel(Label cardLabel) {
        this.cardLabel = cardLabel;
    }

    public Cards getCard() {
        return card;
    }

    public void setCard(Cards card) {
        this.card = card;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
        this.cardLabel.setText(String.valueOf(available));
    }
}
