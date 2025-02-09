package controller;

import context.GUIContext;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import model.User;

public class EditProfileController extends BaseController {

    private final UserController userController = new UserController();
    private final GUIContext guiContext = GUIContext.getInstance();

    @FXML
    private ImageView profileImage, homeIcon, profileIcon, backIcon;

    @FXML
    private Label nameLabel, emailLabel, phoneLabel;

    @FXML
    private TextField nameField, emailField, phoneField;

    @FXML
    private void initialize() {
        if (guiContext.isUser()) {
            nameField.setText(guiContext.getUserName());
            emailField.setText(guiContext.getUserEmail());
            phoneField.setText(guiContext.getUserPhoneNumber());
        }
    }

    private void saveChanges() {
        guiContext.setUserName(nameField.getText());
        guiContext.setUserEmail(emailField.getText());
        guiContext.setUserPhoneNumber(phoneField.getText());
        userController.updateUser(guiContext.getUser());
    }

    @FXML
    private void handlePreviousClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION, "profiiliin", "siirrytään profiiliin");
        saveChanges();
        switchScene("profile");
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION, "kotiin", "siirrytään etusivulle");
        saveChanges();
        switchScene("begin_session");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION, "profiiliin", "siirrytään profiiliin");
        saveChanges();
        switchScene("profile");
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION,"takaisin", "siirrytään kirjautumiseen");
        saveChanges();
        switchScene("options");
    }
}
