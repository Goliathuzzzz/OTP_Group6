package controller;

import context.GUIContext;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import model.User;

public class ProfileController extends BaseController {

    private final GUIContext guiContext = GUIContext.getInstance();

    @FXML
    private Pane bottomNavPane, profileImagePane;

    @FXML
    private ImageView homeIcon, profileIcon, backIcon, editButton;

    @FXML
    private Label nameLabel, emailLabel, phoneLabel;

    @FXML
    private void initialize() {
        if (guiContext.isUser()) {
            guiContext.setUserNameProperty(guiContext.getUserName());
            guiContext.setEmailProperty(guiContext.getUserEmail());
            guiContext.setPhoneProperty(guiContext.getUserPhoneNumber());
            nameLabel.textProperty().bind(guiContext.getUserNameProperty());
            emailLabel.textProperty().bind(guiContext.getEmailProperty());
            phoneLabel.textProperty().bind(guiContext.getPhoneProperty());
        } else if (guiContext.isGuest()) {
            guiContext.setPhoneProperty(guiContext.getGuestPhoneNumber());
            phoneLabel.textProperty().bind(guiContext.getPhoneProperty());
            nameLabel.setText("vieras");
            emailLabel.setVisible(false); // figure a way to remove the empty space, this only hides the text
            editButton.setVisible(false);
        } else {
            showAlert(Alert.AlertType.ERROR, "virhe", "käyttäjätietoja ei löydy");
            System.err.println("ERROR: No user or guest data found");
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
        switchScene("edit_profile");
    }
}

