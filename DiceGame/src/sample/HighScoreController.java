package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HighScoreController implements Initializable {

    FileOperations fileOperations = new FileOperations();
    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Integer> integers = fileOperations.readFile();
        for(Integer i : integers){
            textArea.appendText("High Score : " + i + "\n");
        }
    }

    @FXML
    void onBackBtnClick(ActionEvent event){
        Stage stage = (Stage) textArea.getScene().getWindow();
        stage.close();
    }
}
