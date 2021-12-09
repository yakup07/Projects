package sample;

import javafx.scene.control.Button;

public class OrangeButton {
    private int value;
    private Cards card;
    private Button button;

    public OrangeButton(int value, Cards card, Button button) {
        this.value = value;
        this.card = card;
        this.button = button;
    }

    @Override
    public String toString() {
        return "OrangeButton{" +
                "value=" + value +
                ", card=" + card.toString() +
                '}';
    }

    public int getValue() {
        return value;
    }

    public Cards getCard() {
        return card;
    }

    public void setCard(Cards card) {
        this.card = card;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setBtnValue(int value){
        this.button.setText(String.valueOf(value));
    }
}
