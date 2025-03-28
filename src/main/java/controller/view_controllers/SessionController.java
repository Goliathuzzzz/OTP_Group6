package controller.view_controllers;

import context.GUIContext;
import controller.BaseController;
import controller.MatchController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    private final GUIContext context = GUIContext.getInstance();
    private Session session = context.getSession();
    private final Participant participant = session.getParticipant();
    private MatchController matchController = new MatchController();
    private Matcher matcher = new Matcher(session);

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
    private Label animalLabel, foodLabel, hobbiesLabel, sportsLabel, scienceLabel;

    @FXML
    private void handleReady(ActionEvent event) {
        List<Category> selectedInterests = session.getParticipantInterests();
        if (selectedInterests.isEmpty()) {
            System.err.println("ERROR: No interests selected in SessionController handleReady()");
            showAlert(Alert.AlertType.WARNING, "error", "choose_one_interest_alert");
            return;
        }
        matchParticipant();
        switchScene(SceneNames.MATCH);
    }

    @FXML
    public void initialize() {
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
    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    // For testing
    public Matcher getMatcher() {
        return matcher;
    }
}