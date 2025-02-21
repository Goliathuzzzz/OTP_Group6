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

public class EditProfileController extends BaseController {

    private final UserController userController = new UserController();
    private final GUIContext guiContext = GUIContext.getInstance();

    @FXML
    private ImageView profileImage, homeIcon, profileIcon, backIcon, helpIcon;

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
        } else {
            showAlert(Alert.AlertType.ERROR, "virhe", "käyttäjätietoja ei löydy");
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
    private void handlePreviousClick(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "tallennus", "muutokset tallennettu");
        saveChanges();
        switchScene(SceneNames.PROFILE);
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        saveChanges();
        switchScene(SceneNames.BEGIN_SESSION);
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "tallennus", "muutokset tallennettu");
        saveChanges();
        switchScene(SceneNames.PROFILE);
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "tallennus", "muutokset tallennettu");
        saveChanges();
        switchScene(SceneNames.OPTIONS);
    }

    @FXML
    private void handleHelpClick(MouseEvent mouseEvent) {
        System.out.println("help");
    }
}
