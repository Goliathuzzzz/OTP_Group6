package controller.view_controllers;

import controller.BaseController;
import controller.UserController;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import util.SceneNames;

public class LoginController extends BaseController {

    private final ResourceBundle bundle = localeManager.getBundle();
    UserController userController = new UserController();

    @FXML
    private TextField emailField;
    @FXML
    private Label newAccount, forgotPassword, or;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private AnchorPane loginPane;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"),
                    bundle.getString("fill_all_fields"));
            return;
        }
        User user = userController.login(email, password);
        if (user != null) {
            guiContext.setUser(user);
            switchScene(SceneNames.BEGIN_SESSION);
        } else {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"),
                    bundle.getString("invalid_credentials"));
        }
    }

    @FXML
    public void initialize() {
        handleLang();
        Platform.runLater(() -> {
            Stage stage = (Stage) loginPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("login_title"));
            } else {
                System.err.println("Stage is null in LoginController initialize()");
            }
        });
    }

    @FXML
    private void handleForgotPassword() {
        showAlert(Alert.AlertType.INFORMATION, bundle.getString("forgot_password_title"),
                bundle.getString("forgot_password_instructions"));
    }

    @FXML
    private void handleNewAccount() {
        switchScene(SceneNames.REGISTRATION);
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    private void handleLang() {
        if ("ja".equals(localeManager.getLocale().getLanguage())) {
            or.setLayoutX(175);
            forgotPassword.setLayoutX(130);
        } else if ("zh".equals(localeManager.getLocale().getLanguage())) {
            or.setLayoutX(185);
            forgotPassword.setLayoutX(210);
        } else if ("en".equals(localeManager.getLocale().getLanguage())) {
            forgotPassword.setLayoutX(210);
            newAccount.setLayoutX(195);
        }
    }
}
