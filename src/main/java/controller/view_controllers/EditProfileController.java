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
import javafx.stage.Stage;
import util.SceneNames;

public class EditProfileController extends BaseController {

    private final ResourceBundle bundle = localeManager.getBundle();
    private UserController userController = new UserController();
    private static final String SAVE_TITLE = "save_title";
    private static final String CHANGES_SAVED = "changes_saved";
    private static final String SAVE_ERROR = "save_error";
    private static final String UNABLE_TO_SAVE = "unable_to_save";
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
                    logger.info("Stage is null in EditProfileController initialize()");
                }
            });
        } else {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"),
                    bundle.getString("no_user_data"));
            logger.info("ERROR: No logged in user data found");
        }
    }

    private void saveChanges() {
        if (guiContext.isUser() && !nameField.getText().isEmpty() && !emailField.getText().isEmpty()
                && !phoneField.getText().isEmpty()) {
                guiContext.setUserName(nameField.getText());
                guiContext.setUserEmail(emailField.getText());
                guiContext.setUserPhoneNumber(phoneField.getText());
                userController.updateUser(guiContext.getUser());
                showAlert(Alert.AlertType.INFORMATION, bundle.getString(SAVE_TITLE),
                    bundle.getString(CHANGES_SAVED));
            }
        else {
            showAlert(Alert.AlertType.INFORMATION, bundle.getString(SAVE_ERROR),
                    bundle.getString(UNABLE_TO_SAVE));
        }
    }

    @FXML
    public void handleSwitchToProfile() {
        saveChanges();
        switchScene(SceneNames.PROFILE);
    }

    @Override
    @FXML
    public void handleHomeClick() {
        saveChanges();
        switchScene(SceneNames.BEGIN_SESSION);
    }

    @Override
    @FXML
    public void handleBackClick() {
        saveChanges();
        switchScene(SceneNames.OPTIONS);
    }

    private void handleLang() {
        LocaleManager localeManager = LocaleManager.getInstance();
        Locale locale = localeManager.getLocale();
        switch (locale.getLanguage()) {
            case "ja" -> routeToLang.setLayoutX(200);
            case "zh" -> routeToLang.setLayoutX(220);
            default -> routeToLang.setLayoutX(160);
        }
    }

    // For testing
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void routeToLang() {
        switchScene(SceneNames.LANGUAGE);
    }
}
