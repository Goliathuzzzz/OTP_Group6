package controller.view_controllers;

import context.GUIContext;
import controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import util.SceneNames;

import static util.ProfilePictureUtil.getProfilePictureView;

public class ProfileController extends BaseController {

    private GUIContext guiContext = GUIContext.getInstance();

    @FXML
    private Pane bottomNavPane, profileImagePane;

    @FXML
    private ImageView homeIcon, profileIcon, backIcon, editButton;

    @FXML
    private Label nameLabel, emailLabel, phoneLabel;

    // default constructor for FXML loader
    public ProfileController() {
        this(GUIContext.getInstance());
    }

    // constructor for testing
    public ProfileController(GUIContext guiContext) {
        this.guiContext = guiContext;
    }

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
            nameLabel.setText("guest");

            emailLabel.setVisible(false);
            emailLabel.setManaged(false);
            editButton.setVisible(false);
        } else {
            showAlert(Alert.AlertType.ERROR, "error", "user_details_not_found_alert");
            System.err.println("ERROR: No user or guest data found");
        }
        ImageView profileImageView = getProfilePictureView(guiContext.getId(), 200, 200);
        profileImagePane.getChildren().add(profileImageView);
    }

    @FXML
    private void handleEditClick(MouseEvent event) {
        switchScene(SceneNames.EDIT_PROFILE);
    }
}

