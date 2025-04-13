package controller.view_controllers;

import guiContext.LocaleManager;
import controller.BaseController;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.SceneNames;

public class OptionsController extends BaseController {

    private final ResourceBundle bundle = localeManager.getBundle();
    @FXML
    private AnchorPane optionsPane;
    @FXML
    private Label speedDating, or;

    @FXML
    public void initialize() {
        handleLang();
        Platform.runLater(() -> {
            Stage stage = (Stage) optionsPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("main_menu"));
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

    private void handleLang() {
        LocaleManager localeManager = LocaleManager.getInstance();
        Locale locale = localeManager.getLocale();
        if ("ja".equals(locale.getLanguage())) {
            speedDating.setLayoutX(45);
            or.setLayoutX(175);
        } else if ("zh".equals(locale.getLanguage())) {
            or.setLayoutX(190);
            speedDating.setLayoutX(115);
        }
    }
}
