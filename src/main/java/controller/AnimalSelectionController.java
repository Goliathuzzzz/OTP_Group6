package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import util.SceneNames;

public class AnimalSelectionController extends BaseController {

    //toiminnallisuuksia jeejee
    @FXML
    private RadioButton koiratRadio, kissatRadio, hiiretRadio, hevosetRadio;

    @FXML
    private Button continueButton;

    private String selectedAnimal = "";

    @FXML
    private void handleHomeClick(MouseEvent event) {
        switchScene(SceneNames.BEGIN_SESSION);
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        switchScene(SceneNames.PROFILE);
    }

    // kirjaudu ulös
    @FXML
    private void handleBackClick(MouseEvent event) {
//        switchScene("");
    }

    //edelliselle sivulle
    @FXML
    private void handleBack() {
//        switchScene("");
    }

    @FXML
    private void handleContinue() {
        if (selectedAnimal.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "valitse", "valitse eläin.");
        }
    }
}
