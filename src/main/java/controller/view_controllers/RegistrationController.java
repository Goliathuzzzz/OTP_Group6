package controller.view_controllers;

import context.GUIContext;
import context.LocaleManager;
import controller.BaseController;
import controller.UserController;
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
import java.util.ResourceBundle;

public class RegistrationController extends BaseController {

    UserController uController = new UserController();
    GUIContext guiContext = GUIContext.getInstance();

    @FXML
    private AnchorPane registrationPane;

    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) registrationPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("registration"));
            }
            else {
                System.err.println("Stage is null in RegistrationController initialize()");
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
            showAlert(Alert.AlertType.ERROR, bundle.getString("error"), bundle.getString("fill_all_fields_alert"));
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error"), bundle.getString("mismatching_passwords_alert"));
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error"), bundle.getString("invalid_email_alert"));
            return;
        }

        if (!isValidPhone(phone)) {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error"), bundle.getString("invalid_phone_number_alert"));
            return;
        }
        if (uController.existsByEmail(email)) {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error"), bundle.getString("email_in_use_alert"));
            return;
        }
        String userName = email.substring(0, email.indexOf("@")).replace(".", " "); // can be improved
        User user = new User(userName, password, email, "user", phone, new Date(), guiContext.getLanguage());
        uController.registerUser(user);
        GUIContext.getInstance().setUser(user);
        switchScene(SceneNames.BEGIN_SESSION);
    }

    @FXML
    private void handleBack() {
        switchScene(SceneNames.OPTIONS);
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^[0-9]{10,15}$");
    }

    public UserController getUController() {
        return uController;
    }

    public void setuController(UserController uController) {
        this.uController = uController;
    }
}
