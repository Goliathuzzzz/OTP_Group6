package controller.view_controllers;

import static util.ProfilePictureUtil.getProfilePictureView;

import guiContext.GuiContext;
import guiContext.LocaleManager;
import controller.BaseController;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.SceneNames;

public class ProfileController extends BaseController {

    private final ResourceBundle bundle = localeManager.getBundle();
    @FXML
    private Pane profileImagePane;
    @FXML
    private ImageView editButton;
    @FXML
    private Label nameLabel, emailLabel, phoneLabel, langLabel;
    @FXML
    private AnchorPane profilePane;

    // default constructor for FXML loader
    public ProfileController() {
        this(GuiContext.getInstance());
    }

    // constructor for testing
    public ProfileController(GuiContext guiContext) {
        this.guiContext = guiContext;
    }

    @FXML
    private void initialize() {
        if (guiContext.isUser()) {
            setUserLabels(guiContext, nameLabel, emailLabel, phoneLabel);
            //TODO: set language
        } else if (guiContext.isGuest()) {
            setGuestLabels(guiContext, bundle, phoneLabel, nameLabel, emailLabel, editButton);
        } else {
            showAlert(Alert.AlertType.ERROR, bundle.getString("error"),
                    bundle.getString("user_details_not_found_alert"));
            System.err.println("ERROR: No user or guest data found");
        }
        ImageView profileImageView = getProfilePictureView(guiContext.getId(), 200, 200);
        profileImagePane.getChildren().add(profileImageView);

        Locale currentLocale = localeManager.getLocale();
        String languageDisplay = currentLocale.getDisplayLanguage(currentLocale).toLowerCase(Locale.ROOT);
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

    public static void setUserLabels(GuiContext guiContext, Label nameLabel, Label emailLabel,
                                     Label phoneLabel) {
        guiContext.setUserNameProperty(guiContext.getUserName());
        guiContext.setEmailProperty(guiContext.getUserEmail());
        guiContext.setPhoneProperty(guiContext.getUserPhoneNumber());
        nameLabel.textProperty().bind(guiContext.getUserNameProperty());
        emailLabel.textProperty().bind(guiContext.getEmailProperty());
        phoneLabel.textProperty().bind(guiContext.getPhoneProperty());
    }

    private void setGuestLabels(GuiContext guiContext, ResourceBundle bundle, Label phoneLabel, Label nameLabel, Label emailLabel, ImageView editButton) {
        guiContext.setPhoneProperty(guiContext.getGuestPhoneNumber());
        phoneLabel.textProperty().bind(guiContext.getPhoneProperty());
        nameLabel.setText(bundle.getString("guest"));
        emailLabel.setVisible(false);
        emailLabel.setManaged(false);
        editButton.setVisible(false);
    }

    @FXML
    private void handleEditClick() {
        switchScene(SceneNames.EDIT_PROFILE);
    }
}
