package controller.view_controllers;

import controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class HelpController extends BaseController {

    @FXML
    private Label middleText1;

    @FXML
    private Label middleText2;

    @FXML
    private VBox vBox;

    @FXML
    public void initialize() {
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setPadding(new javafx.geometry.Insets(0, 0, 50, 0));
    }
}