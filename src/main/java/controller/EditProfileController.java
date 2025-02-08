package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;

public class EditProfileController extends BaseController {

    @FXML
    private ImageView profileImage, homeIcon, profileIcon, backIcon;

    @FXML
    private Label nameLabel, emailLabel, phoneLabel;

    @FXML
    private TextField nameField, emailField, phoneField;

    @FXML
    private void handlePreviousClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION, "profiiliin", "siirrytään profiiliin");
        switchScene("profile");
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "kotiin", "siirrytään etusivulle");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION, "profiiliin", "siirrytään profiiliin");
        switchScene("profile");
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION,"takaisin", "siirrytään kirjautumiseen");
        switchScene("options");
    }
}
