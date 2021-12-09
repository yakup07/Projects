package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InstructionScreenController implements Initializable {
    String details = "1. As the game starts, there will be two windows one for a player one and second for player two.\n " +
            "2. On game window, click the roll and write button in right bottom corner, this will roll the all six dices.\n " +
            "3. As the dice buttons update with new values, click the dice button of your choice, this will add the button in selection buttons. \n" +
            "   You can see your selection buttons in top left corner, button will be clearly visible having same dice value and background.\n" +
            "4. After selecting specific dice, click on the area's button according to the dice you selected, this will update the current button. \n" +
            "   Please follow the rules and condition. \n" +
            "5. Same procedure is going on as you your round is over. Now its the second player turn. Hold the system to second player.\n" +
            "6. Repeat the above steps as you don't left with your rounds. \n" +
            "7. Stones will be automatically collected to your queue. You can use them without choosing the stone. \n"+
            "8. Re-roll and plus one stone will be used with specific round. \n" +
            "9. Follow and instructions and enjoy the game!\n";

    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArea.setText(details);
    }

    @FXML
    void onBackBtnClick(ActionEvent event){
        Parent parentScreen = null;
        try {
            parentScreen = FXMLLoader.load(getClass().getResource("welcomeScreen.fxml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Scene scene = new Scene(parentScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

}
