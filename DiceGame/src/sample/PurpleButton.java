package sample;


import javafx.scene.control.Button;

public class PurpleButton {
    private int value;
    private Cards card;
    private Button button;

    public PurpleButton(int value, Cards card,Button button) {
        this.value = value;
        this.card = card;
        this.button = button;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Cards getCard() {
        return card;
    }

    public void setCard(Cards card) {
        this.card = card;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setBtnValue(int value){
        setValue(value);
        this.button.setText(String.valueOf(value));
    }
}
