package controller.view_controllers;

import context.GUIContext;
import controller.BaseController;
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

import static util.ProfilePictureUtil.getProfilePictureView;

public class MatchViewController extends BaseController {

    @FXML
    private ImageView matchHeart;

    @FXML
    private ImageView placeholderProfile1;

    @FXML
    private ImageView placeholderProfile2;

    @FXML
    private Button detailsButton;

    @FXML
    private void handleDetailsButtonClick(MouseEvent event) {
        switchScene(SceneNames.AFTER_MATCH);
    }

    @FXML
    private AnchorPane matchPane;

    GUIContext context = GUIContext.getInstance();

    @FXML
    public void initialize() {
        startHeartbeatAnimation();
        loadProfileImages();
        Platform.runLater(() -> {
            Stage stage = (Stage) matchPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("start");
            } else {
                System.out.println("Stage is null in MatchViewController initialize()");
            }
        });
//        detailsButton.setOnMouseClicked(this::handleDetailsButtonClick);
    }

    private void loadProfileImages() {
        if (context.getMatches().isEmpty() || context.getMatches().getLast() == null) {
            System.err.println("ERROR: Match is null in MatchViewController initialize()");
            placeholderProfile1.setImage(null);
            placeholderProfile2.setImage(null);
            detailsButton.setDisable(true);
            return;
        }
        int profile1Id = context.getId();
        int profile2Id = context.getMatches().getLast().getParticipant2().getId();

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
