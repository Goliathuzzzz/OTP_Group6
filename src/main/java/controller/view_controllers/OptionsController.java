package controller.view_controllers;

import controller.BaseController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.SceneNames;

public class OptionsController extends BaseController {

    @FXML
    private AnchorPane optionsPane;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) optionsPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("main_menu");
            }
        });
    }

    @FXML
    private void handleLogin() {
        switchScene(SceneNames.LOGIN);
    }

    @FXML
    private void handleNewUser() {
        switchScene(SceneNames.REGISTRATION);
    }

    @FXML
    private void handleGuest() {
        switchScene(SceneNames.GUEST);
    }
}
