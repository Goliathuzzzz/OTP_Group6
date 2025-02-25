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
    private AnchorPane afterMatchPane;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) afterMatchPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("Sport");
            } else {
                System.out.println("Stage is null in AfterMatchController initialize()");
            }
        });
    }
}
