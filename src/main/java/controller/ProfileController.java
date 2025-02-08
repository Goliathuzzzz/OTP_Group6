package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class ProfileController extends BaseController {

    @FXML
    private Pane bottomNavPane, profileImagePane;

    @FXML
    private ImageView homeIcon, profileIcon, backIcon, editButton;

    @FXML
    private Label nameLabel, emailLabel, phoneLabel;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        showAlert( Alert.AlertType.INFORMATION, "kotiin","siirrytään etusivulle");
        System.out.println("Home button clicked");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        switchScene("profile");
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        switchScene("options");
    }

    @FXML
    private void handleEditClick(MouseEvent event) {
        //showAlert(Alert.AlertType.INFORMATION, "muokkaus", "siirrytään muokkaukseen");
        switchScene("edit_profile");
    }
}

