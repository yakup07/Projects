package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import jdk.jshell.Diag;

import java.net.URL;
import java.util.*;

public class GameScreenController implements Initializable {

    private ArrayList<GreenButton> greenButtons;
    private ArrayList<OrangeButton> orangeButtons;
    private ArrayList<PurpleButton> purpleButtons;

    private int greenCount = 0;
    private int orangeCount = 0;
    private int purpleCount = 0;
    private int round = 1;

    private int score = 0;

    private ArrayList<Integer> scores = new ArrayList<>();
    private FileOperations operations = new FileOperations();

    private int yellowScore = 0;
    private int blueScore = 0;
    private int greenScore = 0;
    private int purpleScore = 0;
    private int orangeScore = 0;


    private Map<Integer, Integer> map = new HashMap<>();
    private ArrayList<Button> diceButtons = new ArrayList<>();
    private ArrayList<DiceButton> selectionButtons = new ArrayList<>();
    private ArrayList<DiceButton> yourButtons = new ArrayList<>();
    private ArrayList<Button> blueButtons = new ArrayList<>();

    private DiceButton purpleDiceBtn;
    private DiceButton whiteDiceBtn;
    private DiceButton blueDiceBtn;
    private DiceButton yellowDiceBtn;
    private DiceButton greenDiceBtn;
    private DiceButton orangeDiceBtn;

    private Card blueCardX;
    private Card greenCardX;
    private Card foxCard;
    private Card yellowCardX;
    private Card plusOneCard;
    private Card reRollCard;
    private Card orangeCard4;
    private Card orangeCard5;
    private Card purpleCard6;
    private Card orangeCard6;

    private ArrayList<Card> availableCards = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 4);
        map.put(4, 7);
        map.put(5, 11);
        map.put(6, 16);
        map.put(7, 22);
        map.put(8, 29);
        map.put(9, 37);
        map.put(10, 46);
        map.put(11, 56);

        init();
        settingGreenArea();
        settingOrangeArea();
        settingPurpleArea();
    }

    private void calculateScore() {

        for (GreenButton greenButton : greenButtons) {
            if (greenButton != null) {
                if (greenButton.getButton().getText().equals("X")) {
                    greenScore += greenButton.getScore();
                }
            }
        }

        int bCount = 0;
        for (Button button : blueButtons) {
            if (button.getText().equals("X")) {
                bCount++;
            }
        }

        if (bCount != 0) {
            blueScore = map.get(bCount);
        }

        scores.add(blueScore);
        scores.add(yellowScore);
        scores.add(orangeScore);
        scores.add(greenScore);
        scores.add(purpleScore);

        score += getMin(scores) * foxCard.getAvailable();
        score += getSum();

        ArrayList<Integer> integers = operations.readFile();

        if(integers.size() >= 5){
            int min = getMin(scores);
            integers.remove(min);
        }

        integers.add(score);
        operations.writeFile(integers);
    }

    private int getMin(ArrayList<Integer> scores){
        Integer min = scores.get(0);
        for(Integer i : scores){
            if(i < min){
                min = i;
            }
        }

        return min;
    }

    private int getSum(){
        int sum = 0;
        for(Integer i : scores){
            sum += i;
        }
        return sum;
    }

    private void init() {
        diceButtons.add(yellowDice);
        diceButtons.add(blueDice);
        diceButtons.add(whiteDice);
        diceButtons.add(orangeDice);
        diceButtons.add(purpleDice);
        diceButtons.add(greenDice);

        purpleDiceBtn = new DiceButton(purpleDice, DiceCategories.purple);
        orangeDiceBtn = new DiceButton(orangeDice, DiceCategories.orange);
        whiteDiceBtn = new DiceButton(purpleDice, DiceCategories.white);
        blueDiceBtn = new DiceButton(orangeDice, DiceCategories.blue);
        greenDiceBtn = new DiceButton(purpleDice, DiceCategories.green);
        yellowDiceBtn = new DiceButton(orangeDice, DiceCategories.yellow);

        yourButtons.add(purpleDiceBtn);
        yourButtons.add(whiteDiceBtn);
        yourButtons.add(yellowDiceBtn);
        yourButtons.add(blueDiceBtn);
        yourButtons.add(greenDiceBtn);
        yourButtons.add(orangeDiceBtn);

        selectionButtons.add(new DiceButton(selectionBtn1, null));
        selectionButtons.add(new DiceButton(selectionBtn2, null));
        selectionButtons.add(new DiceButton(selectionBtn3, null));

        blueButtons.add(nBtnB1);
        blueButtons.add(nBtnB2);
        blueButtons.add(nBtnB3);
        blueButtons.add(nBtnB4);
        blueButtons.add(nBtnB5);
        blueButtons.add(nBtnB6);
        blueButtons.add(nBtnB7);
        blueButtons.add(nBtnB8);
        blueButtons.add(nBtnB9);
        blueButtons.add(nBtnB10);
        blueButtons.add(nBtnB11);


        initCards();
    }

    private boolean triesLeft() {
        for (DiceButton diceButton : selectionButtons) {
            if (diceButton.getButton().getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void nextRound() {
        for (DiceButton button : selectionButtons) {
            button.getButton().setText("");
            button.getButton().setBackground(Background.EMPTY);
        }

        for (Button button : diceButtons) {
            button.setDisable(false);
            button.setText("0");
        }

        round++;
        roundLabel.setText(String.valueOf(round));

        if(round == 2){
            plusOneCard.setAvailable(plusOneCard.getAvailable() + 1);
        }

        if(round == 2){
            reRollCard.setAvailable(reRollCard.getAvailable() + 1);
        }

        if(round == 2){
            yellowCardX.setAvailable(yellowCardX.getAvailable() + 1);
        }


        if (round == 4) {
            calculateScore();
            DIALOG.showDialog("Game has been finished. Thanks for your time!", "Game Ended");
            Stage stage = (Stage) rollButton.getScene().getWindow();
            stage.close();
        }

    }

    private void incrementingCard(Cards card) {
        for (Card crd : availableCards) {
            if (crd.getCard().equals(card)) {
                crd.setAvailable(crd.getAvailable() + 1);
            }
        }
    }

    // purple buttons

    private void setPurpleButtonValue() {
        if (buttonExists(purpleDice, DiceCategories.purple.toString()) ) {
            PurpleButton purpleButton = purpleButtons.get(purpleCount);
            int value = Integer.parseInt(purpleDice.getText());
            if (value > purpleButtons.get(purpleCount - 1).getValue() || purpleButtons.get(purpleCount - 1).getValue() == 6) {
                purpleScore  += value;
                purpleButton.setBtnValue(value);
                purpleCount++;

                if (purpleButton.getCard() != null) {
                    incrementingCard(purpleButton.getCard());
                }
            }
        } else {

            if (purpleCard6.getAvailable() > 0) {
                PurpleButton purpleButton = purpleButtons.get(purpleCount);
                purpleCard6.setAvailable(purpleCard6.getAvailable() - 1);
                purpleButton.setBtnValue(6);
                purpleScore += 6;
                purpleCount++;
            }

        }

    }

    @FXML
    void onNBtnP1Click(ActionEvent event) {
        if (buttonExists(purpleDice, DiceCategories.purple.toString())) {
            PurpleButton purpleButton = purpleButtons.get(0);
            int value = Integer.parseInt(purpleDice.getText());
            purpleScore  += value;
            purpleButton.setBtnValue(value);
            purpleCount++;
        }
    }

    @FXML
    void onNBtnP2Click(ActionEvent event) {
        setPurpleButtonValue();
    }

    @FXML
    void onNBtnP3Click(ActionEvent event) {
        setPurpleButtonValue();
    }

    @FXML
    void onNBtnP4Click(ActionEvent event) {
        setPurpleButtonValue();
    }

    @FXML
    void onNBtnP5Click(ActionEvent event) {
        setPurpleButtonValue();
    }

    @FXML
    void onNBtnP6Click(ActionEvent event) {
        setPurpleButtonValue();
    }

    @FXML
    void onNBtnP7Click(ActionEvent event) {
        setPurpleButtonValue();
    }

    @FXML
    void onNBtnP8Click(ActionEvent event) {
        setPurpleButtonValue();
    }

    @FXML
    void onNBtnP9Click(ActionEvent event) {
        setPurpleButtonValue();
    }

    @FXML
    void onNBtnP10Click(ActionEvent event) {
        setPurpleButtonValue();
    }

    @FXML
    void onNBtnP11Click(ActionEvent event) {
        setPurpleButtonValue();
    }

    // orange buttons

    private void setOrangeButtonValue(Button button) {
        if (buttonExists(orangeDice, DiceCategories.orange.toString()) ) {
            OrangeButton orangeButton = orangeButtons.get(orangeCount);
            if (orangeButton.getValue() == 0) {
                orangeScore +=  Integer.parseInt(orangeDice.getText());
                button.setText(orangeDice.getText());
            } else {
                int value = orangeButton.getValue() * Integer.parseInt(orangeDice.getText().toString());
                this.orangeScore += value;
                button.setText(String.valueOf(value));
            }
            orangeCount++;
            if (orangeButton.getCard() != null) {
                incrementingCard(orangeButton.getCard());
            }
        } else {

            if (orangeCard4.getAvailable() > 0) {
                OrangeButton orangeButton = orangeButtons.get(orangeCount);
                orangeCard4.setAvailable(orangeCard4.getAvailable() - 1);
                orangeButton.setBtnValue(4);
                this.orangeScore += 4;
            }

            if (orangeCard5.getAvailable() > 0) {
                OrangeButton orangeButton = orangeButtons.get(orangeCount);
                orangeCard5.setAvailable(orangeCard5.getAvailable() - 1);
                orangeButton.setBtnValue(5);
                this.orangeScore += 5;
            }


            if (orangeCard6.getAvailable() > 0) {
                OrangeButton orangeButton = orangeButtons.get(orangeCount);
                orangeCard6.setAvailable(orangeCard6.getAvailable() - 1);
                orangeButton.setBtnValue(6);
                this.orangeScore += 6;
            }

            orangeCount++;

        }
    }

    @FXML
    void onNBtnO1Click(ActionEvent event) {
        setOrangeButtonValue(nBtnO1);
    }

    @FXML
    void onNBtnO2Click(ActionEvent event) {
        setOrangeButtonValue(nBtnO2);
    }

    @FXML
    void onNBtnO3Click(ActionEvent event) {
        setOrangeButtonValue(nBtnO3);
    }

    @FXML
    void onNBtnO4Click(ActionEvent event) {
        setOrangeButtonValue(nBtnO4);
    }

    @FXML
    void onNBtnO5Click(ActionEvent event) {
        setOrangeButtonValue(nBtnO5);
    }

    @FXML
    void onNBtnO6Click(ActionEvent event) {
        setOrangeButtonValue(nBtnO6);
    }

    @FXML
    void onNBtnO7Click(ActionEvent event) {
        setOrangeButtonValue(nBtnO7);
    }

    @FXML
    void onNBtnO8Click(ActionEvent event) {
        setOrangeButtonValue(nBtnO8);
    }

    @FXML
    void onNBtnO9Click(ActionEvent event) {
        setOrangeButtonValue(nBtnO9);
    }

    @FXML
    void onNBtnO10Click(ActionEvent event) {
        setOrangeButtonValue(nBtnO10);
    }

    @FXML
    void onNBtnO11Click(ActionEvent event) {
        setOrangeButtonValue(nBtnO11);
    }

    // green buttons

    private void setGreenButtonValue() {
        GreenButton greenButton = greenButtons.get(greenCount);
        if (greenButton.getPosition() - 1 == greenCount && buttonExists(greenDice, DiceCategories.green.toString())) {
            if (Integer.parseInt(greenDice.getText()) > greenButton.getCondition()) {
                greenButton.setBtnValue("X");
                greenCount++;

                if (greenButton.getCard() != null) {
                    incrementingCard(greenButton.getCard());
                }
            }
        } else {

            if (greenCardX.getAvailable() > 0) {
                greenCardX.setAvailable(greenCardX.getAvailable() - 1);
                greenButton.setBtnValue("X");
            }

        }
    }

    @FXML
    void onConBtn1Click(ActionEvent event) {
        setGreenButtonValue();
    }

    @FXML
    void onConBtn2Click(ActionEvent event) {
        setGreenButtonValue();
    }

    @FXML
    void onConBtn3Click(ActionEvent event) {
        setGreenButtonValue();
    }

    @FXML
    void onConBtn4Click(ActionEvent event) {
        setGreenButtonValue();
    }

    @FXML
    void onConBtn5Click(ActionEvent event) {
        setGreenButtonValue();
    }

    @FXML
    void onConBtn6Click(ActionEvent event) {
        setGreenButtonValue();
    }

    @FXML
    void onConBtn7Click(ActionEvent event) {
        setGreenButtonValue();
    }

    @FXML
    void onConBtn8Click(ActionEvent event) {
        setGreenButtonValue();
    }

    @FXML
    void onConBtn9Click(ActionEvent event) {
        setGreenButtonValue();
    }

    @FXML
    void onConBtn10Click(ActionEvent event) {
        setGreenButtonValue();
    }

    @FXML
    void onConBtn11Click(ActionEvent event) {
        setGreenButtonValue();
    }

    private void randomizeValues() {
        Random random = new Random();
        for (int i = 0; i < diceButtons.size(); i++) {
            Button button = diceButtons.get(i);
            if (!button.isDisable()) {
                button.setText(String.valueOf((random.nextInt(6) + 1)));
            }
        }
    }

    @FXML
    void onRollButtonClick(ActionEvent event) {
        if (triesLeft()) {
            randomizeValues();
        } else if (reRollCard.getAvailable() > 0) {

            for (Button button : diceButtons) {
                button.setDisable(false);
                button.setText("0");
            }

            randomizeValues();
            reRollCard.setAvailable(reRollCard.getAvailable() - 1);
        } else if (plusOneCard.getAvailable() > 0) {
            for (DiceButton button : selectionButtons) {
                button.getButton().setText("");
                button.getButton().setBackground(Background.EMPTY);
            }

            for (Button button : diceButtons) {
                button.setDisable(false);
                button.setText("0");
            }
            randomizeValues();
            plusOneCard.setAvailable(plusOneCard.getAvailable() - 1);
        } else {
            if(round != 4){
                nextRound();
                DIALOG.showDialog("Next round is started. Now you can play game", "No more tries left!!");
            }
        }
    }

    @FXML
    void onBlueDiceClick(ActionEvent event) {
        if (triesLeft()) {
            for (DiceButton button : selectionButtons) {
                if (button.getButton().getText().isEmpty()) {
                    button.getButton().setText(blueDice.getText());
                    button.getButton().setBackground(blueDice.getBackground());
                    button.getButton().setFont(blueDice.getFont());
                    button.setDiceCategories(DiceCategories.blue);
                    blueDice.setDisable(true);
                    break;
                }
            }
        } else {
            nextRound();
            DIALOG.showDialog("Wait for next round!!", "No more tries left!!");
        }
    }

    @FXML
    void onWhiteDiceClick(ActionEvent event) {
        if (triesLeft()) {
            for (DiceButton button : selectionButtons) {
                if (button.getButton().getText().isEmpty()) {
                    button.getButton().setText(whiteDice.getText());
                    button.getButton().setBackground(whiteDice.getBackground());
                    button.getButton().setFont(whiteDice.getFont());
                    button.setDiceCategories(DiceCategories.white);
                    whiteDice.setDisable(true);
                    break;
                }
            }
        } else {
            nextRound();
            DIALOG.showDialog("Wait for next round!!", "No more tries left!!");
        }
    }

    @FXML
    void onPurpleDiceClick(ActionEvent event) {
        if (triesLeft()) {
            for (DiceButton button : selectionButtons) {
                if (button.getButton().getText().isEmpty()) {
                    button.getButton().setText(purpleDice.getText());
                    button.getButton().setBackground(purpleDice.getBackground());
                    button.getButton().setFont(purpleDice.getFont());
                    button.setDiceCategories(DiceCategories.purple);
                    purpleDice.setDisable(true);
                    break;
                }
            }
        } else {
            nextRound();
            DIALOG.showDialog("Wait for next round!!", "No more tries left!!");
        }
    }

    @FXML
    void onYellowDiceClick(ActionEvent event) {
        if (triesLeft()) {
            for (DiceButton button : selectionButtons) {
                if (button.getButton().getText().isEmpty()) {
                    button.getButton().setText(yellowDice.getText());
                    button.getButton().setBackground(yellowDice.getBackground());
                    button.getButton().setFont(yellowDice.getFont());
                    button.setDiceCategories(DiceCategories.yellow);
                    yellowDice.setDisable(true);

                    break;
                }
            }
        } else {
            nextRound();
            DIALOG.showDialog("Wait for next round!!", "No more tries left!!");
        }
    }

    @FXML
    void onOrangeDiceClick(ActionEvent event) {
        if (triesLeft()) {
            for (DiceButton button : selectionButtons) {
                if (button.getButton().getText().isEmpty()) {
                    button.getButton().setText(orangeDice.getText());
                    button.getButton().setBackground(orangeDice.getBackground());
                    button.getButton().setFont(orangeDice.getFont());
                    button.setDiceCategories(DiceCategories.orange);
                    orangeDice.setDisable(true);
                    break;
                }
            }
        } else {
            nextRound();
            DIALOG.showDialog("Wait for next round!!", "No more tries left!!");
        }
    }

    @FXML
    void onGreenDiceClick(ActionEvent event) {
        if (triesLeft()) {
            for (DiceButton button : selectionButtons) {
                if (button.getButton().getText().isEmpty()) {
                    button.getButton().setText(greenDice.getText());
                    button.getButton().setBackground(greenDice.getBackground());
                    button.getButton().setFont(greenDice.getFont());
                    button.setDiceCategories(DiceCategories.green);
                    greenDice.setDisable(true);
                    break;
                }
            }
        } else {
            nextRound();
            DIALOG.showDialog("Wait for next round!!", "No more tries left!!");
        }
    }

    // blue buttons

    @FXML
    void onBBtn1Click(ActionEvent event) {
        setBlueButtonValue(nBtnB1);
    }

    @FXML
    void onBBtn2Click(ActionEvent event) {
        setBlueButtonValue(nBtnB2);
    }

    @FXML
    void onBBtn3Click(ActionEvent event) {
        setBlueButtonValue(nBtnB3);
    }

    @FXML
    void onBBtn4Click(ActionEvent event) {
        setBlueButtonValue(nBtnB4);
    }

    @FXML
    void onBBtn5Click(ActionEvent event) {
        setBlueButtonValue(nBtnB5);
    }

    @FXML
    void onBBtn6Click(ActionEvent event) {
        setBlueButtonValue(nBtnB6);
    }

    @FXML
    void onBBtn7Click(ActionEvent event) {
        setBlueButtonValue(nBtnB7);
    }

    @FXML
    void onBBtn8Click(ActionEvent event) {
        setBlueButtonValue(nBtnB8);
    }

    @FXML
    void onBBtn9Click(ActionEvent event) {
        setBlueButtonValue(nBtnB9);
    }

    @FXML
    void onBBtn10Click(ActionEvent event) {
        setBlueButtonValue(nBtnB10);
    }

    @FXML
    void onBBtn11Click(ActionEvent event) {
        setBlueButtonValue(nBtnB11);
    }

    private void setBlueButtonValue(Button button) {
        if (buttonExists(blueDice, DiceCategories.blue.toString()) || buttonExists(whiteDice, DiceCategories.white.toString())) {
            int bValue = Integer.parseInt(blueDice.getText());
            int wValue = Integer.parseInt(whiteDice.getText());

            int buttonValue = Integer.parseInt(button.getText());

            if (buttonValue == (bValue + wValue)) {
                button.setText("X");
            }
        } else {
            if (blueCardX.getAvailable() > 0) {
                blueCardX.setAvailable(blueCardX.getAvailable() - 1);
                button.setText("X");
            }
        }

        checkingBCardBonus();
        checkingBBonus();
    }

    // yellow buttons
    @FXML
    void onYBtn1Click(ActionEvent event) {
        setYellowButtonValue(nBtnY1);
    }

    @FXML
    void onYBtn2Click(ActionEvent event) {
        setYellowButtonValue(nBtnY2);
    }

    @FXML
    void onYBtn3Click(ActionEvent event) {
        setYellowButtonValue(nBtnY3);
    }

    @FXML
    void onYBtn4Click(ActionEvent event) {
        setYellowButtonValue(nBtnY4);
    }

    @FXML
    void onYBtn5Click(ActionEvent event) {
        setYellowButtonValue(nBtnY5);
    }

    @FXML
    void onYBtn6Click(ActionEvent event) {
        setYellowButtonValue(nBtnY6);
    }

    @FXML
    void onYBtn7Click(ActionEvent event) {
        setYellowButtonValue(nBtnY7);
    }

    @FXML
    void onYBtn8Click(ActionEvent event) {
        setYellowButtonValue(nBtnY8);
    }

    @FXML
    void onYBtn9Click(ActionEvent event) {
        setYellowButtonValue(nBtnY9);
    }

    @FXML
    void onYBtn10Click(ActionEvent event) {
        setYellowButtonValue(nBtnY10);
    }

    @FXML
    void onYBtn11Click(ActionEvent event) {
        setYellowButtonValue(nBtnY11);
    }

    @FXML
    void onYBtn12Click(ActionEvent event) {
        setYellowButtonValue(nBtnY12);
    }

    private void setYellowButtonValue(Button button) {
        if (buttonExists(button, DiceCategories.yellow.toString())) {
            button.setText("X");
        }

        if (yellowCardX.getAvailable() > 0) {
            yellowCardX.setAvailable(yellowCardX.getAvailable() - 1);
            button.setText("X");
        }


        checkingYCardBonus();
        if (checkingYNoBonus() != 0) {
            int i = checkingYNoBonus();
            this.yellowScore += i;
        }
    }

    // validations
    private boolean buttonExists(Button button, String category) {
        for (DiceButton b : selectionButtons) {
            if (button.getText().equals(b.getButton().getText()) && b.getDiceCategories().toString().equals(category)) {
                return true;
            }
        }
        return false;
    }

    // setting arraylists functions
    private void settingOrangeArea() {
        orangeButtons = new ArrayList<>();
        OrangeButton orangeButton = new OrangeButton(0, null, nBtnO1);
        orangeButtons.add(orangeButton);

        orangeButton = new OrangeButton(0, null, nBtnO2);
        orangeButtons.add(orangeButton);

        orangeButton = new OrangeButton(0, Cards.reRollCard, nBtnO3);
        orangeButtons.add(orangeButton);

        orangeButton = new OrangeButton(2, null, nBtnO4);
        orangeButtons.add(orangeButton);

        orangeButton = new OrangeButton(0, Cards.yellowCardX, nBtnO5);
        orangeButtons.add(orangeButton);

        orangeButton = new OrangeButton(0, Cards.plusOneCard, nBtnO6);
        orangeButtons.add(orangeButton);

        orangeButton = new OrangeButton(2, null, nBtnO7);
        orangeButtons.add(orangeButton);

        orangeButton = new OrangeButton(0, Cards.foxCard, nBtnO8);
        orangeButtons.add(orangeButton);

        orangeButton = new OrangeButton(2, null, nBtnO9);
        orangeButtons.add(orangeButton);

        orangeButton = new OrangeButton(0, Cards.orangeCard6, nBtnO10);
        orangeButtons.add(orangeButton);

        orangeButton = new OrangeButton(3, null, nBtnO11);
        orangeButtons.add(orangeButton);
    }

    private void settingGreenArea() {
        greenButtons = new ArrayList<>();
        GreenButton greenBtn = new GreenButton(1, 1, null, conBtnG1, 1);
        greenButtons.add(greenBtn);
        greenBtn = new GreenButton(2, 2, null, conBtnG2, 3);
        greenButtons.add(greenBtn);
        greenBtn = new GreenButton(3, 3, null, conBtnG3, 6);
        greenButtons.add(greenBtn);
        greenBtn = new GreenButton(4, 4, Cards.plusOneCard, conBtnG4, 10);
        greenButtons.add(greenBtn);
        greenBtn = new GreenButton(5, 5, null, conBtnG5, 15);
        greenButtons.add(greenBtn);
        greenBtn = new GreenButton(6, 1, Cards.blueCardX, conBtnG6, 21);
        greenButtons.add(greenBtn);
        greenBtn = new GreenButton(7, 2, Cards.foxCard, conBtnG7, 28);
        greenButtons.add(greenBtn);
        greenBtn = new GreenButton(8, 3, null, conBtnG8, 36);
        greenButtons.add(greenBtn);
        greenBtn = new GreenButton(9, 4, Cards.purpleCard6, conBtnG9, 45);
        greenButtons.add(greenBtn);
        greenBtn = new GreenButton(10, 5, Cards.reRollCard, conBtnG10, 55);
        greenButtons.add(greenBtn);
        greenBtn = new GreenButton(11, 6, null, conBtnG11, 66);
        greenButtons.add(greenBtn);
    }

    private void settingPurpleArea() {
        purpleButtons = new ArrayList<>();

        PurpleButton purpleButton = new PurpleButton(0, null, nBtnP1);
        purpleButtons.add(purpleButton);

        purpleButton = new PurpleButton(0, null, nBtnP2);
        purpleButtons.add(purpleButton);

        purpleButton = new PurpleButton(0, Cards.reRollCard, nBtnP3);
        purpleButtons.add(purpleButton);

        purpleButton = new PurpleButton(0, Cards.blueCardX, nBtnP4);
        purpleButtons.add(purpleButton);

        purpleButton = new PurpleButton(0, Cards.plusOneCard, nBtnP5);
        purpleButtons.add(purpleButton);

        purpleButton = new PurpleButton(0, Cards.yellowCardX, nBtnP6);
        purpleButtons.add(purpleButton);

        purpleButton = new PurpleButton(0, Cards.foxCard, nBtnP7);
        purpleButtons.add(purpleButton);

        purpleButton = new PurpleButton(0, Cards.reRollCard, nBtnP8);
        purpleButtons.add(purpleButton);

        purpleButton = new PurpleButton(0, Cards.greenCardX, nBtnP9);
        purpleButtons.add(purpleButton);

        purpleButton = new PurpleButton(0, Cards.orangeCard6, nBtnP10);
        purpleButtons.add(purpleButton);

        purpleButton = new PurpleButton(0, Cards.plusOneCard, nBtnP11);
        purpleButtons.add(purpleButton);
    }

    private void initCards() {

        blueCardX = new Card(Cards.blueCardX, 0, blueCardXL);
        orangeCard4 = new Card(Cards.orangeCard4, 0, orangeCard4L);
        orangeCard5 = new Card(Cards.orangeCard5, 0, orangeCard5L);
        orangeCard6 = new Card(Cards.orangeCard6, 0, orangeCard6L);
        greenCardX = new Card(Cards.greenCardX, 0, greenCardXL);
        foxCard = new Card(Cards.foxCard, 0, foxCardL);
        yellowCardX = new Card(Cards.yellowCardX, 0, yellowCardXL);
        plusOneCard = new Card(Cards.plusOneCard, 0, plusOneCardL);
        reRollCard = new Card(Cards.reRollCard, 1, reRollCardL);
        purpleCard6 = new Card(Cards.blueCardX, 0, blueCardXL);

        availableCards.add(blueCardX);
        availableCards.add(orangeCard4);
        availableCards.add(orangeCard5);
        availableCards.add(orangeCard6);
        availableCards.add(greenCardX);
        availableCards.add(foxCard);
        availableCards.add(yellowCardX);
        availableCards.add(plusOneCard);
        availableCards.add(reRollCard);
        availableCards.add(purpleCard6);
    }

    private void checkingBCardBonus() {

        if (isFilled(nBtnB1) && isFilled(nBtnB2) && isFilled(nBtnB3) && !verB1) {
            orangeCard5.setAvailable(orangeCard5.getAvailable() + 1);
            verB1 = true;

        } else if (isFilled(nBtnB4) && isFilled(nBtnB5) && isFilled(nBtnB6) && isFilled(nBtnB7) && !verB2) {
            yellowCardX.setAvailable(yellowCardX.getAvailable() + 1);
            verB2 = true;

        } else if (isFilled(nBtnY8) && isFilled(nBtnY9) && isFilled(nBtnY10) && isFilled(nBtnY11) && !verB3) {
            foxCard.setAvailable(foxCard.getAvailable() + 1);
            verB3 = true;
        }
    }

    private void checkingBBonus() {
        if (isFilled(nBtnB1) && isFilled(nBtnB5) && isFilled(nBtnB9) && !horB1) {
            greenCardX.setAvailable(greenCardX.getAvailable() + 1);
            horB1 = true;
        } else if (isFilled(nBtnB2) && isFilled(nBtnB6) && isFilled(nBtnB10) && !horB2) {
            purpleCard6.setAvailable(purpleCard6.getAvailable() + 1);
            horB2 = true;
        } else if (isFilled(nBtnB3) && isFilled(nBtnB7) && isFilled(nBtnB11) && !horB3) {
            plusOneCard.setAvailable(plusOneCard.getAvailable() + 1);
            horB3 = true;
        } else if (isFilled(nBtnB4) && isFilled(nBtnB8) && !horB4) {
            reRollCard.setAvailable(reRollCard.getAvailable() + 1);
            horB4 = true;
        }
    }


    private void checkingYCardBonus() {

        if (isFilled(nBtnY1) && isFilled(nBtnY2) && isFilled(nBtnY3) && !verY1) {
            blueCardX.setAvailable(blueCardX.getAvailable() + 1);
            verY1 = true;

        } else if (isFilled(nBtnY4) && isFilled(nBtnY5) && isFilled(nBtnY6) && !verY2) {
            orangeCard4.setAvailable(orangeCard4.getAvailable() + 1);
            verY2 = true;

        } else if (isFilled(nBtnY7) && isFilled(nBtnY8) && isFilled(nBtnY9) && !verY3) {
            greenCardX.setAvailable(greenCardX.getAvailable() + 1);
            verY3 = true;

        } else if (isFilled(nBtnY10) && isFilled(nBtnY11) && isFilled(nBtnY12) && !verY4) {
            foxCard.setAvailable(foxCard.getAvailable() + 1);
            verY4 = true;

        } else if (isFilled(nBtnY1) && isFilled(nBtnY5) && isFilled(nBtnY8) && isFilled(nBtnY12) && !diaY) {
            plusOneCard.setAvailable(plusOneCard.getAvailable() + 1);
            diaY = true;
        }
    }

    private int checkingYNoBonus() {
        if (isFilled(nBtnY1) && isFilled(nBtnY4) && isFilled(nBtnY7) && !horY1) {
            horY1 = true;
            return 10;
        } else if (isFilled(nBtnY2) && isFilled(nBtnY5) && isFilled(nBtnY10) && !horY2) {
            horY2 = true;
            return 14;
        } else if (isFilled(nBtnY3) && isFilled(nBtnY8) && isFilled(nBtnY11) && !horY3) {
            horY3 = true;
            return 16;
        } else if (isFilled(nBtnY6) && isFilled(nBtnY9) && isFilled(nBtnY12) && !horY4) {
            horY4 = true;
            return 20;
        } else {
            return 0;
        }
    }

    private boolean isFilled(Button btn) {
        if (btn.getText().equals("X")) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private Label blueCardXL;

    @FXML
    private Label orangeCard4L;

    @FXML
    private Label greenCardXL;

    @FXML
    private Label orangeCard6L;

    @FXML
    private Label foxCardL;

    @FXML
    private Label orangeCard5L;

    @FXML
    private Label plusOneCardL;

    @FXML
    private Label purpleCardL;

    @FXML
    private Label reRollCardL;

    @FXML
    private Label yellowCardXL;

    // declarations

    private boolean verY1 = false;
    private boolean verY2 = false;
    private boolean verY3 = false;
    private boolean verY4 = false;
    private boolean verB1 = false;
    private boolean verB2 = false;
    private boolean verB3 = false;
    private boolean diaY = false;

    private boolean horY1 = false;
    private boolean horY2 = false;
    private boolean horY3 = false;
    private boolean horY4 = false;
    private boolean horB1 = false;
    private boolean horB2 = false;
    private boolean horB3 = false;
    private boolean horB4 = false;

    @FXML
    private Button nBtnO1;
    @FXML
    private Button nBtnO2;
    @FXML
    private Button nBtnO3;
    @FXML
    private Button nBtnO4;
    @FXML
    private Button nBtnO5;
    @FXML
    private Button nBtnO6;
    @FXML
    private Button nBtnO7;
    @FXML
    private Button nBtnO8;
    @FXML
    private Button nBtnO9;
    @FXML
    private Button nBtnO10;
    @FXML
    private Button nBtnO11;
    @FXML
    private Button nBtnP1;
    @FXML
    private Button nBtnP2;
    @FXML
    private Button nBtnP3;
    @FXML
    private Button nBtnP4;
    @FXML
    private Button nBtnP5;
    @FXML
    private Button nBtnP6;
    @FXML
    private Button nBtnP7;
    @FXML
    private Button nBtnP8;
    @FXML
    private Button nBtnP9;
    @FXML
    private Button nBtnP10;
    @FXML
    private Button nBtnP11;
    @FXML
    private Button infoBtnY;
    @FXML
    private Button nBtnY2;
    @FXML
    private Button nBtnY3;
    @FXML
    private Button nBtnY1;
    @FXML
    private Button nBtnY5;
    @FXML
    private Button nBtnY4;
    @FXML
    private Button nBtnY6;
    @FXML
    private Button nBtnY8;
    @FXML
    private Button nBtnY7;
    @FXML
    private Button nBtnY9;
    @FXML
    private Button nBtnY10;
    @FXML
    private Button nBtnY11;
    @FXML
    private Button nBtnY12;
    @FXML
    private Button blueDice;
    @FXML
    private Button orangeDice;
    @FXML
    private Button greenDice;
    @FXML
    private Button yellowDice;
    @FXML
    private Button purpleDice;
    @FXML
    private Button whiteDice;
    @FXML
    private Button rollButton;
    @FXML
    private Button selectionBtn1;
    @FXML
    private Button selectionBtn2;
    @FXML
    private Button selectionBtn3;
    @FXML
    private Button nBtnB1;
    @FXML
    private Button nBtnB2;
    @FXML
    private Button nBtnB3;
    @FXML
    private Button nBtnB4;
    @FXML
    private Button nBtnB5;
    @FXML
    private Button nBtnB6;
    @FXML
    private Button nBtnB7;
    @FXML
    private Button nBtnB8;
    @FXML
    private Button nBtnB9;
    @FXML
    private Button nBtnB10;
    @FXML
    private Button nBtnB11;
    @FXML
    private Button conBtnG1;
    @FXML
    private Button conBtnG2;
    @FXML
    private Button conBtnG3;
    @FXML
    private Button conBtnG4;
    @FXML
    private Button conBtnG5;
    @FXML
    private Button conBtnG6;
    @FXML
    private Button conBtnG7;
    @FXML
    private Button conBtnG8;
    @FXML
    private Button conBtnG9;
    @FXML
    private Button conBtnG10;
    @FXML
    private Button conBtnG11;
    @FXML
    private Label roundLabel;
}