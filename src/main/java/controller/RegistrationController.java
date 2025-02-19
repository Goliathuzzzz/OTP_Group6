package controller;

import context.GUIContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import util.SceneNames;

import java.util.Date;

public class RegistrationController extends BaseController {

    UserController uController = new UserController();

    @FXML
    private AnchorPane registrationPane;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) registrationPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("rekisteröinti");
            }
        });
    }

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
        if (uController.existsByEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "virhe", "sähköpostiosoite on jo käytössä!");
            return;
        }
        String userName = email.substring(0, email.indexOf("@")).replace(".", " "); // can be improved
        User user = new User(userName, password, email, "none", phone, new Date());
        uController.registerUser(user);
        GUIContext.getInstance().setUser(user);
        switchScene(SceneNames.BEGIN_SESSION);
    }

    @FXML
    private void handleBack() {
        switchScene(SceneNames.OPTIONS);
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^[0-9]{10,15}$");
    }
}
