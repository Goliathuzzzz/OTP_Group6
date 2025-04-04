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
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.SceneNames;

import java.util.Locale;
import java.util.ResourceBundle;

import static util.ProfilePictureUtil.getProfilePictureView;

public class ProfileController extends BaseController {

    private GUIContext guiContext = GUIContext.getInstance();

    @FXML
    private Pane bottomNavPane, profileImagePane;

    @FXML
    private ImageView homeIcon, profileIcon, backIcon, editButton;

    @FXML
    private Label nameLabel, emailLabel, phoneLabel, langLabel;

    @FXML
    private AnchorPane profilePane;

    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();

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
            //TODO: set language
        } else if (guiContext.isGuest()) {
            guiContext.setPhoneProperty(guiContext.getGuestPhoneNumber());
            phoneLabel.textProperty().bind(guiContext.getPhoneProperty());
            nameLabel.setText(bundle.getString("guest"));

            emailLabel.setVisible(false);
            emailLabel.setManaged(false);
            editButton.setVisible(false);
        } else {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error"), bundle.getString("user_details_not_found_alert"));
            System.err.println("ERROR: No user or guest data found");
        }
        ImageView profileImageView = getProfilePictureView(guiContext.getId(), 200, 200);
        profileImagePane.getChildren().add(profileImageView);

        Locale currentLocale = localeManager.getLocale();
        String languageDisplay = currentLocale.getDisplayLanguage(currentLocale).toLowerCase();
        langLabel.setText(languageDisplay);

        Platform.runLater(() -> {
            Stage stage = (Stage) profilePane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("profile"));
            } else {
                System.out.println("Stage is null in ProfileController initialize()");
            }
        });
    }

    @FXML
    private void handleEditClick(MouseEvent event) {
        switchScene(SceneNames.EDIT_PROFILE);
    }
}
