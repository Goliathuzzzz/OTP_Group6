package controller.view_controllers;

import context.GUIContext;
import context.LocaleManager;
import controller.BaseController;
import controller.UserController;
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

import java.util.Locale;
import java.util.ResourceBundle;

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
    private AnchorPane loginPane;

    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"), bundle.getString("fill_all_fields"));
            return;
        }
        User user = uController.login(email, password);
        if (user != null) {
            guiContext.setUser(user);
            switchScene(SceneNames.BEGIN_SESSION);
        } else {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"), bundle.getString("invalid_credentials"));
        }
    }

    @FXML
    public void initialize() {
        handleLang();
        Platform.runLater(() -> {
            Stage stage = (Stage) loginPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("login_title"));
            }
            else {
                System.err.println("Stage is null in LoginController initialize()");
            }
        });
    }

    @FXML
    private void handleForgotPassword() {
        showAlert(Alert.AlertType.INFORMATION, bundle.getString("forgot_password_title"), bundle.getString("forgot_password_instructions"));
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
        if (localeManager.getLocale().getLanguage().equals("ja")) {
            or.setLayoutX(175);
            forgotPassword.setLayoutX(130);
        }
        else if (localeManager.getLocale().getLanguage().equals("zh")) {
            or.setLayoutX(185);
            forgotPassword.setLayoutX(210);
        }
        else if (localeManager.getLocale().getLanguage().equals("en")) {
            forgotPassword.setLayoutX(210);
            newAccount.setLayoutX(195);
        }
    }
}

