package controller.view_controllers;

import static util.ProfilePictureUtil.getProfilePictureView;
import static util.SceneNames.EDIT_PROFILE;

import gui_context.LocaleManager;
import controller.BaseController;
import controller.UserController;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.SceneNames;

public class EditProfileController extends BaseController {

    private final ResourceBundle bundle = localeManager.getBundle();
    private UserController userController = new UserController();
    @FXML
    private ImageView profileImage, homeIcon, profileIcon, backIcon, helpIcon, backIcon1;
    @FXML
    private Label nameLabel, emailLabel, phoneLabel, routeToLang;
    @FXML
    private TextField nameField, emailField, phoneField;

    @FXML
    private void initialize() {
        handleLang();
        if (guiContext.isUser()) {
            nameField.setText(guiContext.getUserName());
            emailField.setText(guiContext.getUserEmail());
            phoneField.setText(guiContext.getUserPhoneNumber());

            ImageView profileImageView = getProfilePictureView(guiContext.getId(), 120, 120);
            profileImage.setImage(profileImageView.getImage());
            LanguageController.setPreviousScene(EDIT_PROFILE);

            Platform.runLater(() -> {
                Stage stage = (Stage) profileImage.getScene().getWindow();
                if (stage != null) {
                    stage.setTitle(bundle.getString("edit"));
                } else {
                    System.err.println("Stage is null in EditProfileController initialize()");
                }
            });
        } else {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"),
                    bundle.getString("no_user_data"));
            System.err.println("ERROR: No logged in user data found");
        }
    }

    private void saveChanges() {
        if (guiContext.isUser()) {
            guiContext.setUserName(nameField.getText());
            guiContext.setUserEmail(emailField.getText());
            guiContext.setUserPhoneNumber(phoneField.getText());
            userController.updateUser(guiContext.getUser());
        }
    }

    @FXML
    public void handlePreviousClick(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, bundle.getString("save_title"),
                bundle.getString("changes_saved"));
        saveChanges();
        switchScene(SceneNames.PROFILE);
    }

    @FXML
    public void handleHomeClick(MouseEvent event) {
        saveChanges();
        switchScene(SceneNames.BEGIN_SESSION);
    }

    @FXML
    public void handleProfileClick(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, bundle.getString("save_title"),
                bundle.getString("changes_saved"));
        saveChanges();
        switchScene(SceneNames.PROFILE);
    }

    @FXML
    public void handleBackClick(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, bundle.getString("save_title"),
                bundle.getString("changes_saved"));
        saveChanges();
        switchScene(SceneNames.OPTIONS);
    }

    private void handleLang() {
        LocaleManager localeManager = LocaleManager.getInstance();
        Locale locale = localeManager.getLocale();
        if ("ja".equals(locale.getLanguage())) {
            routeToLang.setLayoutX(200);
        } else if ("zh".equals(locale.getLanguage())) {
            routeToLang.setLayoutX(220);
        } else if ("en".equals(locale.getLanguage())) {
            routeToLang.setLayoutX(160);
        }
    }

    // For testing
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void routeToLang(MouseEvent event) {
        switchScene(SceneNames.LANGUAGE);
    }
}
