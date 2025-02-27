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

    private GUIContext guiContext = GUIContext.getInstance();

    @FXML
    private Pane bottomNavPane, profileImagePane;

    @FXML
    private ImageView homeIcon, profileIcon, backIcon, editButton, helpIcon;

    @FXML
    private Label nameLabel, emailLabel, phoneLabel;

    // default constructor for FXML loader
    public AdminProfileController() {
        this(GUIContext.getInstance());
    }

    // constructor for testing
    public AdminProfileController(GUIContext guiContext) {
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
        }
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

