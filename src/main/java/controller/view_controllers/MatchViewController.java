package controller.view_controllers;

import static util.ProfilePictureUtil.getProfilePictureView;

import controller.BaseController;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.SceneNames;

public class MatchViewController extends BaseController {

    private final ResourceBundle bundle = localeManager.getBundle();
    @FXML
    private ImageView matchHeart;
    @FXML
    private ImageView placeholderProfile1;
    @FXML
    private ImageView placeholderProfile2;
    @FXML
    private Button detailsButton;
    @FXML
    private AnchorPane matchPane;

    @FXML
    private void handleDetailsButtonClick(MouseEvent event) {
        switchScene(SceneNames.AFTER_MATCH);
    }

    @FXML
    public void initialize() {
        startHeartbeatAnimation();
        loadProfileImages();
        Platform.runLater(() -> {
            Stage stage = (Stage) matchPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("match"));
            } else {
                System.out.println("Stage is null in MatchViewController initialize()");
            }
        });
    }

    private void loadProfileImages() {
        if (guiContext.getMatches().isEmpty() || guiContext.getMatches().getLast() == null) {
            System.err.println("ERROR: Match is null in MatchViewController initialize()");
            placeholderProfile1.setImage(null);
            placeholderProfile2.setImage(null);
            detailsButton.setDisable(true);
            return;
        }
        int profile1Id = guiContext.getId();
        int profile2Id = guiContext.getMatches().getLast().getParticipant2().getId();

        ImageView profile1 = getProfilePictureView(profile1Id, 200, 200);
        ImageView profile2 = getProfilePictureView(profile2Id, 200, 200);
        placeholderProfile1.setImage(profile1.getImage());
        placeholderProfile2.setImage(profile2.getImage());
    }

    private void startHeartbeatAnimation() {
        ScaleTransition heartbeat = new ScaleTransition(Duration.seconds(1), matchHeart);
        heartbeat.setByX(0.1);
        heartbeat.setByY(0.1);
        heartbeat.setCycleCount(ScaleTransition.INDEFINITE);
        heartbeat.setAutoReverse(true);
        heartbeat.play();
    }
}
