package controller;

import context.GUIContext;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import model.User;

public class ProfileController extends BaseController {

    private final GUIContext guiContext = GUIContext.getInstance();
    private User user = guiContext.getUser();

    @FXML
    private Pane bottomNavPane, profileImagePane;

    @FXML
    private ImageView homeIcon, profileIcon, backIcon, editButton;

    @FXML
    private Label nameLabel, emailLabel, phoneLabel;

    @FXML
    private void initialize() {
        if (user != null) {
            nameLabel.setText(user.getUserName());
            emailLabel.setText(user.getEmail());
            phoneLabel.setText(user.getPhoneNumber());
        }
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        //showAlert( Alert.AlertType.INFORMATION, "kotiin","siirrytään etusivulle");
        switchScene("begin_session");
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

