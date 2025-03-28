package controller.view_controllers;

import context.LocaleManager;
import controller.BaseController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class HelpController extends BaseController {

    @FXML
    private Label middleText1;

    @FXML
    private Label middleText2;

    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane helpPane;

    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();

    @FXML
    public void initialize() {
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setPadding(new javafx.geometry.Insets(0, 0, 50, 0));
        Platform.runLater(() -> {
            Stage stage = (Stage) helpPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("help"));
            } else {
                System.err.println("Stage is null in HelpController initialize()");
            }
        });
    }
}