package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class BeginSessionController {

    @FXML
    private ImageView homeIcon, profileIcon, backIcon;

    @FXML
    private void handleBeginSessionClick(MouseEvent event) {
        showAlert("aloitetaan", "siirrytään valintoihin");
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

