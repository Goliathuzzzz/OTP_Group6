package controller.view_controllers;

import guiContext.GuiContext;
import guiContext.LocaleManager;
import controller.BaseController;
import controller.MatchController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Match;
import model.Matcher;
import model.Participant;
import model.Session;
import model.User;
import model.categories.Category;
import util.SceneNames;

public class SessionController extends BaseController {

    private final Session session = guiContext.getSession();
    private final Participant participant = session.getParticipant();
    private Matcher matcher = new Matcher(session);
    private final ResourceBundle bundle = localeManager.getBundle();
    private MatchController matchController = new MatchController();
    @FXML
    private Button readyButton;
    @FXML
    private Pane animals, food, hobbies, sports, science;
    @FXML
    private ImageView homeIcon, profileIcon, backIcon;
    @FXML
    private VBox interestsContainer;
    @FXML
    private Label titleLabel, subtitleLabel;
    @FXML
    private AnchorPane sessionPane;
    @FXML
    private Label animalLabel, foodLabel, hobbiesLabel, sportsLabel, scienceLabel, sessionLabel;

    @FXML
    private void handleReady(ActionEvent event) {
        List<Category> selectedInterests = session.getParticipantInterests();
        if (selectedInterests.isEmpty()) {
            System.err.println("ERROR: No interests selected in SessionController handleReady()");
            showAlert(Alert.AlertType.WARNING, bundle.getString("error"),
                    bundle.getString("choose_one_interest_alert"));
            return;
        }
        matchParticipant();
        switchScene(SceneNames.MATCH);
    }

    @FXML
    public void initialize() {
        handleLang();
        // check if session is set
        if (session == null) {
            System.err.println("ERROR: Session is null in SessionController");
            return;
        }

        if (participant == null) {
            System.err.println("ERROR: Participant is null in SessionController");
            return;
        }

        // set event handlers for interests
        for (Node interest : interestsContainer.getChildren()) {
            interest.setOnMouseClicked(this::handleInterestSelection);
        }

        // wait for stage to be set before accessing UI elements
        // redundant?
        Platform.runLater(() -> {
            System.out.println("DEBUG: stage is " + (stage == null ? "NULL" : "SET"));
            Stage stage = (Stage) sessionPane.getScene().getWindow();
        });

        Platform.runLater(() -> {
            Stage stage = (Stage) sessionPane.getScene().getWindow();
            if (stage != null) {
                stage.setTitle(bundle.getString("session"));
            } else {
                System.out.println("Stage is null in SessionController initialize()");
            }
        });
    }

    @FXML
    private void handleInterestSelection(MouseEvent event) {
        Pane selectedInterest = (Pane) event.getSource();
        if (selectedInterest.getStyleClass().contains("selected")) {
            selectedInterest.getStyleClass().remove("selected");
        } else {
            selectedInterest.getStyleClass().add("selected");
        }

        String categoryString = selectedInterest.getId();
        if (categoryString != null) {
            navigateToInterests(event, categoryString);
        }
    }

    private void navigateToInterests(MouseEvent event, String category) {
        switchScene("interest_selection", category); //passing category only for interest selection
        System.out.println("Navigated to category: " + category);
    }

    public void matchParticipant() {
        matcher.matchParticipant();
        List<Match> matches = new ArrayList<>();
        HashMap<User, Double> topMatches = matcher.getTopMatches();

        if (topMatches.isEmpty()) {
            System.err.println(
                    "ERROR: No top matches found in SessionController matchParticipant()");
            return;
        }

        for (Map.Entry<User, Double> entry : topMatches.entrySet()) {
            matchController.matchParticipants(participant, entry.getKey(), entry.getValue());
            matches.add(new Match(participant, entry.getKey(), entry.getValue()));
        }
        guiContext.setMatches(matches);
    }

    // For testing
    public void setMatchController(MatchController matchController) {
        this.matchController = matchController;
    }

    // For testing
    public Matcher getMatcher() {
        return matcher;
    }

    // For testing
    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    private void handleLang() {
        LocaleManager localeManager = LocaleManager.getInstance();
        Locale locale = localeManager.getLocale();
        if ("ja".equals(locale.getLanguage())) {
            sessionLabel.setScaleX(0.8);
            sessionLabel.setScaleY(0.8);
        }
    }
}
