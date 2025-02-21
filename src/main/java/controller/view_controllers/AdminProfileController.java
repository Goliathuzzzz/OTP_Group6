package controller.view_controllers;

import context.GUIContext;
import controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import util.SceneNames;

public class AdminProfileController extends BaseController {

    private final GUIContext guiContext = GUIContext.getInstance();

    @FXML
    private Pane bottomNavPane, profileImagePane;

    @FXML
    private ImageView homeIcon, profileIcon, backIcon, editButton, helpIcon;

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
        switchScene("begin_session");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        switchScene(SceneNames.PROFILE);
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        switchScene(SceneNames.OPTIONS);
    }

    @FXML
    private void handleHelpClick(MouseEvent event) {
        System.out.println("help");
    }

    @FXML
    private void handleUsersClick(MouseEvent event) {
        switchScene(SceneNames.ADMIN_USERS);
    }

    @FXML
    private void handlePairsClick(MouseEvent event) {
        switchScene(SceneNames.ADMIN_HOME);
    }

    @FXML
    private void handleEditClick(MouseEvent event) {
        switchScene(SceneNames.EDIT_PROFILE);
    }
}

