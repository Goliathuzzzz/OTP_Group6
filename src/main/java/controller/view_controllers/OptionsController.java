package controller.view_controllers;

import context.LocaleManager;
import controller.BaseController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.SceneNames;

import java.util.Locale;

public class OptionsController extends BaseController {

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

    private void handleLang() {
        LocaleManager localeManager = LocaleManager.getInstance();
        Locale locale = localeManager.getLocale();
        if (locale.getLanguage().equals("ja")) {
            speedDating.setLayoutX(45);
            or.setLayoutX(175);
        }
    }
}
