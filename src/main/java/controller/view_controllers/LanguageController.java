package controller.view_controllers;

import controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import util.SceneNames;

import java.util.HashMap;
import java.util.Map;

public class LanguageController extends BaseController {

    @FXML
    private Button readyButton;

    @FXML
    private Circle toggleCircle1, toggleCircle2, toggleCircle3, toggleCircle4;

    @FXML
    private AnchorPane toggleFinnish, toggleEnglish, toggleJapanese, toggleChinese;

    private final Map<AnchorPane, Circle> toggleMap = new HashMap<>();

    @FXML
    public void initialize() {
        toggleMap.put(toggleFinnish, toggleCircle1);
        toggleMap.put(toggleEnglish, toggleCircle2);
        toggleMap.put(toggleJapanese, toggleCircle3);
        toggleMap.put(toggleChinese, toggleCircle4);

        for (Map.Entry<AnchorPane, Circle> entry : toggleMap.entrySet()) {
            AnchorPane toggle = entry.getKey();
            Circle circle = entry.getValue();

            toggle.setOnMouseClicked(event -> {
                resetAllToggles();
                circle.setLayoutX(47); //"on" position
                setLanguageFromToggle(toggle);
            });
        }

        readyButton.setOnAction(event -> handleReady());
    }

    private void resetAllToggles() {
        for (Circle circle : toggleMap.values()) {
            circle.setLayoutX(18); //"off" position
        }
    }

    private void setLanguageFromToggle(AnchorPane toggle) {
        if (toggle == toggleFinnish) {
            setLanguage("finnish");
        } else if (toggle == toggleEnglish) {
            setLanguage("english");
        } else if (toggle == toggleJapanese) {
            setLanguage("japanese");
        } else if (toggle == toggleChinese) {
            setLanguage("mandarin chinese");
        }
    }

    //placeholder for now
    public void setLanguage(String language) {
        System.out.println("Language set to: " + language);
    }

    @FXML
    private void handleReady() {
        switchScene(SceneNames.OPTIONS);
    }
}
