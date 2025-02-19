package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import util.SceneNames;

public class OptionsController extends BaseController {

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
