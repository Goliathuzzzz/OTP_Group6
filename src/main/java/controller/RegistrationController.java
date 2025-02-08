package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;

public class RegistrationController extends BaseController {

    UserController uController = new UserController();

    @FXML
    private TextField emailField, phoneField;

    @FXML
    private PasswordField passwordField, confirmPasswordField;

    @FXML
    private ImageView backIcon;

    @FXML
    private void handleSignUp() {
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "virhe", "täytä kaikki kentät!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "virhe", "salasanat eivät täsmää!");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "virhe", "sähköpostiosoite ei ole kelvollinen!");
            return;
        }

        if (!isValidPhone(phone)) {
            showAlert(Alert.AlertType.ERROR, "virhe", "puhelinnumero ei ole kelvollinen!");
            return;
        }

        //showAlert(Alert.AlertType.INFORMATION, "onnistunut", "tili luotu onnistuneesti!");
        switchScene("begin_session");
    }

    @FXML
    private void handleBack() {
        switchScene("options");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^[0-9]{10,15}$");
    }
}
