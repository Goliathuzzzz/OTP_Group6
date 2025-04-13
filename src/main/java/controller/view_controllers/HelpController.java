package controller.view_controllers;

import controller.BaseController;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelpController extends BaseController {

    private final ResourceBundle bundle = localeManager.getBundle();
    @FXML
    private Label middleText1;
    @FXML
    private Label middleText2;
    @FXML
    private VBox vBox;
    @FXML
    private AnchorPane helpPane;

    @FXML
    public void initialize() {
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(0, 0, 50, 0));
        Platform.runLater(() -> {
            Stage stage = (Stage) helpPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("help"));
            } else {
                logger.log(Level.SEVERE, "Stage is null in HelpController initialize()");
            }
        });
    }
}
