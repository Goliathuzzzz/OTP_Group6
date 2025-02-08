package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GuestViewController {

    @FXML
    private TextField phoneField;

    @FXML
    private Button continueButton;

    @FXML
    private ImageView backIcon1, homeIcon, profileIcon, backIcon;

    @FXML
    private void handleContinueClick() {
        String phoneNumber = phoneField.getText();
        if (phoneNumber.isEmpty()) {
            showAlert( "syötä","syötä puhelinnumero.");
        } else {
            showAlert( "jatketaan","jatketaan vierailijana puhelinnumerolla: " + phoneNumber);
        }
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        showAlert( "kotiin","siirrytään etusivulle");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        showAlert( "profiiliin","siirrytään profiiliin");
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
