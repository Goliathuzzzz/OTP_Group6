package controller.view_controllers;

import controller.BaseController;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import util.SceneNames;

public class WelcomeController extends BaseController {

    @FXML
    public ImageView logo;

    @FXML
    public void initialize() {
        System.out.println("welcome");
        startPulsatingLogo();
    }

    @FXML
    private void handleClick() {
        switchScene(SceneNames.LANGUAGE);
        LanguageController.setPreviousScene(SceneNames.WELCOME);
    }

    private void startPulsatingLogo() {
        ScaleTransition pulsate = new ScaleTransition(Duration.seconds(1), logo);
        pulsate.setByX(0.1);
        pulsate.setByY(0.1);
        pulsate.setCycleCount(ScaleTransition.INDEFINITE);
        pulsate.setAutoReverse(true);
        pulsate.play();
    }
}
