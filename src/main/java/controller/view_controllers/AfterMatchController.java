package controller.view_controllers;

import controller.BaseController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.SceneNames;

public class AfterMatchController extends BaseController {

    @FXML
    private ImageView homeIcon, profileIcon, backIcon;

    @FXML
    private AnchorPane sportPane;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) sportPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("Sport");
            } else {
                System.out.println("Stage is null in SportController initialize()");
            }
        });
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
        System.out.println("Help button clicked");
    }
}
