package controller.view_controllers;

import controller.BaseController;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.SceneNames;

public class BeginSessionController extends BaseController {

    @FXML
    private ImageView homeIcon, profileIcon, backIcon, bigHeart;

    @FXML
    private AnchorPane beginSessionPane;

    private ScaleTransition heartbeat;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) beginSessionPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("aloita");
            } else {
                System.out.println("Stage is null in BeginSessionController initialize()");
            }
        });

        setupHoverHeartbeat();
    }

    private void setupHoverHeartbeat() {
        heartbeat = new ScaleTransition(Duration.seconds(0.8), bigHeart);
        heartbeat.setByX(0.1);
        heartbeat.setByY(0.1);
        heartbeat.setCycleCount(ScaleTransition.INDEFINITE);
        heartbeat.setAutoReverse(true);

        bigHeart.setOnMouseEntered(event -> heartbeat.play());

        bigHeart.setOnMouseExited(event -> {
            heartbeat.stop();
            bigHeart.setScaleX(1.0);
            bigHeart.setScaleY(1.0);
        });
    }

    @FXML
    private void handleBeginSessionClick(MouseEvent event) {
        switchScene(SceneNames.SESSION);
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        switchScene(SceneNames.BEGIN_SESSION);
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        switchScene(SceneNames.PROFILE);
    }

    @FXML
    private void handleBackClick(MouseEvent event) {
        switchScene(SceneNames.OPTIONS);
    }

    @FXML
    private void handleHelpClick(MouseEvent mouseEvent) {
        System.out.println("help");
    }
}
