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

