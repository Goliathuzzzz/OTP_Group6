package controller.view_controllers;

import context.GUIContext;
import controller.BaseController;
import controller.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.SceneNames;

import static util.ProfilePictureUtil.getProfilePictureView;
import static util.SceneNames.EDIT_PROFILE;

public class EditProfileController extends BaseController {

    private UserController userController = new UserController();
    private final GUIContext guiContext = GUIContext.getInstance();

    @FXML
    private ImageView profileImage, homeIcon, profileIcon, backIcon, helpIcon, backIcon1;

    @FXML
    private Label nameLabel, emailLabel, phoneLabel, routeToLang;

    @FXML
    private TextField nameField, emailField, phoneField;

    @FXML
    private void initialize() {
        if (guiContext.isUser()) {
            nameField.setText(guiContext.getUserName());
            emailField.setText(guiContext.getUserEmail());
            phoneField.setText(guiContext.getUserPhoneNumber());

            ImageView profileImageView = getProfilePictureView(guiContext.getId(), 120, 120);
            profileImage.setImage(profileImageView.getImage());
            LanguageController.setPreviousScene(EDIT_PROFILE);
        } else {
            showAlert(Alert.AlertType.ERROR, "error_title", "no_user_data");
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
        showAlert(Alert.AlertType.INFORMATION, "save_title", "changes_saved");
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
        showAlert(Alert.AlertType.INFORMATION, "save_title", "changes_saved");
        saveChanges();
        switchScene(SceneNames.PROFILE);
    }

    @FXML
    public void handleBackClick(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "save_title", "changes_saved");
        saveChanges();
        switchScene(SceneNames.OPTIONS);
    }

    // For testing
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void routeToLang(MouseEvent event) {
        switchScene(SceneNames.LANGUAGE);
    }
}
