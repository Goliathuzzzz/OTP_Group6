package controller.view_controllers;

import controller.BaseController;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import util.SceneNames;

import java.util.Objects;

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
        System.out.println("Details button clicked!");
    }

    @FXML
    public void initialize() {
        startHeartbeatAnimation();
        loadProfileImages();
        detailsButton.setOnMouseClicked(this::handleDetailsButtonClick);
    }

    private void loadProfileImages() {
        Image dummyImage1 = loadDummyImage("/images/placeholder_profile2.png");
        Image dummyImage2 = loadDummyImage("/images/placeholder_profile1.png");

        placeholderProfile1.setImage(dummyImage1);
        placeholderProfile2.setImage(dummyImage2);
    }

    private Image loadDummyImage(String imagePath) {
        try {
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (Exception e) {
            System.out.println("Failed to load image: " + imagePath);
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/placeholder_profile.png")));
        }
    }

    private void startHeartbeatAnimation() {
        ScaleTransition heartbeat = new ScaleTransition(Duration.seconds(1), matchHeart);
        heartbeat.setByX(0.1);
        heartbeat.setByY(0.1);
        heartbeat.setCycleCount(ScaleTransition.INDEFINITE);
        heartbeat.setAutoReverse(true);
        heartbeat.play();
    }

    @FXML
    private void handleNext(MouseEvent event) {
        switchScene(SceneNames.AFTER_MATCH);
    }
}
