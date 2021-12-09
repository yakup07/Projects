package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeScreenController implements Initializable {
    public static String firstName = null;
    public static String secondName = null;

    @FXML
    private TextField firstPlayerName;

    @FXML
    private TextField secondPlayerName;

    @FXML
    void onInsBtnClick(ActionEvent event) {
        Parent parentScreen = null;
        try {
            parentScreen = FXMLLoader.load(getClass().getResource("InstructionScreen.fxml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Scene scene = new Scene(parentScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void onScoreBtnClick(ActionEvent event) throws IOException {
        Parent rot = FXMLLoader.load(getClass().getResource("highScoreScreen.fxml"));
        Stage stag = new Stage();
        stag.setScene(new Scene(rot, 600, 400));
        stag.setTitle("High Scores");
        stag.show();
    }

    @FXML
    void onStartBtnClick(ActionEvent event) throws IOException {
        if(!firstPlayerName.getText().isEmpty() || !secondPlayerName.getText().isEmpty()){

            firstName = firstPlayerName.getText();
            secondName = secondPlayerName.getText();

            Parent root = FXMLLoader.load(getClass().getResource("GameScreen.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle(firstName + "'s Screen");
            stage.show();

            Parent rot = FXMLLoader.load(getClass().getResource("GameScreen.fxml"));
            Stage stag = new Stage();
            stag.setScene(new Scene(rot, 1000, 700));
            stag.setTitle(secondName + "'s Screen");
            stag.show();

        } else {
            DIALOG.showDialog("Fields shouldn't be empty","Fields required");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}