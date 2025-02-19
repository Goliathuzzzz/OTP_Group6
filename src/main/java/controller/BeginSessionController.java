package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.SceneNames;

public class BeginSessionController extends BaseController {

    @FXML
    private ImageView homeIcon, profileIcon, backIcon;

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
}

