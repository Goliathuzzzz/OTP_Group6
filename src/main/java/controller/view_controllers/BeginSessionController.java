package controller.view_controllers;
import controller.BaseController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.SceneNames;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BeginSessionController extends BaseController {

    @FXML
    private ImageView homeIcon, profileIcon, backIcon;

    @FXML
    private AnchorPane beginSessionPane;

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

