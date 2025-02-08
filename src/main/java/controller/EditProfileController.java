package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;

public class EditProfileController {

    @FXML
    private ImageView profileImage;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField phoneField;

    @FXML
    private ImageView homeIcon;

    @FXML
    private ImageView profileIcon;

    @FXML
    private ImageView backIcon;

    @FXML
    private void handlePreviousClick(MouseEvent event) {
        showAlert("profiiliin", "siirrytään profiiliin");
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        showAlert("kotiin", "siirrytään etusivulle");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        showAlert("profiiliin", "siirrytään profiiliin");
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        showAlert("takaisin", "siirrytään kirjautumiseen");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
