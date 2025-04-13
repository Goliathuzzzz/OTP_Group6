package controller.view_controllers;

import static util.ProfilePictureUtil.getProfilePictureView;

import gui_context.GuiContext;
import gui_context.LocaleManager;
import controller.BaseController;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.SceneNames;

public class AdminProfileController extends BaseController {

    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();
    @FXML
    private Pane bottomNavPane, profileImagePane;
    @FXML
    private ImageView homeIcon, profileIcon, backIcon, editButton, helpIcon;
    @FXML
    private Label nameLabel, emailLabel, phoneLabel;
    @FXML
    private AnchorPane adminProfilePane;

    // default constructor for FXML loader
    public AdminProfileController() {
        this(GuiContext.getInstance());
    }

    // constructor for testing
    public AdminProfileController(GuiContext guiContext) {
        this.guiContext = guiContext;
    }

    @FXML
    private void initialize() {
        if (guiContext.isUser()) {
            ProfileController.setUserLabels(guiContext, nameLabel, emailLabel, phoneLabel);

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
