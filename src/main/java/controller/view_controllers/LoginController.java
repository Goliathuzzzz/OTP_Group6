package controller.view_controllers;

import context.GUIContext;
import context.LocaleManager;
import controller.BaseController;
import controller.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import util.SceneNames;

import java.util.Locale;

public class LoginController extends BaseController {

    UserController uController = new UserController();
    GUIContext guiContext = GUIContext.getInstance();

    @FXML
    private TextField emailField;

    @FXML
    private Label newAccount, forgotPassword, or;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "error_title", "fill_all_fields");
            return;
        }
        User user = uController.login(email, password);
        if (user != null) {
            guiContext.setUser(user);
            switchScene(SceneNames.BEGIN_SESSION);
        } else {
            showAlert(Alert.AlertType.ERROR, "error_title", "invalid_credentials");
        }
    }

    @FXML
    public void initialize() {
        handleLang();
    }

    @FXML
    private void handleForgotPassword() {
        showAlert(Alert.AlertType.INFORMATION, "forgot_password_title", "forgot_password_instructions");
    }

    @FXML
    private void handleNewAccount() {
        switchScene(SceneNames.REGISTRATION);
    }

    public UserController getUController() {
        return uController;
    }

    public void setUserController(UserController userController) {
        uController = userController;
    }

    private void handleLang() {
        LocaleManager localeManager = LocaleManager.getInstance();
        Locale locale = localeManager.getLocale();
        if (locale.getLanguage().equals("ja")) {
            or.setLayoutX(175);
            forgotPassword.setLayoutX(130);
        }
    }
}

