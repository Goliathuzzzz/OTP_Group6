package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class ProfileController {

    @FXML
    private Pane bottomNavPane;

    @FXML
    private ImageView homeIcon;

    @FXML
    private ImageView profileIcon;

    @FXML
    private ImageView backIcon;

    @FXML
    private ImageView editButton;

    @FXML
    private Pane profileImagePane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        showAlert( "kotiin","siirrytään etusivulle");
        System.out.println("Home button clicked");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        showAlert( "profiiliin","siirrytään profiiliin");
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        showAlert("takaisin", "siirrytään kirjautumiseen");
    }

    @FXML
    private void handleEditClick(MouseEvent event) {
        showAlert("muokkaus", "siirrytään muokkaukseen");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

