package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class OptionsController {

    @FXML
    private Button loginButton;

    @FXML
    private Button newUserButton;

    @FXML
    private Button guestButton;

    @FXML
    private void handleLogin() {
        showAlert("kirjautuminen", "siirrytään kirjautumiseen");
    }

    @FXML
    private void handleNewUser() {
        showAlert("uusi käyttäjä", "siirrytään luomaan tiliä");
    }

    @FXML
    private void handleGuest() {
        showAlert("vierailijana jatkaminen", "jatketaan vierailijana");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
