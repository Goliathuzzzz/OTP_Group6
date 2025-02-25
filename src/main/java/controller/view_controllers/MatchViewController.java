package controller.view_controllers;

import controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import util.SceneNames;

public class MatchViewController extends BaseController {

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
    private void handleHelpClick(MouseEvent event) {
        System.out.println("help");
    }

    @FXML
    private void handleNext(MouseEvent event) {
        switchScene(SceneNames.AFTER_MATCH);
    }
}
