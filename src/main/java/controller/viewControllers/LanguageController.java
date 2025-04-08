package controller.viewControllers;

import context.LocaleManager;
import controller.BaseController;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import util.SceneNames;

public class LanguageController extends BaseController {

    private static String previousScene;
    private final Map<AnchorPane, Circle> toggleMap = new HashMap<>();
    private final LocaleManager localeManager = LocaleManager.getInstance();
    private final ResourceBundle bundle = localeManager.getBundle();
    @FXML
    private Button readyButton;
    ;
    @FXML
    private Circle toggleCircle1, toggleCircle2, toggleCircle3, toggleCircle4;
    @FXML
    private AnchorPane toggleFinnish, toggleEnglish, toggleJapanese, toggleChinese, languagePane;

    public static void setPreviousScene(String scene) {
        previousScene = scene;
        System.out.println("DEBUG: previous scene set to " + previousScene);
    }

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

        Platform.runLater(() -> {
            Stage stage = (Stage) languagePane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("language_selection"));
            } else {
                System.err.println("Stage is null in InterestSelectionController initialize()");
            }
        });
    }

    private void resetAllToggles() {
        for (Circle circle : toggleMap.values()) {
            circle.setLayoutX(18); //"off" position
        }
    }

    private void setLanguageFromToggle(AnchorPane toggle) {
        if (toggle == toggleFinnish) {
            setLanguage(new Locale("fi", "FI"));
        } else if (toggle == toggleEnglish) {
            setLanguage(new Locale("en", "US"));
        } else if (toggle == toggleJapanese) {
            setLanguage(new Locale("ja", "JP"));
        } else if (toggle == toggleChinese) {
            setLanguage(new Locale("zh", "CN"));
        }
    }

    public void setLanguage(Locale locale) {
        localeManager.setLocale(locale);
        localeManager.setBundle(ResourceBundle.getBundle("Messages", locale));
        System.out.println("DEBUG: Locale set to " + locale.getLanguage());
    }

    @FXML
    private void handleReady() {
        System.out.println("DEBUG: previous scene is " + previousScene);
        if (previousScene != null) {
            switch (previousScene) {
                case SceneNames.EDIT_PROFILE:
                    switchScene(SceneNames.EDIT_PROFILE);
                    break;
                case SceneNames.WELCOME:
                default:
                    switchScene(SceneNames.OPTIONS);
                    break;
            }
        } else {
            //default routing if no previous scene is set
            switchScene(SceneNames.OPTIONS);
        }
    }
}
