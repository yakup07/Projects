package sample;

import javafx.scene.control.Button;

public class GreenButton {
    private int position;
    private int condition;
    private int score;
    private Cards card;
    private Button button;

    public GreenButton(int position, int condition, Cards card, Button button,int score) {
        this.position = position;
        this.condition = condition;
        this.card = card;
        this.button = button;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "GreenButton{" +
                "position=" + position +
                ", condition=" + condition +
                ", card =" + card.toString() +
                '}';
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public Cards getCard() {
        return card;
    }

    public void setCard(Cards card) {
        this.card = card;
    }

    public void setBtnValue(String value){
        this.button.setText(value);
    }
}
