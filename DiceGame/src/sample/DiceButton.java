package sample;


import javafx.scene.control.Button;

public class DiceButton {
    private Button button;
    private DiceCategories diceCategories;

    public DiceButton(Button button, DiceCategories diceCategories) {
        this.button = button;
        this.diceCategories = diceCategories;
    }


    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public DiceCategories getDiceCategories() {
        return diceCategories;
    }

    public void setDiceCategories(DiceCategories diceCategories) {
        this.diceCategories = diceCategories;
    }
}
