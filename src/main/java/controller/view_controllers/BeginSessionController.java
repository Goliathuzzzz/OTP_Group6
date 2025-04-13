package controller.view_controllers;

import guiContext.GuiContext;
import controller.BaseController;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Participant;
import model.Session;
import util.SceneNames;

public class BeginSessionController extends BaseController {

    private final ResourceBundle bundle = localeManager.getBundle();
    @FXML
    private ImageView homeIcon, profileIcon, backIcon, bigHeart;
    @FXML
    private AnchorPane beginSessionPane;
    @FXML
    private Label beginSession;
    private ScaleTransition heartbeat;

    @FXML
    public void initialize() {
        handleLang();
        Platform.runLater(() -> {
            Stage stage = (Stage) beginSessionPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("begin"));
            } else {
                System.out.println("Stage is null in BeginSessionController initialize()");
            }
        });
        setupHoverHeartbeat();
    }

    private void setupHoverHeartbeat() {
        heartbeat = new ScaleTransition(Duration.seconds(0.8), bigHeart);
        heartbeat.setByX(0.1);
        heartbeat.setByY(0.1);
        heartbeat.setCycleCount(ScaleTransition.INDEFINITE);
        heartbeat.setAutoReverse(true);

        bigHeart.setOnMouseEntered(event -> heartbeat.play());

        bigHeart.setOnMouseExited(event -> {
            heartbeat.stop();
            bigHeart.setScaleX(1.0);
            bigHeart.setScaleY(1.0);
        });
    }

    @FXML
    private void handleBeginSessionClick(MouseEvent event) {
        GuiContext context = GuiContext.getInstance();
        Participant participant = context.isUser() ? context.getUser() :
                context.isGuest() ? context.getGuest() : null;
        if (participant != null) {
            context.setSession(new Session(participant));
            // reset participant interests
            context.getSession().getParticipant().clearInterests();
            switchScene(SceneNames.SESSION);
        } else {
            System.err.println("No user or guest found in GUIContext");
        }
    }

    private void handleLang() {
        if ("ja".equals(localeManager.getLocale().getLanguage())) {
            beginSession.setScaleX(0.8);
            beginSession.setScaleY(0.8);
            beginSession.setMinWidth(300);
            beginSession.setLayoutX(-40);
        }
    }
}
