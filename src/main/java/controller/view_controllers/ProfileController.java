package controller.view_controllers;

import context.GUIContext;
import controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import util.SceneNames;

import java.util.Objects;
import java.util.Random;

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
            nameLabel.setText("vieras");

            emailLabel.setVisible(false);
            emailLabel.setManaged(false);
            editButton.setVisible(false);
        } else {
            showAlert(Alert.AlertType.ERROR, "virhe", "käyttäjätietoja ei löydy");
            System.err.println("ERROR: No user or guest data found");
        }

        String[] imagePaths = {
                "/images/alpaca_icon.png",
                "/images/elephant_icon.png",
                "/images/flamingo_icon.png",
                "/images/giraffe_icon.png",
                "/images/parrot_icon.png",
                "/images/rhino_icon.png",
                "/images/tiger_icon.png"
        };

        Random random = new Random();
        int randomIndex = random.nextInt(imagePaths.length);

        Image profileImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePaths[randomIndex])));

        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitWidth(200);
        profileImageView.setFitHeight(200);
        profileImageView.setPreserveRatio(true);
        profileImagePane.getChildren().add(profileImageView);
    }

    @FXML
    private void handleEditClick(MouseEvent event) {
        switchScene(SceneNames.EDIT_PROFILE);
    }
}

