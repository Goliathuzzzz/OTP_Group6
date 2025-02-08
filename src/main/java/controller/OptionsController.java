package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class OptionsController extends BaseController {

    @FXML
    private void handleLogin() {
        //showAlert(Alert.AlertType.INFORMATION, "kirjautuminen", "siirrytään kirjautumiseen");
        switchScene("login");
    }

    @FXML
    private void handleNewUser() {
        //showAlert(Alert.AlertType.INFORMATION,"uusi käyttäjä", "siirrytään luomaan tiliä");
        switchScene("registration");
    }

    @FXML
    private void handleGuest() {
        //showAlert(Alert.AlertType.INFORMATION,"vierailijana jatkaminen", "jatketaan vierailijana");
        switchScene("guest");
    }
}
