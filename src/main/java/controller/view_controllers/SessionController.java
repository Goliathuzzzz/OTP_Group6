package controller.view_controllers;

import context.GUIContext;
import controller.BaseController;
import controller.MatchController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import model.categories.Category;
import util.SceneNames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionController extends BaseController {

    private GUIContext context = GUIContext.getInstance();
    private Session session;
    private Participant participant;
    private MatchController matchController;
    private Matcher matcher;

    @FXML
    private Button readyButton;

    @FXML
    private ImageView homeIcon, profileIcon, backIcon;

    @FXML
    private VBox interestsContainer;

    @FXML
    private Label titleLabel, subtitleLabel;

    @FXML
    private AnchorPane sessionPane;

    @FXML
    private Label animalLabel, foodLabel, hobbiesLabel, sportsLabel, scienceLabel;

    private static final Map<String, String> CATEGORY_MAP = Map.of(
            "eläimet", "animals",
            "ruoka", "food",
            "harrastukset", "hobbies",
            "urheilu", "sports",
            "tiede", "science"
    );

    @FXML
    private void handleReady(ActionEvent event) {
        matchParticipant();
        switchScene(SceneNames.MATCH);
    }

    @FXML
    public void initialize() {
        // check if session is set
        session = context.getSession();
        if (session == null) {
            System.err.println("ERROR: Session is null in SessionController");
            return;
        }

        // initialize participant, matchController and matcher
        participant = session.getParticipant();
        if (participant == null) {
            System.err.println("ERROR: Participant is null in SessionController");
            return;
        }
        matchController = new MatchController();
        matcher = new Matcher(session);

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
    }

    @FXML
    private void handleInterestSelection(MouseEvent event) {
        Pane selectedInterest = (Pane) event.getSource();
        if (selectedInterest.getStyleClass().contains("selected")) {
            selectedInterest.getStyleClass().remove("selected");
        } else {
            selectedInterest.getStyleClass().add("selected");
        }

        Label interestLabel = (Label) selectedInterest.lookup(".interest-label");
        if (interestLabel != null) {
            String categoryKey = interestLabel.getText().toLowerCase();
            if (CATEGORY_MAP.containsKey(categoryKey)) {
                navigateToInterests(event, CATEGORY_MAP.get(categoryKey));
            }
        }
    }
    private void navigateToInterests(MouseEvent event, String category) {
        switchScene("interest_selection", category); //passing category only for interest selection
        System.out.println("Navigated to category: " + category);
    }

    private void matchParticipant() {
        matcher.matchParticipant();
        List<Match> matches = new ArrayList<>();
        HashMap<User, Double> topMatches = matcher.getTopMatches();

        if (topMatches.isEmpty()) {
            System.err.println("ERROR: No top matches found in SessionController matchParticipant()");
            return;
        }

        for (Map.Entry<User, Double> entry: topMatches.entrySet()) {
            matchController.matchParticipants(participant, entry.getKey(), entry.getValue());
            matches.add(new Match(participant, entry.getKey(), entry.getValue()));
        }
        context.setMatches(matches);
    }

    // For testing
    public void setMatchController(MatchController matchController) {
        this.matchController = matchController;
    }

    // For testing
    public Map<String, String> getCategoryMap() {
        return CATEGORY_MAP;
    }
}