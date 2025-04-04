package controller.view_controllers;

import context.GUIContext;
import context.LocaleManager;
import controller.BaseController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.SceneNames;

import java.util.ResourceBundle;

import static util.ProfilePictureUtil.getProfilePictureView;

public class AdminProfileController extends BaseController {

    private GUIContext guiContext = GUIContext.getInstance();

    @FXML
    private Pane bottomNavPane, profileImagePane;

    @FXML
    private ImageView homeIcon, profileIcon, backIcon, editButton, helpIcon;

    @FXML
    private Label nameLabel, emailLabel, phoneLabel;

    @FXML
    private AnchorPane adminProfilePane;

    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();

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

            ImageView profileImageView = getProfilePictureView(guiContext.getId(), 200, 200);
            profileImagePane.getChildren().add(profileImageView);
        }
        Platform.runLater(() -> {
            Stage stage = (Stage) adminProfilePane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("admin_profile"));
            } else {
                System.out.println("Stage is null in AdminProfileController initialize()");
            }
        });
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
