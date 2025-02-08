package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {

    @FXML
    private TextField emailField;

    @FXML
    private Label newAccount, forgotPassword;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "virhe", "täytä kaikki kentät!");
            return;
        }

        if (authenticateUser(email, password)) {
            //showAlert(Alert.AlertType.INFORMATION, "onnistunut", "kirjautuminen onnistui!");
            switchScene("begin_session");
        } else {
            showAlert(Alert.AlertType.ERROR, "virhe", "väärä sähköposti tai salasana.");
        }
    }

    @FXML
    private void handleForgotPassword() {
        showAlert(Alert.AlertType.INFORMATION, "salasana unohtunut?", "ohjeet salasanan palauttamiseen lähetetään sähköpostiisi.");
    }

    @FXML
    private void handleNewAccount() {
        //showAlert(Alert.AlertType.INFORMATION, "luo uusi tili", "uuden tilin luominen ei ole vielä käytössä.");
        switchScene("registration");
    }

    private boolean authenticateUser(String email, String password) {
        // lisää oikea käyttäjätunnistus
        return email.equals("ade@gmail.com") && password.equals("uwu");
    }
}

